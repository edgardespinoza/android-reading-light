package com.eespinor.lightreading.common

data class ValidationResult<T>(
    val successful: Boolean,
    val type: T? = null
)
