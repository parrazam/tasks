package es.parravidales.tasks.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {
}
