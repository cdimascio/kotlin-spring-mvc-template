package api.common

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class Swagger {
    @Bean
    fun api() = Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("api"))
        .paths(PathSelectors.any())
        .build()
        .pathMapping("/")

    private fun apiInfo() = ApiInfoBuilder()
        .title("Kotlin Spring MVC Template with Swagger")
        .description("""
            An API template project using Kotlin, Spring MVC, JSR-303 Bean Validation, and Swagger
            """.trimIndent())
        .license("Apache 2.0")
        .version("1.0")
        .build()
}
