package com.example.transaction.commision.configuration.common;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RootUriTemplateHandler;

public class HttpConfig {

    public static TestRestTemplate getTestRestTemplate() {
        var endpoint = "localhost";
        var port = 8080;
        var restTemplate = new TestRestTemplate();
        restTemplate.setUriTemplateHandler(new RootUriTemplateHandler(String.format("http://%s:%s", endpoint, port)));
        return restTemplate;
    }
}
