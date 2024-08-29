package com.example.inventumopus.datamodels

data class JobApplication(
    val username: String,
    val jobId: Int
)

data class JobApplicationResponse(
    val success: Boolean,
    val application: JobApplication,
    val detail: String,
    val error: String
)
