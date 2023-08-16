package com.example.imagemachine.domain.use_case.form_validation

class ValidateMachineName {

    fun execute(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The machine name can't be empty"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}