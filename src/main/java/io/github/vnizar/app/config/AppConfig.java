package io.github.vnizar.app.config;

import io.github.vnizar.app.interceptor.NeoRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean("neoRestTemplate")
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (interceptors.isEmpty()) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new NeoRequestInterceptor("M6WpETazyUwGLqqCjppp85RNZl4RbQdSzbfvZc1c"));
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }
}
