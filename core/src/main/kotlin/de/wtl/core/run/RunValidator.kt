package de.wtl.core.run

import de.wtl.api.model.RunCreateRequest
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength

val RunValidator = Validation {
    RunCreateRequest::experiment required {
        minLength(3)
        maxLength(100)
    }
    RunCreateRequest::name required {
        minLength(3)
        maxLength(100)
    }
    RunCreateRequest::project required {
        minLength(3)
        maxLength(100)
    }
}
