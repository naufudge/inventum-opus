package com.example.inventumopus

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("jobs/")
    fun getJobData() : Call<Jobs>

    @GET("jobs/random")
    fun getRandomJob(): Call<Job>

    @GET("jobs/random/100")
    fun getRandomJobs(): Call<Jobs>
}