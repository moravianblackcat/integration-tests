Collection of Spring beans handy for integration tests.

# REST
For Rest API interaction with the service itself, `@TestConfiguration` class for Cucumber must create `RestTemplate` bean and those two properties must be set for the `integration-tests` profile:
- app.test.host
- app.test.port

# Kafka
Kafka beans are available only if the `spring.kafka.bootstrap-servers` property is set in the project.