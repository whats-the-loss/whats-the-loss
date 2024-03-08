package de.wtl.core.run

import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength

val RunValidator = Validation {
    RunDtoRequest::experiment required {
        minLength(3)
        maxLength(100)
    }
    RunDtoRequest::name required {
        minLength(3)
        maxLength(100)
    }
    RunDtoRequest::project required {
        minLength(3)
        maxLength(100)
    }
}
