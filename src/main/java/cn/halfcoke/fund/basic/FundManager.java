package cn.halfcoke.fund.basic;


import com.google.common.base.MoreObjects;

import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class FundManager {
    private String name;
    private String id;
    private Integer workingDay;
    private String totalAssetSize;
    private String company;
    private String companyID;
    private List<Fund> currentOperatingFunds;

    public FundManager(String name, String id, Integer workingDay, String totalAssetSize, String company, String companyID) {
        this(name, id, workingDay, totalAssetSize, company, companyID, new LinkedList<>());
    }

    public FundManager(String name, String id, Integer workingDay, String totalAssetSize, String company, String companyID, List<Fund> currentOperatingFunds) {
        this.name = name;
        this.id = id;
        this.workingDay = workingDay;
        this.totalAssetSize = totalAssetSize;
        this.company = company;
        this.companyID = companyID;
        this.currentOperatingFunds = currentOperatingFunds;
    }

    public void addFund(Fund fund) {
        checkNotNull(fund);
        this.currentOperatingFunds.add(fund);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Integer getWorkingDay() {
        return workingDay;
    }

    public String getTotalAssetSize() {
        return totalAssetSize;
    }

    public String getCompany() {
        return company;
    }

    public String getCompanyID() {
        return companyID;
    }

    public List<Fund> getCurrentOperatingFunds() {
        return currentOperatingFunds;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("id", id)
                .add("workingDay", workingDay)
                .add("totalAssetSize", totalAssetSize)
                .add("company", company)
                .add("companyID", companyID)
                .add("currentOperatingFunds", currentOperatingFunds)
                .toString();
    }
}
