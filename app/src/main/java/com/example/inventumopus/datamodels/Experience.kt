package com.example.inventumopus.datamodels

data class Experience(
    val username: String,
    val companyName: String,
    val days: Int,
    val responsibilities: String
)

data class ExperienceResponse(
    val success: Boolean,
    val experience: Experience,
    val detail: String,
    val error: String
)