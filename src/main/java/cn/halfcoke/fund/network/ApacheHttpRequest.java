package cn.halfcoke.fund.network;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.charset.StandardCharsets;


public class ApacheHttpRequest implements AutoCloseable {

    private final static Logger LOGGER = LoggerFactory.getLogger(ApacheHttpRequest.class);

    private CloseableHttpClient httpClient;

    ApacheHttpRequest() {
        httpClient = HttpClients.createDefault();
    }

    String get(URIBuilder uriBuilder) throws Exception {
        URI uri = uriBuilder.setCharset(StandardCharsets.UTF_8).build();
        HttpGet httpGet = new HttpGet(uri);
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
