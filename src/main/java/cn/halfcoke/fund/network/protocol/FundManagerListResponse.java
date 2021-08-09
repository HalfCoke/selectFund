package cn.halfcoke.fund.network.protocol;

import com.google.common.base.MoreObjects;
import java.util.List;

/**
 * 基金经理人列表响应信息.
 */
public class FundManagerListResponse extends AbstractListResponse {
  private List<List<String>> data;
  private int record;
  private int pages;
  private int curpage;

  /**
   * 构造函数.
   */
  public FundManagerListResponse(List<List<String>> data, int record, int pages, int curpage) {
    this.data = data;
    this.record = record;
    this.pages = pages;
    this.curpage = curpage;
  }

  public List<List<String>> getData() {
    return data;
  }

  public int getRecord() {
    return record;
  }

  public int getPages() {
    return pages;
  }

  public int getCurpage() {
    return curpage;
  }

  public void setData(List<List<String>> data) {
    this.data = data;
  }

  public void setRecord(int record) {
    this.record = record;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public void setCurpage(int curpage) {
    this.curpage = curpage;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("data", data)
        .add("record", record)
        .add("pages", pages)
        .add("curpage", curpage)
        .toString();
  }
}
