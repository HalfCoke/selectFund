package cn.halfcoke.fund.network.utils;

import cn.halfcoke.fund.network.protocol.AbstractListResponse;

/**
 * 响应信息处理工具基类.
 */
public abstract class ResponseUtils<T extends AbstractListResponse> {
  protected T listResponse;

  public ResponseUtils(T listResponse) {
    this.listResponse = listResponse;
  }

  public abstract int getPageCount();
}
