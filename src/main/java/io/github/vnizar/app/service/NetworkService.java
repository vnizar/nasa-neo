package io.github.vnizar.app.service;

import org.springframework.http.ResponseEntity;

public interface NetworkService<T> {
    <V> ResponseEntity<V> get(String url, Class<V> responseClass);
}
