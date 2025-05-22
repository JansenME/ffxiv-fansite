package com.ffxiv_fansite.fansite.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffxiv_fansite.fansite.model.exception.ConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ConnectionService {
    private static final String NEWS_URL = "https://lodestonenews.com/news/topics";

    private static final int MAX_RETRIES = 10;
    private static final List<Integer> RETRYABLE_ERROR_CODES = List.of(500, 501, 502, 503, 504);

    public JsonNode getJsonNodeForNews() {
        return getJsonNodeFromUrl(NEWS_URL);
    }

    private JsonNode getJsonNodeFromUrl(final String url) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readTree(getHttpEntityAsString(url));
        } catch (JsonProcessingException e) {
            return objectMapper.nullNode();
        }
    }

    private String getHttpEntityAsString(final String url) {
        HttpUriRequest request = createRequest(url);

        try (CloseableHttpClient client = createClient();
             CloseableHttpResponse response = client.execute(request)) {
            if (response.getStatusLine().getStatusCode() != 200) {
                log.debug(String.format("The url %s was called, but returned a %s response code", url, response.getStatusLine().getStatusCode()));
                return "";
            }

            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    private HttpUriRequest createRequest(final String url) {
        HttpUriRequest request = new HttpGet(url);
        request.addHeader("accept", "application/json");
        request.addHeader("accept", "text/csv");

        return request;
    }

    private CloseableHttpClient createClient() {
        return HttpClientBuilder.create()
                .setServiceUnavailableRetryStrategy(createServiceUnavailableRetryStrategy())
                .build();
    }

    private ServiceUnavailableRetryStrategy createServiceUnavailableRetryStrategy() {
        return new ServiceUnavailableRetryStrategy() {
            @Override
            public boolean retryRequest(HttpResponse httpResponse, int executionCount, HttpContext httpContext) {
                if(RETRYABLE_ERROR_CODES.contains(httpResponse.getStatusLine().getStatusCode()) && executionCount <= MAX_RETRIES) {
                    log.info(String.format("This is try number %s. The url %s came back with a %s response", executionCount, ((HttpClientContext) httpContext).getRequest().getRequestLine().getUri(), httpResponse.getStatusLine().getStatusCode()));
                    return true;
                }

                return false;
            }

            @Override
            public long getRetryInterval() {
                return 250;
            }
        };
    }
}
