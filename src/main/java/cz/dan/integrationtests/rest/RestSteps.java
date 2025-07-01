package cz.dan.integrationtests.rest;

import cz.dan.integrationtests.http.HttpHelper;
import cz.dan.integrationtests.util.ClassPathResourceUtil;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestSteps {

    private final ClassPathResourceUtil classPathResourceUtil;

    private final HttpHelper httpHelper;

    @When("I send POST request to {} with request body from {}")
    public void postRequestToWithRequestBody(String endpoint, String requestBodyJsonPath) {
        httpHelper.postWithJsonRequestBody(classPathResourceUtil.getStringFromJsonPath(requestBodyJsonPath), endpoint);
    }

}
