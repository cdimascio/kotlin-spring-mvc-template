package api.common

import api.logger
import com.atlassian.oai.validator.springmvc.InvalidRequestException
import com.atlassian.oai.validator.springmvc.InvalidResponseException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.cdimascio.japierrors.ApiError
import io.github.cdimascio.japierrors.ApiError.badRequest
import io.github.cdimascio.japierrors.ApiError.internalServerError
import io.github.cdimascio.japierrors.basic.ApiErrorBasic
import io.github.cdimascio.japierrors.wcp.ApiErrorWcp
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ErrorHandlers {
    private val mapper = jacksonObjectMapper()

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun badRequest(res: HttpServletResponse, ex: Exception): ApiError {
        ex.printStackTrace()
        return badRequest(ex)
    }

    @ExceptionHandler(InvalidRequestException::class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun invalidRequest(res: HttpServletResponse, ex: InvalidRequestException): ApiError {
        logger.warn(ex.toString())
        val json = mapper.readTree(ex.message)
        val key = json["messages"][0]["key"].asText()
        val messageBody = json["messages"][0]["message"].asText()
        val messagePrefix = json["messages"][0]["context"]["parameter"]?.let {
            val name = it.get("name")?.asText() ?: ""
            val inType = it.get("in")?.let {
                "(in ${it.asText()})"
            } ?: ""
            "$name $inType:"
        } ?: ""
        val message = "$messagePrefix $messageBody"
        return if (key == "validation.request.path.missing") {
            res.status = 404
            ApiError.notFound(message)
        } else {
            res.status = 400
            badRequest(message)
        }
    }

    @ExceptionHandler(InvalidResponseException::class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun invalidResponse(res: HttpServletResponse, ex: InvalidResponseException): ApiError {
        logger.error(ex.toString())
        val json = mapper.readTree(ex.message)
        val message = json["messages"][0]["message"].asText()
        return internalServerError("response validation error: $message")
    }

    @ExceptionHandler(ApiError::class)
    fun apiError(res: HttpServletResponse, ex: ApiError): ApiError {
        ex.printStackTrace()
        res.status = when (ex) {
            is ApiErrorWcp -> ex.status.code
            is ApiErrorBasic -> ex.code
            else -> {
                ex.addSuppressed(Exception("unknown ApiError type"))
                500
            }
        }
        return ex
    }
}

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
class FailoverHandlers {

    @ExceptionHandler(NoHandlerFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun noHandler(ex: NoHandlerFoundException): ApiError {
        val e = ApiError.notFound(ex)
        logger.warn(e.toString())
        return e
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun catchall(ex: Exception): ApiError {
        ex.printStackTrace()
        return internalServerError(ex)
    }
}

@RestController
class MyErrorController : ErrorController {
    override fun getErrorPath() = "/error"

    @RequestMapping("/error")
    fun handle(req: HttpServletRequest, res: HttpServletResponse): ApiError {
        val status = Integer.parseInt(req.getAttribute("javax.servlet.error.status_code").toString())
        val message = req.getAttribute("javax.servlet.error.message").toString()
        val requestUri = req.getAttribute("javax.servlet.error.request_uri").toString()
        if (status >= 500) return internalServerError(message)
        // if we got here the route likely doesn't exist
        res.status = 404
        return ApiError.notFound(requestUri)
    }
}
