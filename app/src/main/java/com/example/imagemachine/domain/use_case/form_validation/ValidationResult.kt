package com.example.imagemachine.domain.use_case.form_validation

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
