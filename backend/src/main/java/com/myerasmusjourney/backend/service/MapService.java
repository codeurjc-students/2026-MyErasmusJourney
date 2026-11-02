package com.myerasmusjourney.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.myerasmusjourney.backend.config.GeoapifyProperties;
import com.myerasmusjourney.backend.dto.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class MapService {

    @Autowired
    private GeoapifyProperties geoapifyProperties;

    public LocationDTO geoLocateCity(String city, String country) {
        RestClient restClient = RestClient.create();

        JsonNode response = restClient.get()
                .uri("https://api.geoapify.com/v1/geocode/search?city={city}&country={country}&lang=en&apiKey={apiKey}",
                        city, country, geoapifyProperties.apiKey())
                .retrieve()
                .body(JsonNode.class);

        if (response == null) return null;
        JsonNode properties = response.path("features").path(0).path("properties");
        if (properties.isMissingNode())return null;

        return new LocationDTO(
                properties.path("lat").asDouble(),
                properties.path("lon").asDouble()
        );
    }
}
