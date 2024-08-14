package com.example.inventumopus

data class User(
    val username: String,
    val email: String,
    val password: String,
    val bookmarks: List<Job>,
    val appliedJobs: List<Job>
)
