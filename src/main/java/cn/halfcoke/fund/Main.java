package cn.halfcoke.fund;

import cn.halfcoke.fund.apis.TianTianApi;
import cn.halfcoke.fund.basic.Fund;
import cn.halfcoke.fund.basic.FundManager;
import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 测试功能使用main.
 */
public class Main {
  /**
   * 功能测试使用.
   */
  public static void main(String[] args) throws Exception {
    new Main().demo();
    try (final FileReader fileReader = new FileReader("./res.json");
         final FileWriter fileWriter = new FileWriter("./sortedRes.json")) {
      Scanner scanner = new Scanner(fileReader);
      StringBuilder builder = new StringBuilder();
      while (scanner.hasNext()) {
        builder.append(scanner.nextLine());
      }
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      List<Fund> fundList = gson.fromJson(builder.toString(), new TypeToken<List<Fund>>() {
      }.getType());

      fundList.sort((o1, o2) -> Float.compare(o2.getYearGrowthRate(), o1.getYearGrowthRate()));

      fileWriter.write(gson.toJson(fundList));
    }
  }

  public void demo() throws Exception {
    // 获取基金列表，并写入json
    try (final FileWriter fundWriter = new FileWriter("./fundList.json")) {
      String fundList = TianTianApi.getFundList();
      fundWriter.write(fundList);
    }
    // 获取基金经理人列表，并写入json
    try (final FileWriter fundManagerWriter = new FileWriter("fundManagerList.json")) {
      String fundManagerList = TianTianApi.getFundManagerList();
      fundManagerWriter.write(fundManagerList);
    }
    // 获取全部信息，并写入json
    try (final FileWriter totalFundManagersInfoWriter = new FileWriter("./totalManagers.json")) {
      String totalFundManagerList = TianTianApi.getTotalFundManagerList();
      totalFundManagersInfoWriter.write(totalFundManagerList);
    }

    // 获取以下规则的基金(有递进关系)
    // 1. 经理人从业5年以上
    // 2. 基金成立5年以上
    // 3. 经理人近2年操盘的成立5年以上的基金平均收益率在前30%，近5年排20%
    // 4. 获得以上经理人的所有基金，然后选出在"近1月"、"近6月"....等等等收益率排名前30%的基金
    List<Fund> someFund = getSomeFund();
    try (FileWriter fileWriter = new FileWriter("./res.json")) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      fileWriter.write(gson.toJson(someFund));
    }

  }

  public List<Fund> getSomeFund() {

    List<Fund> res = null;
    try (FileReader managers = new FileReader("./totalManagers.json")) {

      final StringBuilder managersBuilder = new StringBuilder();
      final Scanner managersScanner = new Scanner(managers);
      while (managersScanner.hasNext()) {
        managersBuilder.append(managersScanner.nextLine());
      }

      Gson gson = new GsonBuilder().setPrettyPrinting().create();

      // 筛选从业5年以上的基金经理
      String filteredManagers =
          TianTianApi.getSpecificWorkYearFundManagers(5, managersBuilder.toString());

      // 获取5年以上基金经理所运营的基金列表
      String filteredFunds =
          TianTianApi.getSpecificManagerOperatorFundList(filteredManagers);

      // 获取5年以上的基金列表
      String greaterYearFundList = TianTianApi.getGreaterYearFundList(filteredFunds, 5);
      // 获取指定资金范围内的基金
      String assetIntervalFundList =
          TianTianApi.getAssetIntervalFundList(greaterYearFundList, 2, 60);

      // 获取从业5年以上的基金经理在运营的5年以上的基金排名中，收益率排名前20%的基金经理名单
      String yearFundManagers =
          TianTianApi.getHeadEarnManagers(filteredManagers, assetIntervalFundList, "近2年", 0.3f);

      String fiveYearFundManagers =
          TianTianApi.getHeadEarnManagers(filteredManagers, assetIntervalFundList, "近5年", 0.2f);

      List<FundManager> yearFundManagersList =
          gson.fromJson(yearFundManagers, new TypeToken<List<FundManager>>() {
          }.getType());
      List<FundManager> fiveYearFundManagersList =
          gson.fromJson(fiveYearFundManagers, new TypeToken<List<FundManager>>() {
          }.getType());

      Sets.SetView<FundManager> fundManagers =
          Sets.intersection(new HashSet<>(yearFundManagersList),
              new HashSet<>(fiveYearFundManagersList));

      List<Fund> fundList = new LinkedList<>();

      for (FundManager fundManager : fundManagers) {
        fundList.addAll(fundManager.getCurrentOperatingFunds());
      }
      float rate = 0.3f;

      List<Fund> threeMonthGrowthRate = fundList.stream()
          .sorted(
              (o1, o2) -> Float.compare(o2.getThreeMonthGrowthRate(), o1.getThreeMonthGrowthRate()))
          .collect(Collectors.toList());

      List<Fund> sixMonthGrowthRate = fundList.stream()
          .sorted(
              (o1, o2) -> Float.compare(o2.getSixMonthGrowthRate(), o1.getSixMonthGrowthRate()))
          .collect(Collectors.toList());

      List<Fund> yearGrowthRate = fundList.stream()
          .sorted(
              (o1, o2) -> Float.compare(o2.getYearGrowthRate(), o1.getYearGrowthRate()))
          .collect(Collectors.toList());

      List<Fund> twoYearGrowthRate = fundList.stream()
          .sorted(
              (o1, o2) -> Float.compare(o2.getTwoYearGrowthRate(), o1.getTwoYearGrowthRate()))
          .collect(Collectors.toList());

      List<Fund> threeYearGrowthRate = fundList.stream()
          .sorted(
              (o1, o2) -> Float.compare(o2.getThreeYearGrowthRate(), o1.getThreeYearGrowthRate()))
          .collect(Collectors.toList());

      Set<Fund> threeMonthGrowthRateSet = new HashSet<>(getHeadFund(threeMonthGrowthRate, rate));
      Set<Fund> sixMonthGrowthRateSet = new HashSet<>(getHeadFund(sixMonthGrowthRate, rate));
      Set<Fund> yearGrowthRateSet = new HashSet<>(getHeadFund(yearGrowthRate, rate));
      Set<Fund> twoYearGrowthRateSet = new HashSet<>(getHeadFund(twoYearGrowthRate, rate));
      Set<Fund> threeYearGrowthRateSet = new HashSet<>(getHeadFund(threeYearGrowthRate, rate));


      Sets.SetView<Fund> intersection =
          Sets.intersection(threeMonthGrowthRateSet, sixMonthGrowthRateSet);
      Sets.SetView<Fund> intersection1 = Sets.intersection(intersection, yearGrowthRateSet);
      Sets.SetView<Fund> intersection2 = Sets.intersection(intersection1, twoYearGrowthRateSet);
      Sets.SetView<Fund> intersection3 = Sets.intersection(intersection2, threeYearGrowthRateSet);

      res = new LinkedList<>(intersection3);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return res;
  }

  private List<Fund> getHeadFund(List<Fund> fundList, float rate) {
    int index = (int) (fundList.size() * rate);
    return fundList.subList(0, index);
  }

}
