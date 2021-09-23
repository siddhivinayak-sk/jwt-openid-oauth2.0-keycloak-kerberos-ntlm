package com.sk.config;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket productApi() {
        
    	ApiInfo apiInfo = new ApiInfoBuilder()
    			.title("Title")
    			.description("Description")
				.contact(new Contact("Admin", "https://www.sk.com", "sk@test.com"))
				.version("0.0.1")
				.build();
        
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
        		.paths(PathSelectors.any())
        		.build()
        		.apiInfo(apiInfo);
    }
    
    //@Component
    //@Primary
    public static class CombinedSwaggerResources implements SwaggerResourcesProvider {

        @Resource
        private InMemorySwaggerResourcesProvider inMemorySwaggerResourcesProvider;

        @Override
        public List<SwaggerResource> get() {

            SwaggerResource jaxRsResource = new SwaggerResource();
            jaxRsResource.setLocation("/json/swagger.json");
            jaxRsResource.setSwaggerVersion("2.0");
            jaxRsResource.setName("Documentation");

            return Stream.concat(Stream.of(jaxRsResource), inMemorySwaggerResourcesProvider.get().stream()).collect(Collectors.toList());
        }

    }
}