package io.github.vnizar.app.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.util.ForwardedHeaderUtils;

import java.io.IOException;
import java.net.URI;

public class NeoRequestInterceptor implements ClientHttpRequestInterceptor {
    private final String apiKey;

    public NeoRequestInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        HttpRequest newRequest = new HttpRequestWrapper(request) {

            @Override
            public URI getURI() {
                return ForwardedHeaderUtils.adaptFromForwardedHeaders(
                                request.getURI(), request.getHeaders())
                        .queryParam("api_key", apiKey)
                        .build()
                        .toUri();
            }
        };
        System.out.println("URI "+ newRequest.getURI());
        return execution.execute(newRequest, body);
    }
}
