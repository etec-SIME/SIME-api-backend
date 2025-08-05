package br.com.sime.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("SIME - Sistema Integrado de Manutenção Escolar")
                .version("v1")
                .description("Documentação da API para gestão de chamados escolares")
                .contact(new Contact()
                        .name("Equipe NeWo")
                        .email("tcchas2@gmail.com")
                        .url("https://www.newo.com.br")));
    }
    @Bean
    public GroupedOpenApi publicOpenApi(){
        return GroupedOpenApi.builder()
                .group("sime-public")
                .pathsToMatch("/**")
                .build();
    }
}
