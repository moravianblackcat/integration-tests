package cz.dan.integrationtests.http;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "app.test.host=localhost",
        "app.test.port=8000"
})
class HttpAutoConfigurationTest {

    @MockitoBean
    private RestTemplate restTemplate;

    @Autowired
    private ApplicationContext context;

    @Test
    void httpHelperConfigPropertiesBindsCustomProperties() {
        HttpHelperConfigProperties sut = context.getBeansOfType(HttpHelperConfigProperties.class)
                .get("httpHelperConfigProperties");
        assertThat(sut.getHost()).isEqualTo("localhost");
        assertThat(sut.getPort()).isEqualTo(8000);
    }

    @Test
    void httpHelperBeanIsPresent() {
        assertThat(context.getBeansOfType(HttpHelper.class)).isNotEmpty();
    }
}