package de.wtl.core.run

import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength

val RunValidator = Validation {
    Run::project required {
        minLength(3)
        maxLength(100)
    }
    Run::experiment required {
        minLength(3)
        maxLength(100)
    }
    Run::name required {
        minLength(3)
        maxLength(100)
    }
}
