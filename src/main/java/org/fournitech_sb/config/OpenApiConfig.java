package org.fournitech_sb.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI fournitechOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fournitech API - Gestion d’approvisionnement")
                        .description("Documentation des endpoints de l’application de gestion des produits, commandes et stocks.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Équipe Fournitech")
                                .email("support@fournitech.com")
                                .url("https://github.com/fournitech"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentation complète du projet")
                        .url("https://github.com/fournitech"));
    }
}
