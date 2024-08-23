package ru.sckibin.messagesender.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    public static final String VERSION = "1";
    public static final String TITLE = "Task manager REST API";
    public static final String DESCRIPTION = "Description of API.";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title(TITLE)
                        .description(DESCRIPTION)
                        .version(VERSION).contact(new Contact()
                                .name("Skibin Denis")
                                .email("d.sckibin2017@yandex.ru")
                                .url("https://www.linkedin.com/in/denis-skibin-366607278/")
                        )
                );
    }
}