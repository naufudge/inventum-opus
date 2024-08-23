package com.example.inventumopus.datamodels

data class Bookmark(
    val username: String,
    val jobId: Int,
    val add: Boolean
)

data class BookmarkResponse(
    val success: Boolean,
    val bookmark: Bookmark,
    val detail: String,
    val error: String
)
