package com.example.inventumopus.datamodels

data class Qualification(
    val username: String,
    val qualificationId: Int? = null,
    val school: String? = null,
    val degree: String? = null,
    val field: String? = null,
    val add: Boolean? = null
)

data class QualificationResponse(
    val success: Boolean,
    val qualification: Qualification,
    val detail: String,
    val error: String
)
