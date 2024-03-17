package de.wtl.core.run

import de.wtl.api.model.RunCreateRequest
import de.wtl.core.utils.Validator
import de.wtl.core.utils.ValidatorFactory
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import org.koin.core.annotation.Factory

@Factory
class RunValidatorFactory : ValidatorFactory<RunCreateRequest> {
    override fun validator() = Validator {
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
}
