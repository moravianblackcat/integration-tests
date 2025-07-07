package cz.dan.integrationtests.tests;

import io.cucumber.java.DataTableType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class TestsConfiguration {

    @Bean
    @DataTableType
    public Map<String, Object> transform(Map<String, String> entry) {
        return new DataTableTransformer().transform(entry);
    }

}
