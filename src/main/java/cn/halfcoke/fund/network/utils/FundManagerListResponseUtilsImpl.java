package cn.halfcoke.fund.network.utils;

import cn.halfcoke.fund.network.protocol.FundManagerListResponse;

/**
 * 基金管理人列表响应数据工具类实现.
 */
public class FundManagerListResponseUtilsImpl extends ResponseUtils<FundManagerListResponse> {
  public FundManagerListResponseUtilsImpl(FundManagerListResponse listResponse) {
    super(listResponse);
  }

  @Override
  public int getPageCount() {
    return this.listResponse.getPages();
  }
}
