package io.github.vnizar.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NetworkServiceImpl implements NetworkService {

    @Autowired
    @Qualifier("neoRestTemplate")
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity get(String url, Class responseClass) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(headers);

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                responseClass
        );
    }
}
