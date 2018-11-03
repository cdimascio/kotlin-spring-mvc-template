package api.common

import api.dotenv
import com.atlassian.oai.validator.OpenApiInteractionValidator
import com.atlassian.oai.validator.springmvc.OpenApiValidationFilter
import com.atlassian.oai.validator.springmvc.OpenApiValidationInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.Filter

@Configuration
class OpenApiValidator : WebMvcConfigurer {
    private val validationInterceptor: OpenApiValidationInterceptor

    init {
        val validator = OpenApiInteractionValidator.createFor("static/api_explorer/api.yaml").build()
        this.validationInterceptor = OpenApiValidationInterceptor(validator)
    }

    @Bean
    fun validationFilter(): Filter {
        val validateRequests = dotenv["VALIDATE_REQUESTS"]?.toBoolean() ?: true
        val validateResponses = dotenv["VALIDATE_RESPONSES"]?.toBoolean() ?: true

        return OpenApiValidationFilter(
            validateRequests,
            validateResponses
        )
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(validationInterceptor).excludePathPatterns("/**/api_explorer/**", "/index.html")
    }
}
