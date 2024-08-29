package com.example.inventumopus.datamodels

data class User(
    val username: String,
    val email: String,
    val password: String,
    val experience: List<Experience>,
    val qualifications: List<Qualification>,
    val appliedJobs: List<Int>,
    val bookmarks: List<Int>,
    val picture: String,
    val userId: Int
)

data class UserCreation(
    val username: String,
    val email: String,
    val password: String
)

data class UserCreationResponse(
    val success: Boolean,
    val user: UserCreation,
    val detail: String
)

data class Username(
    val name: String
)