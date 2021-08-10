package cn.halfcoke.fund.basic;


import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.util.LinkedList;
import java.util.List;

/**
 * 基金经理人对象.
 */
public class FundManager {
  private String name;
  private String id;
  private Integer workingDay;
  private String totalAssetSize;
  private String company;
  private String companyId;
  private List<Fund> currentOperatingFunds;

  public FundManager(String name, String id, Integer workingDay, String totalAssetSize,
                     String company, String companyId) {
    this(name, id, workingDay, totalAssetSize, company, companyId, new LinkedList<>());
  }

  /**
   * 构造函数.
   */
  public FundManager(String name, String id, Integer workingDay, String totalAssetSize,
                     String company, String companyId, List<Fund> currentOperatingFunds) {
    this.name = name;
    this.id = id;
    this.workingDay = workingDay;
    this.totalAssetSize = totalAssetSize;
    this.company = company;
    this.companyId = companyId;
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

  public String getCompanyId() {
    return companyId;
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
        .add("companyID", companyId)
        .add("currentOperatingFunds", currentOperatingFunds)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FundManager)) {
      return false;
    }
    FundManager that = (FundManager) o;
    return Objects.equal(name, that.name)
        && Objects.equal(id, that.id)
        && Objects.equal(workingDay, that.workingDay)
        && Objects.equal(totalAssetSize, that.totalAssetSize)
        && Objects.equal(company, that.company)
        && Objects.equal(companyId, that.companyId)
        && Objects.equal(currentOperatingFunds, that.currentOperatingFunds);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name, id, workingDay, totalAssetSize, company, companyId,
        currentOperatingFunds);
  }
}
