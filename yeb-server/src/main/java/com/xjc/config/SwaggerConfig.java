package com.xjc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
public class SwaggerConfig {


    //这个docket用来设置文档的信息
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                //配置哪些 api 操作（通过正则表达式模式）和 HTTP 方法将安全上下文应用于 api
                /**
                 *  配置全局策略SecuritySchemes，设置了api访问的时候会带一个Authorization的header，就是携带jwt令牌
                 * 哪些操作有关联SecuritySchemes "/hello/.*"
                 */
                .securitySchemes(securitySchemes())
                 .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("云E办接口文档")
                .description("云E办接口文档")
                .contact(new Contact("项佳诚","http:localhost:8081/doc.html","xxxx@xxxx.com"))
                .version("1.0")
                .build();
    }

    private List<SecurityContext> securityContexts(){
        //设置需要登录认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(getContextByPath("/hello/.*"));
        return result;
    }


    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global",
                "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization",authorizationScopes));
        return result;
    }



    private List<SecurityScheme> securitySchemes(){
        //设置请求头信息
        List<SecurityScheme> result= new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization","Authorization","Header");
        result.add(apiKey);
        return result;
    }
}