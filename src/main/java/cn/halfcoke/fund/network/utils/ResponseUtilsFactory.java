package cn.halfcoke.fund.network.utils;

import cn.halfcoke.fund.network.protocol.AbstractListResponse;
import cn.halfcoke.fund.network.protocol.FundListResponse;
import cn.halfcoke.fund.network.protocol.FundManagerListResponse;

/**
 * 工具类工厂.
 */
public class ResponseUtilsFactory {
  /**
   * 根据响应类型返回响应信息的工具类.
   */
  public static <T> ResponseUtils<? extends AbstractListResponse> getResponseUtils(T response) {
    if (response instanceof FundListResponse) {
      return new FundListResponseUtilsImpl((FundListResponse) response);
    } else if (response instanceof FundManagerListResponse) {
      return new FundManagerListResponseUtilsImpl((FundManagerListResponse) response);
    } else {
      throw new UnsupportedOperationException();
    }
  }
}
