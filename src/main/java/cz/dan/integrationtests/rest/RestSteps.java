package cz.dan.integrationtests.rest;

import cz.dan.integrationtests.http.HttpHelper;
import cz.dan.integrationtests.util.ClassPathResourceUtil;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestSteps {

    private final ClassPathResourceUtil classPathResourceUtil;

    private final HttpHelper httpHelper;

    @When("GET request to {}")
    public void getRequestTo(String endpoint) {
        httpHelper.get(endpoint);
    }

    @When("I send GET request to {} with query parameters from {}")
    public void getRequestToWithQueryParams(String endpoint, String queryParamsJsonPath) {
        httpHelper.getWithQueryParams(endpoint, classPathResourceUtil.getMapFromJsonPath(queryParamsJsonPath));
    }

    @When("I send POST request to {} with request body from {}")
    public void postRequestToWithRequestBody(String endpoint, String requestBodyJsonPath) {
        httpHelper.postWithJsonRequestBody(classPathResourceUtil.getStringFromJsonPath(requestBodyJsonPath), endpoint);
    }

}
