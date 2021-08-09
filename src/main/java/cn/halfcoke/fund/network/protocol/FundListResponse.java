package cn.halfcoke.fund.network.protocol;

import com.google.common.base.MoreObjects;
import java.util.List;

/**
 * 基金列表响应信息.
 */
public class FundListResponse extends AbstractListResponse {
  private List<String> datas;
  private int allRecords;
  private int pageIndex;
  private int pageNum;
  private int allPages;
  private int allNum;
  private int gpNum;
  private int hhNum;
  private int zqNum;
  private int zsNum;
  private int bbNum;
  private int qdiiNum;
  private int etfNum;
  private int lofNum;
  private int fofNum;

  /**
   * 构造函数.
   */
  public FundListResponse(List<String> datas, int allRecords, int pageIndex, int pageNum,
                          int allPages, int allNum, int gpNum, int hhNum, int zqNum, int zsNum,
                          int bbNum, int qdiiNum, int etfNum, int lofNum, int fofNum) {
    this.datas = datas;
    this.allRecords = allRecords;
    this.pageIndex = pageIndex;
    this.pageNum = pageNum;
    this.allPages = allPages;
    this.allNum = allNum;
    this.gpNum = gpNum;
    this.hhNum = hhNum;
    this.zqNum = zqNum;
    this.zsNum = zsNum;
    this.bbNum = bbNum;
    this.qdiiNum = qdiiNum;
    this.etfNum = etfNum;
    this.lofNum = lofNum;
    this.fofNum = fofNum;
  }

  public List<String> getDatas() {
    return datas;
  }

  public int getAllRecords() {
    return allRecords;
  }

  public int getPageIndex() {
    return pageIndex;
  }

  public int getPageNum() {
    return pageNum;
  }

  public int getAllPages() {
    return allPages;
  }

  public int getAllNum() {
    return allNum;
  }

  public int getGpNum() {
    return gpNum;
  }

  public int getHhNum() {
    return hhNum;
  }

  public int getZqNum() {
    return zqNum;
  }

  public int getZsNum() {
    return zsNum;
  }

  public int getBbNum() {
    return bbNum;
  }

  public int getQdiiNum() {
    return qdiiNum;
  }

  public int getEtfNum() {
    return etfNum;
  }

  public int getLofNum() {
    return lofNum;
  }

  public int getFofNum() {
    return fofNum;
  }

  public void setDatas(List<String> datas) {
    this.datas = datas;
  }

  public void setAllRecords(int allRecords) {
    this.allRecords = allRecords;
  }

  public void setPageIndex(int pageIndex) {
    this.pageIndex = pageIndex;
  }

  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }

  public void setAllPages(int allPages) {
    this.allPages = allPages;
  }

  public void setAllNum(int allNum) {
    this.allNum = allNum;
  }

  public void setGpNum(int gpNum) {
    this.gpNum = gpNum;
  }

  public void setHhNum(int hhNum) {
    this.hhNum = hhNum;
  }

  public void setZqNum(int zqNum) {
    this.zqNum = zqNum;
  }

  public void setZsNum(int zsNum) {
    this.zsNum = zsNum;
  }

  public void setBbNum(int bbNum) {
    this.bbNum = bbNum;
  }

  public void setQdiiNum(int qdiiNum) {
    this.qdiiNum = qdiiNum;
  }

  public void setEtfNum(int etfNum) {
    this.etfNum = etfNum;
  }

  public void setLofNum(int lofNum) {
    this.lofNum = lofNum;
  }

  public void setFofNum(int fofNum) {
    this.fofNum = fofNum;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("datas", datas)
        .add("allRecords", allRecords)
        .add("pageIndex", pageIndex)
        .add("pageNum", pageNum)
        .add("allPages", allPages)
        .add("allNum", allNum)
        .add("gpNum", gpNum)
        .add("hhNum", hhNum)
        .add("zqNum", zqNum)
        .add("zsNum", zsNum)
        .add("bbNum", bbNum)
        .add("qdiiNum", qdiiNum)
        .add("etfNum", etfNum)
        .add("lofNum", lofNum)
        .add("fofNum", fofNum)
        .toString();
  }
}
