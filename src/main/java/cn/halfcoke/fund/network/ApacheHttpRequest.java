package cn.halfcoke.fund.network;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 封装apache http client.
 */
public class ApacheHttpRequest implements AutoCloseable {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApacheHttpRequest.class);

  private final CloseableHttpClient httpClient;

  ApacheHttpRequest() {
    httpClient = HttpClients.createDefault();
  }

  String get(URIBuilder uriBuilder) throws Exception {
    URI uri = uriBuilder.setCharset(StandardCharsets.UTF_8).build();
    HttpGet httpGet = new HttpGet(uri);
    httpGet.addHeader("User-Agent",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) "
            + "Chrome/91.0.4472.114 Safari/537.36");
    httpGet.addHeader("Referer", "http://fund.eastmoney.com/data/fundranking.html");
    CloseableHttpResponse response = null;
    String content = null;
    try {
      response = httpClient.execute(httpGet);
      LOGGER.info("Request:{}", uri.toString());
      if (response.getStatusLine().getStatusCode() == 200) {
        content = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
      }
    } finally {
      if (response != null) {
        response.close();
      }
    }
    return content;
  }

  @Override
  public void close() throws Exception {
    httpClient.close();
  }
}
