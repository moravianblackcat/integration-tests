package cz.dan.integrationtests.http;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("app.test")
public class HttpHelperConfigProperties {

    String host;

    int port;

}
