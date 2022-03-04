package com.example.mall.note.config;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.doc.enabled:true}")
    private Boolean docEnabled;

    @Primary
    public Docket createRestApi() {

        // 没效果
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new ParameterBuilder()
                .name("primaryChannelId")
                .defaultValue("10006")
                .description("primaryChannelId")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build());

        Predicate<String> selector = docEnabled ? PathSelectors.any() : PathSelectors.none();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lvmama.messaging.core.controller"))
                .paths(selector)
                .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("messaging Doc").description("messaging系统 Api文档")
                .termsOfServiceUrl("http://localhost/web/").contact(new Contact("yong.cao", "", "")).version("2.0")
                .build();
    }


}
