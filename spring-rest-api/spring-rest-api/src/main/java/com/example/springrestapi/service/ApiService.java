package com.example.springrestapi.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
public class ApiService {

    @Value("${external.api.url}")
    private String externalApiUrl;

    @Value("${external.api.token}")
    private String apiToken;

    @Value("${external.api.historian}")
    private String historianApiUrl;

    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> fetchExternalData() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken); // Set the Authorization header

        HttpEntity<String> entity = new HttpEntity<>(headers); // Wrap headers in an HttpEntity

        // Make the GET request
        ResponseEntity<String> response = restTemplate.exchange(
                externalApiUrl,
                HttpMethod.GET,
                entity,
                String.class);

        try {
            // Parse the JSON response into a list of maps
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(
                    response.getBody(),
                    new TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response", e);
        }
    }

    // public Map<String, String> fetchHistorianData(String id, String start, String end, String interpolationResolution) {
    //     // Calculate interpolation resolution based on the time interval
    //     // long interpolationResolution =
    //     // calculateInterpolationResolution(timeInterval);

    //     // Construct the URL with query parameters
    //     String url = String.format(
    //             "%sids=%s&start=%s&end=%s&interpolationresolution=%s&aggregateFunction=Average&interpolationMethod=Step&interpolationresolutiontype=Ticks",
    //             historianApiUrl, id, start, end, interpolationResolution);

    //     System.out.println("Constructed URL: " + url);

    //     HttpHeaders headers = new HttpHeaders();
    //     headers.set("Authorization", "Bearer " + apiToken);

    //     HttpEntity<String> entity = new HttpEntity<>(headers);

    //     try {
    //         // Make the GET request to the external API
    //         ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

    //         // Log the raw response from the external API
    //         System.out.println("Response from external API: " + response.getBody());

    //         ObjectMapper objectMapper = new ObjectMapper();
    //         // Parse the JSON response into a list of maps
    //         List<Map<String, Object>> responseData = objectMapper.readValue(response.getBody(),
    //                 new TypeReference<List<Map<String, Object>>>() {
    //                 });

    //         // Create a map to store the results
    //         Map<String, String> result = new HashMap<>();

    //         // Iterate through the response data
    //         for (Map<String, Object> item : responseData) {
    //             // Extract the epoch value (t)
    //             if (item.containsKey("t")) {
    //                 String epochString = item.get("t").toString();
    //                 long epoch = Long.parseLong(epochString);

    //                 // Convert epoch to human-readable date
    //                 // LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch),
    //                 // ZoneId.systemDefault());
    //                 // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd
    //                 // HH:mm:ss");
    //                 // String formattedDate = dateTime.format(formatter);

    //                 // Use UTC for conversion
    //                 LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneOffset.UTC);
    //                 DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    //                 String formattedDate = dateTime.format(formatter) + "Z"; // Append 'Z' for UTC

    //                 // Add the formatted date and value to the result map
    //                 String value = item.get("v").toString();
    //                 result.put(formattedDate, value);
    //             }
    //         }

    //         return result;
    //     } catch (Exception e) {
    //         System.err.println("Error during API call or data processing: " + e.getMessage());
    //         throw new RuntimeException("Failed to fetch or process data from external API", e);
    //     }
    // }
// public Map<String, Map<String, String>> fetchHistorianData(List<String> ids, String start, String end, String interpolationResolution) {
//     Map<String, Map<String, String>> allResults = new HashMap<>();

//     for (String id : ids) {
//         HttpHeaders headers = new HttpHeaders();
//         headers.set("Authorization", "Bearer " + apiToken);

//         HttpEntity<String> entity = new HttpEntity<>(headers);

//         String url = String.format(
//             "%sids=%s&start=%s&end=%s&interpolationresolution=%s&aggregateFunction=Average&interpolationMethod=Step&interpolationresolutiontype=Ticks",
//             historianApiUrl, id, start, end, interpolationResolution);

//         try {
//             ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

//             ObjectMapper objectMapper = new ObjectMapper();
//             List<Map<String, Object>> responseData = objectMapper.readValue(response.getBody(),
//                 new TypeReference<List<Map<String, Object>>>() {});

//             Map<String, String> result = new LinkedHashMap<>();
//             for (Map<String, Object> item : responseData) {
//                 if (item.containsKey("t") && item.containsKey("v")) {
//                     long epoch = Long.parseLong(item.get("t").toString());
//                     LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneOffset.UTC);
//                     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                     String formattedDate = dateTime.format(formatter);
//                     String value = item.get("v").toString();
//                     result.put(formattedDate, value);
//                 }
//             }
//             allResults.put(id, result);
//         } catch (Exception e) {
//             System.err.println("Error for ID " + id + ": " + e.getMessage());
//             allResults.put(id, Map.of("error", "Failed to fetch/process data for ID " + id));
//         }
//     }
//     return allResults;
// }

public Map<String, Map<String, String>> fetchHistorianData(List<String> tagids, String start, String end, String interpolationResolution) {
    Map<String, Map<String, String>> allResults = new HashMap<>();

    for (String tagid : tagids) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = String.format(
            "%s?tagid=%s&start=%s&end=%s&interpolationresolution=%s&aggregateFunction=Average&interpolationMethod=Step&interpolationresolutiontype=Ticks",
            historianApiUrl, tagid, start, end, interpolationResolution);

        try {
            System.out.println(url);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> responseData = objectMapper.readValue(response.getBody(),
                new TypeReference<List<Map<String, Object>>>() {});

            Map<String, String> result = new LinkedHashMap<>();
            for (Map<String, Object> item : responseData) {
                if (item.containsKey("t") && item.containsKey("v")) {
                    long epoch = Long.parseLong(item.get("t").toString());
                    LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneOffset.UTC);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = dateTime.format(formatter);
                    String value = item.get("v").toString();
                    result.put(formattedDate, value);
                }
            }
            allResults.put(tagid, result);
        } catch (Exception e) {
            System.err.println("Error for tagid " + tagid + ": " + e.getMessage());
            allResults.put(tagid, Map.of("error", "Failed to fetch/process data for tagid " + tagid));
        }
    }
    return allResults;
}
}


