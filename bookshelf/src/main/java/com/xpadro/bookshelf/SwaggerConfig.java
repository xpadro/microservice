package com.xpadro.bookshelf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static java.util.Collections.emptyList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final Contact CONTACT = new Contact("Xavier Padró", "https://xpadro.com", "xpadro.sw@gmail.com");
    private static final ApiInfo API_INFO = new ApiInfo("Bookshelf", "Bookshelf micro service", "1.0", "tos", CONTACT,
            "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", emptyList());

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(API_INFO);
    }
}
