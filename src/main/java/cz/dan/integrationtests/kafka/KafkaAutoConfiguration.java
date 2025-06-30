package cz.dan.integrationtests.kafka;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@AutoConfiguration
@ConditionalOnProperty("spring.kafka.bootstrap-servers")
public class KafkaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "testKafkaConsumer")
    public TestKafkaConsumer testKafkaConsumer(KafkaTemplate<String, Object> kafkaTemplate) {
        return new TestKafkaConsumer(kafkaTemplate);
    }

}
