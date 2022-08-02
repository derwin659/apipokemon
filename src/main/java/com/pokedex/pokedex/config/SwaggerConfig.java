package com.pokedex.pokedex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).ignoredParameterTypes(ApiIgnore.class)
                .apiInfo(swaggerInfo())
                .select()
                .apis(
                        RequestHandlerSelectors
                                .basePackage("com.pokedex.pokedex.controller"))
                .paths(PathSelectors.any())
                .build()
            ;
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("API Documentation")
                .description("Api que consulta informacion de la API  de pokemones, y envia informacion basica al cliente")
                .license("license : Api que consulta Pokemones").licenseUrl("https://peaceful-caverns-44332.herokuapp.com/modyo/api").build();
    }


}
