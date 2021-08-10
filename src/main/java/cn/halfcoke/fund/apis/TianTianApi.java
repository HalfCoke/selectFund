package cn.halfcoke.fund.apis;

import cn.halfcoke.fund.basic.Fund;
import cn.halfcoke.fund.basic.FundManager;
import cn.halfcoke.fund.network.ApacheHttpRequest;
import cn.halfcoke.fund.network.TianTianWebSite;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 天天基金数据API.
 */
public class TianTianApi {
  private static final Logger LOGGER = LoggerFactory.getLogger(TianTianApi.class);
  static TianTianWebSite SITE = new TianTianWebSite();

  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  /**
   * 返回json格式的经理人列表.
   * 经理人中的基金信息仅包含名称和代码
   */
  public static String getFundManagerList() throws Exception {
    // 获取所有基金管理人列表
    List<FundManager> fundManagerList = SITE.getFundManagerList();
    return GSON.toJson(fundManagerList);
  }

  /**
   * 返回json格式的基金列表.
   * 基金列表信息中不包含规模和成立日期
   */
  public static String getFundList() throws Exception {
    // 获取所有基金列表
    List<Fund> fundList = SITE.getFundList();
    return GSON.toJson(fundList);
  }

  /**
   * 获取所有基金经理人，包含其运营的基金的完整信息.
   */
  public static String getTotalFundManagerList() throws Exception {
    // 获取所有基金管理人列表
    List<FundManager> fundManagerList = SITE.getFundManagerList();
    // 获取所有基金列表
    List<Fund> fundList = SITE.getFundList();
    return getTotalFundManagerList(GSON.toJson(fundManagerList), GSON.toJson(fundList));
  }

