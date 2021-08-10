package cn.halfcoke.fund.network;

import cn.halfcoke.fund.basic.Fund;
import cn.halfcoke.fund.basic.FundManager;
import cn.halfcoke.fund.network.protocol.FundListResponse;
import cn.halfcoke.fund.network.protocol.FundManagerListResponse;
import cn.halfcoke.fund.network.utils.ResponseUtilsFactory;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 天天基金API数据获取.
 */
public class TianTianWebSite implements AutoCloseable {
  private static final Logger LOGGER = LoggerFactory.getLogger(TianTianWebSite.class);
  public static final String[] GROWTH_RATE_PERIODS =
      new String[] {"今年来", "近1周", "近1月", "近3月", "近6月", "近1年", "近2年", "近3年", "近5年"};

  private static final String HTTP_BASIC_URL = "http://fund.eastmoney.com";

  private final HashMap<Class, Integer> responseStringParseMap;

  private final Gson gson;
  private final ApacheHttpRequest httpRequest;

  private final Pattern establishDatePattern;
  private final Pattern assetPattern;
  private final Map<String, Pattern> growthRatePatternMap;

  /**
   * 构造函数.
   */
  public TianTianWebSite() {
    gson = new GsonBuilder().create();
    httpRequest = new ApacheHttpRequest();

    responseStringParseMap = new HashMap<>();
    responseStringParseMap.put(FundListResponse.class, 14);
    responseStringParseMap.put(FundManagerListResponse.class, 9);

    establishDatePattern = Pattern.compile("成立日期：<span>(\\d\\d\\d\\d-\\d\\d-\\d\\d)</span>");
    assetPattern = Pattern.compile("资产规模：<span>\\s*(.*)亿元");
    growthRatePatternMap = new HashMap<>();

    for (String period : GROWTH_RATE_PERIODS) {
      growthRatePatternMap.put(period, Pattern.compile(
          String.format("<li class='title'>%s</li><li class='.*?'>(.*?)</li>", period)));
    }
  }

  /**
   * 获取基金经理人列表.
   */
  public List<FundManager> getFundManagerList() throws Exception {
    URIBuilder uriBuilder =
        new URIBuilder(HTTP_BASIC_URL + "/Data/FundDataPortfolio_Interface.aspx")
            .setParameter("dt", "14")
            .setParameter("ft", "all")
            .setParameter("sc", "abbname")
            .setParameter("st", "asc")
            .setParameter("pn", "50")
            .setParameter("pi", "1")
            .setParameter("mc", "json");

    List<FundManager> managers = new LinkedList<>();

    int pages = getPageCount(uriBuilder, FundManagerListResponse.class);

    for (int i = 1; i <= pages; i++) {
      String content = httpRequest.get(uriBuilder.setParameter("pi", String.valueOf(i)));
      FundManagerListResponse managerListResponse =
          parseResponseStringToJson(content, FundManagerListResponse.class);
      List<FundManager> list = managerListResponse.getData().stream()
          .map(this::parseFundManager)
          .collect(Collectors.toList());
      managers.addAll(list);
    }
    return managers;
  }

  /**
   * 获取公募基金列表.
   *
   * @return 返回全部公募基金列表
   */
  public List<Fund> getFundList() throws Exception {
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime nowDateTime = LocalDateTime.now();
    LocalDateTime dateTime = LocalDateTime.of(nowDateTime.getYear() - 5, nowDateTime.getMonth(),
        nowDateTime.getDayOfMonth(),
        nowDateTime.getHour(), nowDateTime.getMinute());

    URIBuilder uriBuilder = new URIBuilder(HTTP_BASIC_URL + "/data/rankhandler.aspx")
        .setParameter("op", "ph")
        .setParameter("dt", "kf")
        .setParameter("ft", "all")
        .setParameter("rs", "")
        .setParameter("gs", "0")
        .setParameter("sc", "6yzf")
        .setParameter("st", "desc")
        .setParameter("qdii", "")
        .setParameter("tabSubtype", ",,,,,")
        .setParameter("pi", "1")
        .setParameter("pn", "50")
        .setParameter("v", "0.5176398040170527")
        .setParameter("sd", timeFormatter.format(dateTime))
        .setParameter("ed", timeFormatter.format(nowDateTime))
        .setParameter("dx", "1");
    List<Fund> funds = new LinkedList<>();
    int pages = getPageCount(uriBuilder, FundListResponse.class);
    for (int i = 1; i <= pages; i++) {
      String content = httpRequest.get(uriBuilder.setParameter("pi", String.valueOf(i)));
      FundListResponse fundListResponse =
          parseResponseStringToJson(content, FundListResponse.class);
      List<Fund> list = fundListResponse.getDatas().stream()
          .map(this::parseFund)
          .collect(Collectors.toList());
      funds.addAll(list);
    }
    return funds;
  }

