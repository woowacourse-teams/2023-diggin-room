package com.digginroom.digginroom.config;

import com.digginroom.digginroom.controller.Auth;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SESSION_SECURITY_KEY = "session";
    private static final SecurityRequirement SESSION_SECURITY_ITEM = new SecurityRequirement().addList(
            SESSION_SECURITY_KEY
    );

    static {
        SpringDocUtils.getConfig()
                .addAnnotationsToIgnore(Auth.class);
    }

    @Bean
    public OpenAPI openApiSpecification() {
        return new OpenAPI()
                .info(new Info()
                        .version("v0.0.1")
                        .title("디깅룸 API")
                        .description("접근한 서버의 최신 API 스펙입니다")
                )
                .components(new Components()
                        .addSecuritySchemes(
                                SESSION_SECURITY_KEY,
                                sessionSecurityScheme()
                        )
                )
                .addServersItem(new Server().url("/"));
    }

    private SecurityScheme sessionSecurityScheme() {
        return new SecurityScheme()
                .type(Type.APIKEY)
                .in(In.COOKIE)
                .name("JSESSIONID");
    }

    @Bean
    public OperationCustomizer authOperationMarker() {
        return (operation, handlerMethod) -> {
            Arrays.stream(handlerMethod.getMethodParameters())
                    .filter(methodParameter -> methodParameter.hasParameterAnnotation(Auth.class))
                    .findAny()
                    .ifPresent(
                            it -> operation.addSecurityItem(SESSION_SECURITY_ITEM)
                    );
            return operation;
        };
    }
}
