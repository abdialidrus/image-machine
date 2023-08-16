package com.example.imagemachine.domain.use_case.form_validation

class ValidateMachineType {

    fun execute(type: String): ValidationResult {
        if (type.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The machine type can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}