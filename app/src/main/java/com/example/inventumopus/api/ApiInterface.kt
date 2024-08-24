package com.example.inventumopus.api

import com.example.inventumopus.datamodels.Bookmark
import com.example.inventumopus.datamodels.BookmarkResponse
import com.example.inventumopus.datamodels.Experience
import com.example.inventumopus.datamodels.ExperienceResponse
import com.example.inventumopus.datamodels.Job
import com.example.inventumopus.datamodels.JobApplication
import com.example.inventumopus.datamodels.JobApplicationResponse
import com.example.inventumopus.datamodels.JobIDs
import com.example.inventumopus.datamodels.Jobs
import com.example.inventumopus.datamodels.Qualification
import com.example.inventumopus.datamodels.QualificationResponse
import com.example.inventumopus.datamodels.User
import com.example.inventumopus.datamodels.UserCreation
import com.example.inventumopus.datamodels.UserCreationResponse
import com.example.inventumopus.datamodels.Username
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @GET("jobs")
    fun getAllJobsData() : Call<Jobs>

    @GET("jobs/random")
    fun getRandomJob(): Call<Job>

    @GET("jobs/random/100")
    fun getRandomJobs(): Call<Jobs>

    @GET("user/{username}")
    fun getUserByUsername(@Path("username") username: String): Call<User>

    @POST("create_user")
    fun createUser(@Body data: UserCreation): Call<UserCreationResponse>

    @POST("add_experience")
    fun addExperience(@Body data: Experience): Call<ExperienceResponse>

    @POST("add_qualifications")
    fun addQualification(@Body data: Qualification): Call<QualificationResponse>

    @POST("manage_bookmark")
    fun manageBookmark(@Body data: Bookmark): Call<BookmarkResponse>

    @POST("get_specific_jobs")
    fun getSpecificJobs(@Body data: JobIDs): Call<Jobs>

    @POST("add_application")
    fun addApplication(@Body data: JobApplication): Call<JobApplicationResponse>
}