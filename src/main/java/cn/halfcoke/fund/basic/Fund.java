package cn.halfcoke.fund.basic;

import com.google.common.base.MoreObjects;

public class Fund {
    private String fundCode;
    private String fundName;

    public Fund(String fundCode, String fundName) {
        this.fundCode = fundCode;
        this.fundName = fundName;
    }

    public String getFundCode() {
        return fundCode;
    }

    public String getFundName() {
        return fundName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("fundCode", fundCode)
                .add("fundName", fundName)
                .toString();
    }
}
