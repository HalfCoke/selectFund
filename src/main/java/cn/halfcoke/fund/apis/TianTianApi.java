package cn.halfcoke.fund.apis;

import cn.halfcoke.fund.basic.Fund;
import cn.halfcoke.fund.basic.FundManager;
import cn.halfcoke.fund.network.TianTianWebSite;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;

/**
 * 天天基金数据API.
 */
public class TianTianApi {
  static TianTianWebSite SITE = new TianTianWebSite();

  /**
   * 返回json格式的经理人列表.
   */
  public static String getFundManagerList() throws Exception {
    // 获取所有基金管理人列表
    List<FundManager> fundManagerList = SITE.getFundManagerList();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(fundManagerList);
  }

  /**
   * 返回json格式的基金列表.
   */
  public static String getFundList() throws Exception {
    // 获取所有基金列表
    List<Fund> fundList = SITE.getFundList();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(fundList);
  }

  public static String getFundInformation(String fundNumber) {
    return "";
  }
}
