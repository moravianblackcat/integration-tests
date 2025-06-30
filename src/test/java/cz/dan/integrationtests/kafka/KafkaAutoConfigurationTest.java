package cz.dan.integrationtests.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.kafka.bootstrap-servers=localhost:9092")
class KafkaAutoConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testKafkaConsumerBeanExists() {
        assertThat(applicationContext.containsBean("testKafkaConsumer")).isTrue();
    }

}