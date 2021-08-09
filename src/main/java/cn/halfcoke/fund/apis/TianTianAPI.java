package cn.halfcoke.fund.apis;

import cn.halfcoke.fund.basic.FundManager;
import cn.halfcoke.fund.network.TianTianWebSite;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class TianTianAPI {
    public static String getFundManagerList() throws Exception {
        TianTianWebSite site = new TianTianWebSite();
        // 获取所有基金管理人列表
        List<FundManager> fundManagerList = site.getFundManagerList();
        // 存入json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(fundManagerList);
    }
}
