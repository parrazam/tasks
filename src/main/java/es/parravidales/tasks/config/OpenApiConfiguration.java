package es.parravidales.tasks.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;
import java.util.List;

@Slf4j
@OpenAPIDefinition(
  servers = {
    @Server(
      description = "Local server",
      url = "http://127.0.0.1:8080/api/v1"
    ),
    @Server(
      description = "Custom server",
      url = "{scheme}://{server}-{path}",
      variables = {
        @ServerVariable(name = "scheme", defaultValue = "https", allowableValues = {"http", "https"}),
        @ServerVariable(name = "server", description = "Server name", defaultValue = "127.0.0.1:8080"),
        @ServerVariable(name = "path", description = "path", defaultValue = "/api/v1")
      }
    )
  },
  info = @Info(
    title = "Simple tasks manager",
    description = "Very simple tasks manager!",
    version = "0.0.1-SNAPSHOT",
    contact = @Contact(
      name = "parrazam",
      url = "https://github.com/parrazam",
      email = "contacto@parravidales.es"
    ), license = @License(
    name = "No license"
  )
  )
)
@Configuration
public class OpenApiConfiguration {

  @Bean
  @ConditionalOnClass(CorsFilter.class)
  @ConditionalOnProperty(value = "tasks.swagger.cors.enabled")
  public CorsFilter corsFilter() {
    log.info("Initializing CORS filter...");
    final CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(false);
    config.addAllowedHeader(CorsConfiguration.ALL);
    config.setAllowedOrigins(Collections.singletonList("*"));
    config.setAllowedMethods(List.of("GET", "PUT", "POST", "DELETE", "HEAD", "OPTIONS"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

}