  /**
   * 获取所有基金经理人，包含其运营的基金的完整信息.
   */
  public static String getTotalFundManagerList(String fundListManagerJson, String fundListJson)
      throws Exception {
    // 获取所有基金管理人列表
    List<FundManager> fundManagerList =
        GSON.fromJson(fundListManagerJson, new TypeToken<List<FundManager>>() {
        }.getType());
    // 获取所有基金列表
    List<Fund> fundList = GSON.fromJson(fundListJson, new TypeToken<List<Fund>>() {
    }.getType());

    List<Runnable> runnables = new LinkedList<>();
    final CountDownLatch countDownLatch = new CountDownLatch(fundList.size());
    fundList.forEach(fund -> runnables.add(() -> {
      try {
        SITE.addFundInformation(fund);
        countDownLatch.countDown();
      } catch (Exception e) {
        e.printStackTrace();
        System.exit(-1);
      }
    }));


    ExecutorService executorService = new ThreadPoolExecutor(0, 1000,
        60L, TimeUnit.SECONDS,
        new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    for (Runnable runnable : runnables) {
      executorService.submit(runnable);
    }
    Map<String, Fund> fundMap = new HashMap<>();
    fundList.forEach(fund -> fundMap.put(fund.getFundCode(), fund));
    countDownLatch.await();
    LOGGER.info("数据获取完成, 共{}条", fundList.size());
    fundManagerList.forEach(fundManager -> {
      int i = 0;
      while (i < fundManager.getCurrentOperatingFunds().size()) {
        String fundCode = fundManager.getCurrentOperatingFunds().get(i).getFundCode();
        Fund fund = fundMap.get(fundCode);
        if (fund == null) {
          fundManager.getCurrentOperatingFunds().remove(i);
          continue;
        }
        fundManager.getCurrentOperatingFunds().set(i, fund);
        i++;
      }
    });

    return GSON.toJson(fundManagerList);
  }

  public static String getSpecificWorkYearFundManagers(int year) throws Exception {
    return getSpecificWorkYearFundManagers(year, getFundManagerList());
  }

  /**
   * 不小于年限限制的基金经理人列表.
   *
   * @param year             年限限制
   * @param fundManagersJson 基金经理人的json字符串
   * @return 不小于年限限制的基金经理人列表
   */
  public static String getSpecificWorkYearFundManagers(int year, String fundManagersJson) {
    List<FundManager> fundManagers =
        GSON.fromJson(fundManagersJson, new TypeToken<List<FundManager>>() {
        }.getType());

    List<FundManager> res =
        fundManagers.stream().filter(fundManager -> fundManager.getWorkingDay() >= year * 365)
            .collect(
                Collectors.toList());
    return GSON.toJson(res);
  }

  /**
   * 利用所有基金数据，对基金经理人的平均收益率进行排序，并返回指定占前rate的经理人.
   *
   * @param fundManagersJson 基金经理人集合
   * @param allFundJson      基金数据集合，从该集合中返回基金
   * @param period           指定收益率比较维度["今年来", "近1周", "近1月", "近3月", "近6月",
   *                         "近1年", "近2年", "近3年", "近5年"]
   * @param rate             返回前百分之多少的基金经理人
   */
  public static String getHeadEarnManagers(String fundManagersJson, String allFundJson,
                                           String period, float rate) {
    // 解析基金经理人json
    List<FundManager> fundManagers =
        GSON.fromJson(fundManagersJson, new TypeToken<List<FundManager>>() {
        }.getType());

    // 解析基金json
    List<Fund> funds =
        GSON.fromJson(allFundJson, new TypeToken<List<Fund>>() {
        }.getType());

    // 所有基金的map
    Map<String, Fund> fundHashMap = new HashMap<>();
    funds.forEach(fund -> fundHashMap.put(fund.getFundCode(), fund));

    // 得到基金经理人近一段时间的平均收益，时间段由period指定
    Map<FundManager, Float> fundManagerPeriodGrowthRateMap = new HashMap<>();
    fundManagers.forEach(fundManager -> {
      float growthRateAvg = 0;
      List<Fund> currentOperatingFunds = fundManager.getCurrentOperatingFunds();
      int i = 0;
      while (i < currentOperatingFunds.size()) {
        // 补充经理人对象中的运营的基金对象的信息
        Fund fund = fundHashMap.get(currentOperatingFunds.get(i).getFundCode());
        if (fund == null) {
          // 表示基金列表中没有这个基金，不进行计算，并从基金经理人的信息中移除这条信息
          currentOperatingFunds.remove(i);
          continue;
        }
        fund.setNav(currentOperatingFunds.get(i).getNav());
        fund.setTotalNav(currentOperatingFunds.get(i).getTotalNav());
        currentOperatingFunds.set(i, fund);

        if (period.equals(TianTianWebSite.GROWTH_RATE_PERIODS[0])) {
          growthRateAvg += currentOperatingFunds.get(i).getCurYearGrowthRate();
        } else if (period.equals(TianTianWebSite.GROWTH_RATE_PERIODS[1])) {
          growthRateAvg += currentOperatingFunds.get(i).getWeekGrowthRate();
        } else if (period.equals(TianTianWebSite.GROWTH_RATE_PERIODS[2])) {
          growthRateAvg += currentOperatingFunds.get(i).getMonthGrowthRate();
        } else if (period.equals(TianTianWebSite.GROWTH_RATE_PERIODS[3])) {
          growthRateAvg += currentOperatingFunds.get(i).getThreeMonthGrowthRate();
        } else if (period.equals(TianTianWebSite.GROWTH_RATE_PERIODS[4])) {
          growthRateAvg += currentOperatingFunds.get(i).getSixMonthGrowthRate();
        } else if (period.equals(TianTianWebSite.GROWTH_RATE_PERIODS[5])) {
          growthRateAvg += currentOperatingFunds.get(i).getYearGrowthRate();
        } else if (period.equals(TianTianWebSite.GROWTH_RATE_PERIODS[6])) {
          growthRateAvg += currentOperatingFunds.get(i).getTwoYearGrowthRate();
        } else if (period.equals(TianTianWebSite.GROWTH_RATE_PERIODS[7])) {
          growthRateAvg += currentOperatingFunds.get(i).getThreeYearGrowthRate();
        } else if (period.equals(TianTianWebSite.GROWTH_RATE_PERIODS[8])) {
          growthRateAvg += currentOperatingFunds.get(i).getFiveYearGrowthRate();
        }
        i++;
      }
      if (fundManager.getCurrentOperatingFunds().size() != 0) {
        growthRateAvg /= fundManager.getCurrentOperatingFunds().size();
      }

      fundManagerPeriodGrowthRateMap.put(fundManager, growthRateAvg);
    });

    LinkedList<Map.Entry<FundManager, Float>> fundManagersList =
        new LinkedList<>(fundManagerPeriodGrowthRateMap.entrySet());

    fundManagersList.sort((o1, o2) -> Float.compare(o2.getValue(), o1.getValue()));

    int index = (int) (fundManagersList.size() * rate);
    List<Map.Entry<FundManager, Float>> subList = fundManagersList.subList(0, index);
    List<FundManager> res = new LinkedList<>();
    subList.forEach(fundManagerFloatEntry -> res.add(fundManagerFloatEntry.getKey()));

    return GSON.toJson(res);
  }

  /**
   * 获取成立时间大于指定年限的基金.
   *
   * @param fundListJson 基金列表
   * @param year         指定年限
   */
  public static String getGreaterYearFundList(String fundListJson, int year) {
    final List<Fund> fundList = GSON.fromJson(fundListJson, new TypeToken<List<Fund>>() {
    }.getType());

    final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    final LocalDate nowDateTime = LocalDate.now();

    List<Fund> resList = fundList.stream().filter(fund -> {
      try {
        LocalDate establishDate = LocalDate.parse(fund.getEstablishDate(), timeFormatter);
        return nowDateTime.getYear() - establishDate.getYear() >= year;
      } catch (Exception e) {
        LOGGER.error("parse " + fund.getFundCode() + " date error!");
      }
      return false;
    }).collect(Collectors.toList());

    return GSON.toJson(resList);
  }

  /**
   * 返回指定规模的基金列表,单位是亿元.
   *
   * @param fundListJson 基金json列表
   * @param low          基金规模最低限
   * @param high         基金规模最高限
   */
  public static String getAssetIntervalFundList(String fundListJson, int low, int high) {
    final List<Fund> fundList = GSON.fromJson(fundListJson, new TypeToken<List<Fund>>() {
    }.getType());

    List<Fund> resList =
        fundList.stream().filter(fund -> fund.getAssetSize() >= low && fund.getAssetSize() <= high)
            .collect(Collectors.toList());

    return GSON.toJson(resList);
  }

  /**
   * 获取指定基金经理运营的基金.
   *
   * @param fundManagersJson 基金经理人json
   */
  public static String getSpecificManagerOperatorFundList(String fundManagersJson)
      throws Exception {
    List<FundManager> fundManagers =
        GSON.fromJson(fundManagersJson, new TypeToken<List<FundManager>>() {
        }.getType());
    List<Fund> resList = new LinkedList<>();

    fundManagers.forEach(fundManager -> resList.addAll(fundManager.getCurrentOperatingFunds()));

    return GSON.toJson(resList);
  }

  /**
   * 从给定的基金json信息中，返回指定基金经理运营的基金.
   *
   * @param fundManagersJson 基金经理人json
   * @param fundJson         全部基金列表
   */
  public static String getSpecificManagerOperatorFundList(String fundManagersJson,
                                                          String fundJson) {
    List<FundManager> fundManagers =
        GSON.fromJson(fundManagersJson, new TypeToken<List<FundManager>>() {
        }.getType());

    Set<String> filterFundCode = new HashSet<>();
    fundManagers.forEach(fundManager -> {
      for (Fund currentOperatingFund : fundManager.getCurrentOperatingFunds()) {
        filterFundCode.add(currentOperatingFund.getFundCode());
      }
    });

    List<Fund> funds = GSON.fromJson(fundJson, new TypeToken<List<Fund>>() {
    }.getType());

    List<Fund> fundList = funds.stream().filter(fund -> filterFundCode.contains(fund.getFundCode()))
        .collect(Collectors.toList());

    return GSON.toJson(fundList);
  }
}
