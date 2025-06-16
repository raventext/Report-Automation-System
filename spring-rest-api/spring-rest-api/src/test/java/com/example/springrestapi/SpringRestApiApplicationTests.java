package com.example.springrestapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import com.example.springrestapi.controller.ApiController;
import com.example.springrestapi.service.ApiService;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringRestApiApplicationTests {

    @Autowired
    private ApiController apiController;

    @Autowired
    private ApiService apiService;

    @Test
    void contextLoads() {
        assertThat(apiController).isNotNull();
        assertThat(apiService).isNotNull();
    }

@Test
void testGetExternalData() {
    // Call the getExternalData method from the ApiController
    ResponseEntity<List<Map<String, Object>>> response = apiController.getExternalData();

    // Assert that the response status code is 200 (OK)
    assertThat(response.getStatusCode().value()).isEqualTo(200);

    // Assert that the response body is not null
    assertThat(response.getBody()).isNotNull();

    // Optionally, assert that the response body contains expected data
    assertThat(response.getBody()).isNotEmpty();
}
}