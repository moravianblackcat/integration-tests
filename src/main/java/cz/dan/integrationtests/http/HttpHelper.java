package cz.dan.integrationtests.http;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
public class HttpHelper {

    private final HttpHelperConfigProperties configProperties;

    private final RestTemplate restTemplate;

    public Map<String, Object> get(String endpoint) {
        URI uri = getUriBuilder(endpoint).build();
        return performGet(uri);
    }

    public Map<String, Object> getWithQueryParams(String endpoint, Map<String, Object> queryParams) {
        URI uri = getUriBuilder(endpoint).queryParams(transform(queryParams)).build();
        return performGet(uri);
    }

    public void postWithJsonRequestBody(String requestBody, String endpoint) {
        RequestEntity<String> request = RequestEntity
                .post(getUriBuilder(endpoint).build())
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .body(requestBody);
        restTemplate.exchange(request, Void.class);
    }

    private Map<String, Object> performGet(URI uri) {
        RequestEntity<Void> request = RequestEntity.get(uri).accept(APPLICATION_JSON).build();
        ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {};

        return restTemplate.exchange(request, responseType).getBody();
    }

    private UriBuilder getUriBuilder(String endpoint) {
        return UriComponentsBuilder.newInstance()
                .host(configProperties.getHost())
                .port(configProperties.getPort())
                .scheme("http")
                .path(endpoint);
    }

    private MultiValueMap<String, String> transform(Map<String, Object> map) {
        MultiValueMap<String, String> result = new LinkedMultiValueMap<>();
        map.forEach((key, value) -> result.add(key, (String) value));

        return result;
    }

}
