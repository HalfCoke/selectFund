package cn.halfcoke.fund.network.utils;

import cn.halfcoke.fund.network.protocol.FundListResponse;

/**
 * 基金列表响应数据工具实现.
 */
public class FundListResponseUtilsImpl extends ResponseUtils<FundListResponse> {

  public FundListResponseUtilsImpl(FundListResponse listResponse) {
    super(listResponse);
  }

  @Override
  public int getPageCount() {
    return this.listResponse.getAllPages();
  }
}
