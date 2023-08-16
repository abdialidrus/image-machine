package com.example.imagemachine.domain.use_case.form_validation

class ValidateQrNumber {

    fun execute(code: String): ValidationResult {
        if (code.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The machine QR code can't be blank"
            )
        }

        if (code.toIntOrNull() == null) {
            return ValidationResult(
                successful = false,
                errorMessage = "Only numbers are allowed for the machine QR code"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}
