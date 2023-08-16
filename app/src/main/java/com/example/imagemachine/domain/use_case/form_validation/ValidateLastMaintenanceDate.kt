package com.example.imagemachine.domain.use_case.form_validation

class ValidateLastMaintenanceDate {

    fun execute(date: String): ValidationResult {
        if (date.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The last maintenance date can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}