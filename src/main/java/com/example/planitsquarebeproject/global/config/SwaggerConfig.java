package com.example.planitsquarebeproject.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development Server");

        Info info = new Info()
                .title("Holiday Keeper API")
                .version("v1.0")
                .description("Nager API 기반 Holiday Keeper 서비스 API 문서입니다.");

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}
