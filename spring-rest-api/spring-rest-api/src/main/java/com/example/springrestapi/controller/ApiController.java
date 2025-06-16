package com.example.springrestapi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springrestapi.service.ApiService;

@RestController
@RequestMapping("/api")
public class ApiController {

    private ApiService apiService;


    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/testing")
    public ResponseEntity<List<Map<String, Object>>> getExternalData() {
        List<Map<String, Object>> data = apiService.fetchExternalData();
        return ResponseEntity.ok(data);
    }
    

@GetMapping("/historian/v2/trendvalues")
public ResponseEntity<Map<String, Object>> getHistorianData(
        @RequestParam(value = "tagid", required = true) List<String> ids,
        @RequestParam(value = "start", required = true) String start,
        @RequestParam(value = "end", required = true) String end,
        @RequestParam(value = "interpolationresolution", required = true) String interpolationResolution) {
    try {
        Map<String, Map<String, String>> result = apiService.fetchHistorianData(
                ids, start, end, interpolationResolution);

        return ResponseEntity.ok(Map.of("result", result));
    } catch (Exception e) {
        System.err.println("Error in getHistorianData: " + e.getMessage());
        return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
    }
}
}