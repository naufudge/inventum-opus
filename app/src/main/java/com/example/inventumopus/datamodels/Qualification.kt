package com.example.inventumopus.datamodels

data class Qualification(
    val username: String,
    val school: String,
    val degree: String,
    val field: String
)

data class QualificationResponse(
    val success: Boolean,
    val qualification: Qualification,
    val detail: String,
    val error: String
)
