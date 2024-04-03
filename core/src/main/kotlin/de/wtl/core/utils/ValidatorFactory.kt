package de.wtl.core.utils

import io.konform.validation.Validation
import io.konform.validation.ValidationBuilder
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import kotlin.reflect.KClass

class Validator<T : Any>(val kClass: KClass<T>, val builder: ValidationBuilder<T>.() -> Unit) {
    companion object {
        inline operator fun <reified T : Any> invoke(noinline builder: ValidationBuilder<T>.() -> Unit) = Validator(
            T::class,
            builder,
        )
    }
}

fun interface ValidatorFactory<T : Any> {
    fun validator(): Validator<T>

    fun register(config: RequestValidationConfig) {
        val validator = validator()
        config.register(validator.kClass, Validation(validator.builder))
    }
}
