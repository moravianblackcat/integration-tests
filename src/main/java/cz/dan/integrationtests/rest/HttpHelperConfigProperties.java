package cz.dan.integrationtests.rest;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("app.test")
@Component
@Data
public class HttpHelperConfigProperties {

    String host;

    int port;

}
