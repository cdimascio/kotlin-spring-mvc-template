package api.common.errors

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus
import java.util.UUID

@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    creatorVisibility = JsonAutoDetect.Visibility.NONE
)
data class ApiError(
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) val trace: String,
    val status: HttpStatus,
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) val errors: List<ApiErrorDetails>
) : Throwable(errors[0].message)

data class ApiErrorDetails(val code: String, val message: String)

fun badRequest(t: Throwable) = error(HttpStatus.BAD_REQUEST, t)
fun badRequest(message: String = "bad request") = error(HttpStatus.BAD_REQUEST, message)

fun conflict(t: Throwable) = error(HttpStatus.CONFLICT, t)
fun conflict(message: String = "conflict") = error(HttpStatus.CONFLICT, message)

fun forbidden(t: Throwable) = error(HttpStatus.FORBIDDEN, t)
fun forbidden(message: String = "forbidden") = error(HttpStatus.FORBIDDEN, message)

fun internalServerError(t: Throwable) = error(HttpStatus.INTERNAL_SERVER_ERROR, t)
fun internalServerError(message: String = "internal server error") = error(HttpStatus.INTERNAL_SERVER_ERROR, message)

fun notFound(t: Throwable) = error(HttpStatus.NOT_FOUND, t)
fun notFound(message: String = "not found") = error(HttpStatus.NOT_FOUND, message)

fun notImplemented(t: Throwable) = error(HttpStatus.NOT_IMPLEMENTED, t)
fun notImplemented(message: String = "not implemented") = error(HttpStatus.NOT_IMPLEMENTED, message)

fun unauthorized(t: Throwable) = error(HttpStatus.UNAUTHORIZED, t)
fun unauthorized(message: String = "unauthorized") = error(HttpStatus.UNAUTHORIZED, message)

fun unsupportedMediaType(t: Throwable) = error(HttpStatus.UNSUPPORTED_MEDIA_TYPE, t)
fun unsupportedMediaType(message: String = "unsupported media type") = error(HttpStatus.UNSUPPORTED_MEDIA_TYPE, message)

private fun error(status: HttpStatus, message: String) = ApiError(
    trace = UUID.randomUUID().toString(),
    status = status,
    errors = listOf(ApiErrorDetails(statusToCode(status), message)))

private fun error(status: HttpStatus, t: Throwable) =
    ApiError(
        trace = UUID.randomUUID().toString(),
        status = status,
        errors = listOf(ApiErrorDetails(statusToCode(status), t.message
            ?: status.name)))

private fun statusToCode(status: HttpStatus): String {
    val code = { c: String -> c.toLowerCase().replace(" ", "_") }
    return code(status.name)
}
