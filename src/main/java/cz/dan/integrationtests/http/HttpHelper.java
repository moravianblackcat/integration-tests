package cz.dan.integrationtests.http;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
public class HttpHelper {

    private final HttpHelperConfigProperties configProperties;

    private final RestTemplate restTemplate;

    public void postWithJsonRequestBody(String requestBody, String endpoint) {
        String url = getUrl(endpoint);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, getJsonHeaders());
        restTemplate.postForEntity(URI.create(url), entity, String.class);
    }

    private String getUrl(String endpoint) {
        return "http://" + configProperties.getHost() + ":" + configProperties.getPort() + endpoint;
    }

    private HttpHeaders getJsonHeaders() {
        HttpHeaders result = new HttpHeaders();
        result.setContentType(APPLICATION_JSON);

        return result;
    }
}
