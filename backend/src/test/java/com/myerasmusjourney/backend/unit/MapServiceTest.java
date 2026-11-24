package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.config.GeoapifyProperties;
import com.myerasmusjourney.backend.dto.LocationDTO;
import com.myerasmusjourney.backend.service.MapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@Tag("unit")
public class MapServiceTest {

    private MapService mapService;
    private MockRestServiceServer server;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder();

        server = MockRestServiceServer.bindTo(builder).build();

        RestClient restClient = builder.build();

        mapService = new MapService();

        ReflectionTestUtils.setField(mapService, "restClient", restClient);
        ReflectionTestUtils.setField(
                mapService,
                "geoapifyProperties",
                new GeoapifyProperties("test-api-key")
        );
    }

    @Test
    void shouldReturnLocationWhenCityIsFound() {
        String response = """
                {
                    "type": "FeatureCollection",
                    "features": [
                        {
                            "type": "Feature",
                            "properties": {
                                "lat": 40.416782,
                                "lon": -3.703507
                            }
                        }
                    ]
                }
                """;

        server.expect(requestTo(hasToString(containsString(
                        "api.geoapify.com/v1/geocode/search"))))
                .andExpect(method(HttpMethod.GET))
                .andExpect(queryParam("city", equalTo("Madrid")))
                .andExpect(queryParam("country", equalTo("Spain")))
                .andExpect(queryParam("apiKey", equalTo("test-api-key")))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        LocationDTO result = mapService.geoLocateCity("Madrid", "Spain");

        assertNotNull(result);
        assertEquals(40.416782, result.latitude());
        assertEquals(-3.703507, result.longitude());

        server.verify();
    }

    @Test
    void shouldReturnNullWhenResponseIsNull() {
        server.expect(anything())
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON));

        LocationDTO result = mapService.geoLocateCity("Madrid", "Spain");

        assertNull(result);
        server.verify();
    }

    @Test
    void shouldReturnNullWhenFeaturesAreEmpty() {
        String response = """
                {
                    "type": "FeatureCollection",
                    "features": []
                }
                """;

        server.expect(anything())
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        LocationDTO result = mapService.geoLocateCity("Madrid", "Spain");

        assertNull(result);
        server.verify();
    }

    @Test
    void shouldReturnNullWhenPropertiesAreMissing() {
        String response = """
                {
                    "type": "FeatureCollection",
                    "features": [
                        {
                            "type": "Feature"
                        }
                    ]
                }
                """;

        server.expect(anything())
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        LocationDTO result = mapService.geoLocateCity("Madrid", "Spain");

        assertNull(result);
        server.verify();
    }
}