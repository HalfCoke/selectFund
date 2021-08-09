package cn.halfcoke.fund.network;

import cn.halfcoke.fund.basic.Fund;
import cn.halfcoke.fund.basic.FundManager;
import com.google.gson.Gson;
import org.apache.http.client.utils.URIBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TianTianWebSite implements AutoCloseable {
    private String HTTP_BASIC_URL = "http://fund.eastmoney.com/Data/FundDataPortfolio_Interface.aspx";

    private Gson gson;
    private ApacheHttpRequest httpRequest;

    public TianTianWebSite() {
        gson = new Gson();
        httpRequest = new ApacheHttpRequest();
    }

    public List<FundManager> getFundManagerList() throws Exception {
        URIBuilder uriBuilder = new URIBuilder(HTTP_BASIC_URL)
                .setParameter("dt", "14")
                .setParameter("ft", "all")
                .setParameter("sc", "abbname")
                .setParameter("st", "asc")
                .setParameter("pn", "50")
                .setParameter("pi", "1")
                .setParameter("mc", "json");

        List<FundManager> managers = new LinkedList<>();

        int pages = getFundManagerPageCount(uriBuilder);

        for (int i = 1; i <= pages; i++) {
            String content = httpRequest.get(uriBuilder.setParameter("pi", String.valueOf(i)));
            FundManagerListResponse managerListResponse =
                    gson.fromJson(
                            content.substring(9).trim(),
                            FundManagerListResponse.class);
            List<FundManager> list = managerListResponse.getData().stream()
                    .map(this::parseFundManager)
                    .collect(Collectors.toList());
            managers.addAll(list);
        }
        return managers;
    }

    private FundManager parseFundManager(List<String> manager) {
        String id = manager.get(0);
        String name = manager.get(1);
        String companyID = manager.get(2);
        String company = manager.get(3);
        String[] fundCodes = manager.get(4).split(",");
        String[] fundName = manager.get(5).split(",");
        int workingDay = Integer.parseInt(manager.get(6));
        String totalAssetSize = manager.get(10);

        LinkedList<Fund> funds = new LinkedList<Fund>();
        for (int i = 0; i < fundCodes.length; i++) {
            funds.add(new Fund(fundCodes[i], fundName[i]));
        }

        return new FundManager(name, id, workingDay, totalAssetSize, company, companyID, funds);
    }

    private int getFundManagerPageCount(URIBuilder uriBuilder) throws Exception {
        String content = httpRequest.get(uriBuilder);
        FundManagerListResponse managerListResponse =
                gson.fromJson(
                        content.substring(9).trim(),
                        FundManagerListResponse.class);
        return managerListResponse.getPages();
    }

    @Override
    public void close() throws Exception {
        httpRequest.close();
    }
}
