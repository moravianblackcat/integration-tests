package cz.dan.integrationtests.http;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@AutoConfiguration
@ConditionalOnProperty(name = "app.test.host")
public class HttpAutoConfiguration {

    @Bean
    @ConfigurationProperties("app.test")
    @ConditionalOnMissingBean(name = "httpHelperConfigProperties")
    public HttpHelperConfigProperties httpHelperConfigProperties() {
        return new HttpHelperConfigProperties();
    }

    @Bean
    @ConditionalOnMissingBean(name = "httpHelper")
    public HttpHelper httpHelper(HttpHelperConfigProperties httpHelperConfigProperties, RestTemplate restTemplate) {
        return new HttpHelper(httpHelperConfigProperties, restTemplate);
    }

}
