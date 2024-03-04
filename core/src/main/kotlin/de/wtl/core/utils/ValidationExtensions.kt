package de.wtl.core.utils

import io.konform.validation.Invalid
import io.konform.validation.Valid
import io.konform.validation.Validation
import io.konform.validation.ValidationError
import io.konform.validation.ValidationResult
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult as KtorValidationResult

fun <T> ValidationResult<T>.toValidationResult(): KtorValidationResult = when (this) {
    is Valid -> KtorValidationResult.Valid
    is Invalid -> KtorValidationResult.Invalid(errors.map(ValidationError::message))
}

fun <T> Validation<T>.toValidationResult(data: T): KtorValidationResult = validate(data).toValidationResult()

inline fun <reified T : Any> RequestValidationConfig.register(validator: Validation<T>) {
    validate<T>(validator::toValidationResult)
}
