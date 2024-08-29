package com.example.inventumopus.datamodels

data class Experience(
    val username: String,
    val expId: Int? = null,
    val jobTitle: String? = null,
    val companyName: String? = null,
    val years: Int? = null,
    val responsibilities: String? = null,
    val add: Boolean? = null
)

data class ExperienceResponse(
    val success: Boolean,
    val experience: Experience,
    val detail: String,
    val error: String
)