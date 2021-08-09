package cn.halfcoke.fund.network;

import java.util.List;

public class FundManagerListResponse {
    private List<List<String>> data;
    private int record;
    private int pages;
    private int curpage;

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
}
