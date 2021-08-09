package cn.halfcoke.fund.basic;

import com.google.common.base.MoreObjects;

/**
 * 基金基本信息类.
 */
public class Fund {
  private String fundCode;
  private String fundName;
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

  /**
   * 构造函数.
   */
  public Fund(String fundCode, String fundName) {
    this(fundCode, fundName,
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
  public Fund(String fundCode, String fundName,
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
              float establishGrowthRate) {
    this.fundCode = fundCode;
    this.fundName = fundName;
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
  }

  public String getFundCode() {
    return fundCode;
  }

  public String getFundName() {
    return fundName;
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

  public void setFundCode(String fundCode) {
    this.fundCode = fundCode;
  }

  public void setFundName(String fundName) {
    this.fundName = fundName;
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

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("fundCode", fundCode)
        .add("fundName", fundName)
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
        .toString();
  }
}
