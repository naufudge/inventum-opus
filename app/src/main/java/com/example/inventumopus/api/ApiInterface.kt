package com.example.inventumopus.api

import com.example.inventumopus.Job
import com.example.inventumopus.Jobs
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("jobs")
    fun getAllJobsData() : Call<Jobs>

    @GET("jobs/random")
    fun getRandomJob(): Call<Job>

    @GET("jobs/random/100")
    fun getRandomJobs(): Call<Jobs>

    @GET("users/{username}")
    fun getJobById(@Path("username") id: String): Call<Job>
}