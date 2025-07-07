package cz.dan.integrationtests.rest;

import cz.dan.integrationtests.http.HttpHelper;
import cz.dan.integrationtests.http.HttpResponsesCache;
import cz.dan.integrationtests.util.ClassPathResourceUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class RestSteps {

    private final ClassPathResourceUtil classPathResourceUtil;

    private final HttpHelper httpHelper;

    private final HttpResponsesCache httpResponsesCache;

    @When("I send POST request to {} with request body from {}")
    public void postRequestToWithRequestBody(String endpoint, String requestBodyJsonPath) {
        httpHelper.postWithJsonRequestBody(classPathResourceUtil.getStringFromJsonPath(requestBodyJsonPath), endpoint);
    }

    @Then("{} returns array of following objects:")
    @SuppressWarnings("unchecked")
    public void returnsArrayOfFollowingObject(String endpoint, List<Map<String, Object>> expectedObjects) {
        List<Map<String, Object>> actualObjects = (List<Map<String, Object>>) httpResponsesCache.get(endpoint);

        assertThat(actualObjects)
                .usingRecursiveComparison()
                .withComparatorForType(Comparator.comparingLong(Number::longValue), Number.class)
                .isEqualTo(expectedObjects);
    }

}
