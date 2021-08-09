package cn.halfcoke.fund;

import cn.halfcoke.fund.apis.TianTianApi;
import java.io.FileWriter;

/**
 * 测试功能使用main.
 */
public class Main {
  /**
   * 功能测试使用.
   */
  public static void main(String[] args) throws Exception {

    try (FileWriter fileWriter = new FileWriter("./fundList.json")) {
      fileWriter.write(TianTianApi.getFundList());
    }
  }
}
