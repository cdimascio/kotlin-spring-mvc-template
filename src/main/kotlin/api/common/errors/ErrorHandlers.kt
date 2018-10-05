package api.common.errors

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletResponse
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ErrorHandlers {

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun badRequest(res: HttpServletResponse, ex: Exception): ApiError {
        ex.printStackTrace()
        return badRequest(ex)
    }

    @ExceptionHandler(ApiError::class)
    fun apiError(res: HttpServletResponse, ex: ApiError): ApiError {
        ex.printStackTrace()
        res.status = ex.status.value()
        return ex
    }
}

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
class FailoverHandlers {
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun catchall(ex: Exception): ApiError {
        ex.printStackTrace()
        return internalServerError(ex)
    }
}