  /**
   * 补充基金中的资金规模及创建日期字段.
   */
  public void addFundInformation(Fund fund) throws Exception {
    final URIBuilder uriBuilder =
        new URIBuilder("http://fundf10.eastmoney.com/jdzf_" + fund.getFundCode() + ".html");
    final String htmlContent = httpRequest.get(uriBuilder);

    Matcher dateMatcher = establishDatePattern.matcher(htmlContent);
    String establishDate = "2021-08-10";
    if (dateMatcher.find()) {
      establishDate = dateMatcher.group(1);
    }

    Matcher assetMatcher = assetPattern.matcher(htmlContent);
    String assetSize = "0";
    if (assetMatcher.find()) {
      assetSize = assetMatcher.group(1);
    }
    fund.setAssetSize(Float.parseFloat(assetSize));
    fund.setEstablishDate(establishDate);
    LOGGER.info("After Add: " + fund);
  }

  // ----------------------------------------------------------------------------------------------
  // 工具方法
  // ----------------------------------------------------------------------------------------------
  private FundManager parseFundManager(List<String> manager) {
    String id = manager.get(0);
    String name = manager.get(1);
    String companyId = manager.get(2);
    String company = manager.get(3);
    String[] fundCodes = manager.get(4).split(",");
    String[] fundName = manager.get(5).split(",");
    int workingDay = Integer.parseInt(manager.get(6));
    String totalAssetSize = manager.get(10);

    LinkedList<Fund> funds = new LinkedList<>();
    for (int i = 0; i < fundCodes.length; i++) {
      funds.add(new Fund(fundCodes[i], fundName[i]));
    }

    return new FundManager(name, id, workingDay, totalAssetSize, company, companyId, funds);
  }

  private Fund parseFund(String fundString) {
    String[] split = fundString.split(",");
    String fundCode = split[0];
    String fundName = split[1];
    float nav = parseFloatString(split[4]);
    float totalNav = parseFloatString(split[5]);
    float dayGrowthRate = parseFloatString(split[6]);
    float weekGrowthRate = parseFloatString(split[7]);
    float monthGrowthRate = parseFloatString(split[8]);
    float threeMonthGrowthRate = parseFloatString(split[9]);
    float sixMonthGrowthRate = parseFloatString(split[10]);
    float yearGrowthRate = parseFloatString(split[11]);
    float twoYearGrowthRate = parseFloatString(split[12]);
    float threeYearGrowthRate = parseFloatString(split[13]);
    float curYearGrowthRate = parseFloatString(split[14]);
    float establishGrowthRate = parseFloatString(split[15]);
    float fiveYearGrowthRate = parseFloatString(split[18]);

    return new Fund(
        fundCode,
        fundName,
        nav,
        totalNav,
        dayGrowthRate,
        weekGrowthRate,
        monthGrowthRate,
        threeMonthGrowthRate,
        sixMonthGrowthRate,
        yearGrowthRate,
        twoYearGrowthRate,
        threeYearGrowthRate,
        curYearGrowthRate,
        establishGrowthRate,
        fiveYearGrowthRate);
  }

  private <T> int getPageCount(URIBuilder uriBuilder, Class<T> clazz) throws Exception {
    String content = httpRequest.get(uriBuilder);
    return ResponseUtilsFactory.getResponseUtils(parseResponseStringToJson(content, clazz))
        .getPageCount();
  }

  private <T> T parseResponseStringToJson(String content, Class<T> clazz) {
    return gson.fromJson(content.substring(responseStringParseMap.get(clazz)).replaceAll(";", " "),
        clazz);
  }

  private float parseFloatString(String str) {
    return Float.parseFloat(Strings.isNullOrEmpty(str) ? "0" : str);

  }

  private String matchRegexGrowthRate(String period, String htmlContent) {
    Pattern pattern = growthRatePatternMap.get(period);
    Matcher matcher = pattern.matcher(htmlContent);
    matcher.find();
    return matcher.group(1);
  }

  @Override
  public void close() throws Exception {
    httpRequest.close();
  }
}
