package cn.halfcoke.fund.basic;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * 基金基本信息类.
 */
public class Fund {
  private String fundCode;
  private String fundName;
  private String establishDate;
  private float assetSize;
  private float nav;
  private float totalNav;
  private float dayGrowthRate;
  private float weekGrowthRate;
  private float monthGrowthRate;
  private float threeMonthGrowthRate;
  private float sixMonthGrowthRate;
  private float yearGrowthRate;
  private float twoYearGrowthRate;
  private float threeYearGrowthRate;
  private float curYearGrowthRate;
  private float establishGrowthRate;
  private float fiveYearGrowthRate;

  /**
   * 构造函数.
   */
  public Fund(String fundCode, String fundName) {
    this(fundCode,
        fundName,
        "",
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0);
  }


  /**
   * 构造函数.
   */
  public Fund(String fundCode,
              String fundName,
              float nav,
              float totalNav,
              float dayGrowthRate,
              float weekGrowthRate,
              float monthGrowthRate,
              float threeMonthGrowthRate,
              float sixMonthGrowthRate,
              float yearGrowthRate,
              float twoYearGrowthRate,
              float threeYearGrowthRate,
              float curYearGrowthRate,
              float establishGrowthRate,
              float fiveYearGrowthRate) {
    this(fundCode,
        fundName,
        "",
        0,
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

  /**
   * 构造函数.
   */
  public Fund(String fundCode,
              String fundName,
              String establishDate,
              float assetSize,
              float nav,
              float totalNav,
              float dayGrowthRate,
              float weekGrowthRate,
              float monthGrowthRate,
              float threeMonthGrowthRate,
              float sixMonthGrowthRate,
              float yearGrowthRate,
              float twoYearGrowthRate,
              float threeYearGrowthRate,
              float curYearGrowthRate,
              float establishGrowthRate,
              float fiveYearGrowthRate) {
    this.fundCode = fundCode;
    this.fundName = fundName;
    this.establishDate = establishDate;
    this.assetSize = assetSize;
    this.nav = nav;
    this.totalNav = totalNav;
    this.dayGrowthRate = dayGrowthRate;
    this.weekGrowthRate = weekGrowthRate;
    this.monthGrowthRate = monthGrowthRate;
    this.threeMonthGrowthRate = threeMonthGrowthRate;
    this.sixMonthGrowthRate = sixMonthGrowthRate;
    this.yearGrowthRate = yearGrowthRate;
    this.twoYearGrowthRate = twoYearGrowthRate;
    this.threeYearGrowthRate = threeYearGrowthRate;
    this.curYearGrowthRate = curYearGrowthRate;
    this.establishGrowthRate = establishGrowthRate;
    this.fiveYearGrowthRate = fiveYearGrowthRate;
  }

  public String getFundCode() {
    return fundCode;
  }

  public String getFundName() {
    return fundName;
  }

  public String getEstablishDate() {
    return establishDate;
  }

  public float getAssetSize() {
    return assetSize;
  }

  public float getNav() {
    return nav;
  }

  public float getTotalNav() {
    return totalNav;
  }

  public float getDayGrowthRate() {
    return dayGrowthRate;
  }

  public float getWeekGrowthRate() {
    return weekGrowthRate;
  }

  public float getMonthGrowthRate() {
    return monthGrowthRate;
  }

  public float getThreeMonthGrowthRate() {
    return threeMonthGrowthRate;
  }

  public float getSixMonthGrowthRate() {
    return sixMonthGrowthRate;
  }

  public float getYearGrowthRate() {
    return yearGrowthRate;
  }

  public float getTwoYearGrowthRate() {
    return twoYearGrowthRate;
  }

  public float getThreeYearGrowthRate() {
    return threeYearGrowthRate;
  }

  public float getCurYearGrowthRate() {
    return curYearGrowthRate;
  }

  public float getEstablishGrowthRate() {
    return establishGrowthRate;
  }

  public float getFiveYearGrowthRate() {
    return fiveYearGrowthRate;
  }

  public void setFundCode(String fundCode) {
    this.fundCode = fundCode;
  }

  public void setFundName(String fundName) {
    this.fundName = fundName;
  }

  public void setEstablishDate(String establishDate) {
    this.establishDate = establishDate;
  }

  public void setAssetSize(float assetSize) {
    this.assetSize = assetSize;
  }

  public void setNav(float nav) {
    this.nav = nav;
  }

  public void setTotalNav(float totalNav) {
    this.totalNav = totalNav;
  }

  public void setDayGrowthRate(float dayGrowthRate) {
    this.dayGrowthRate = dayGrowthRate;
  }

  public void setWeekGrowthRate(float weekGrowthRate) {
    this.weekGrowthRate = weekGrowthRate;
  }

  public void setMonthGrowthRate(float monthGrowthRate) {
    this.monthGrowthRate = monthGrowthRate;
  }

  public void setThreeMonthGrowthRate(float threeMonthGrowthRate) {
    this.threeMonthGrowthRate = threeMonthGrowthRate;
  }

  public void setSixMonthGrowthRate(float sixMonthGrowthRate) {
    this.sixMonthGrowthRate = sixMonthGrowthRate;
  }

  public void setYearGrowthRate(float yearGrowthRate) {
    this.yearGrowthRate = yearGrowthRate;
  }

  public void setTwoYearGrowthRate(float twoYearGrowthRate) {
    this.twoYearGrowthRate = twoYearGrowthRate;
  }

  public void setThreeYearGrowthRate(float threeYearGrowthRate) {
    this.threeYearGrowthRate = threeYearGrowthRate;
  }

  public void setCurYearGrowthRate(float curYearGrowthRate) {
    this.curYearGrowthRate = curYearGrowthRate;
  }

  public void setEstablishGrowthRate(float establishGrowthRate) {
    this.establishGrowthRate = establishGrowthRate;
  }

  public void setFiveYearGrowthRate(float fiveYearGrowthRate) {
    this.fiveYearGrowthRate = fiveYearGrowthRate;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("fundCode", fundCode)
        .add("fundName", fundName)
        .add("establishDate", establishDate)
        .add("assetSize", assetSize)
        .add("nav", nav)
        .add("totalNav", totalNav)
        .add("dayGrowthRate", dayGrowthRate)
        .add("weekGrowthRate", weekGrowthRate)
        .add("monthGrowthRate", monthGrowthRate)
        .add("threeMonthGrowthRate", threeMonthGrowthRate)
        .add("sixMonthGrowthRate", sixMonthGrowthRate)
        .add("yearGrowthRate", yearGrowthRate)
        .add("twoYearGrowthRate", twoYearGrowthRate)
        .add("threeYearGrowthRate", threeYearGrowthRate)
        .add("curYearGrowthRate", curYearGrowthRate)
        .add("establishGrowthRate", establishGrowthRate)
        .add("fiveYearGrowthRate", fiveYearGrowthRate)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Fund)) {
      return false;
    }
    Fund fund = (Fund) o;
    return Float.compare(fund.assetSize, assetSize) == 0
        && Float.compare(fund.nav, nav) == 0
        && Float.compare(fund.totalNav, totalNav) == 0
        && Float.compare(fund.dayGrowthRate, dayGrowthRate) == 0
        && Float.compare(fund.weekGrowthRate, weekGrowthRate) == 0
        && Float.compare(fund.monthGrowthRate, monthGrowthRate) == 0
        && Float.compare(fund.threeMonthGrowthRate, threeMonthGrowthRate) == 0
        && Float.compare(fund.sixMonthGrowthRate, sixMonthGrowthRate) == 0
        && Float.compare(fund.yearGrowthRate, yearGrowthRate) == 0
        && Float.compare(fund.twoYearGrowthRate, twoYearGrowthRate) == 0
        && Float.compare(fund.threeYearGrowthRate, threeYearGrowthRate) == 0
        && Float.compare(fund.curYearGrowthRate, curYearGrowthRate) == 0
        && Float.compare(fund.establishGrowthRate, establishGrowthRate) == 0
        && Float.compare(fund.fiveYearGrowthRate, fiveYearGrowthRate) == 0
        && Objects.equal(fundCode, fund.fundCode)
        && Objects.equal(fundName, fund.fundName)
        && Objects.equal(establishDate, fund.establishDate);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(fundCode, fundName, establishDate, assetSize, nav, totalNav,
        dayGrowthRate, weekGrowthRate, monthGrowthRate, threeMonthGrowthRate, sixMonthGrowthRate,
        yearGrowthRate, twoYearGrowthRate, threeYearGrowthRate, curYearGrowthRate,
        establishGrowthRate, fiveYearGrowthRate);
  }
}
