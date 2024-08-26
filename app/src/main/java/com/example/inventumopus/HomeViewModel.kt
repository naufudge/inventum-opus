package com.example.inventumopus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventumopus.api.RetrofitInstance
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val _jobsData : MutableStateFlow<List<Job>> = MutableStateFlow(listOf())
    var jobsData : StateFlow<List<Job>> = _jobsData

    var homeScreenLoading by mutableStateOf(true)
    var applicationsScreenLoading by mutableStateOf(true)
    var bookmarksScreenLoading by mutableStateOf(true)
    var experienceScreenLoading by mutableStateOf(false)
    var qualificationScreenLoading by mutableStateOf(false)

    private val _allJobsData : MutableStateFlow<List<Job>> = MutableStateFlow(listOf())
    var allJobsData : StateFlow<List<Job>> = _allJobsData

    var selectedJob by mutableStateOf<Job?>(value = null)
        private set

    var selectedQualification by mutableStateOf<Qualification?>(value = null)
        private set

    var selectedExperience by mutableStateOf<Experience?>(value = null)
        private set

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()


    private val _jobs = MutableStateFlow(jobsData.value)
    val jobs = searchText
        .combine(_allJobsData) { text, jobs ->
            if (text.isBlank()) {
                jobs
            } else {
                jobs.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _allJobsData.value
        )

    private val _signedIn = MutableStateFlow(false)
    val signedIn = _signedIn.asStateFlow()

    private val _userBookmarks : MutableStateFlow<List<Job>> = MutableStateFlow(listOf())
    var userBookmarks : StateFlow<List<Job>> = _userBookmarks

    private val _userAppliedJobs : MutableStateFlow<List<Job>> = MutableStateFlow(listOf())
    var userAppliedJobs : StateFlow<List<Job>> = _userAppliedJobs

    var currentUser by mutableStateOf<User?>(value = null)
        private set

    init {
        getJobsData()
        getAllJobsData()
    }

    // Function that will help keep track of the job that the users selects to view
    fun selectedJob(job: Job){
        viewModelScope.launch {
            selectedJob = job
        }
    }

    // Function that will help keep track of the qualification that the user clicks on
    fun selectedQualification(qualification: Qualification){
        viewModelScope.launch {
            selectedQualification = qualification
        }
    }

    // Function that will help keep track of the experience that the user clicks on
    fun selectedExperience(experience: Experience){
        viewModelScope.launch {
            selectedExperience = experience
        }
    }

    // Search function
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    // Function to update user logged in status
    fun setSignInStatus(status: Boolean) {
        viewModelScope.launch {
            _signedIn.value = status
        }
    }

    // Function to update user logged in status
    fun userSignOut() {
        viewModelScope.launch {
            currentUser = null
            _signedIn.value = false
        }
    }

    // Function to get 100 random jobs
    private fun getJobsData() {
        viewModelScope.launch {
            val call: Call<Jobs> = RetrofitInstance.apiService.getRandomJobs()
            call.enqueue(object : Callback<Jobs> {
                override fun onResponse(
                    call: Call<Jobs>,
                    response: Response<Jobs>
                ) {
                    if(response.isSuccessful){
                        val responseData: List<Job>? = response.body()?.jobs
                        if(responseData != null){
                            _jobsData.value = responseData
                            homeScreenLoading = false
                        }
                    }
                }
                override fun onFailure(call: Call<Jobs>, t: Throwable) {
                    println("Failed to Retrieve RECENT Jobs Data")
                }
            })
        }
    }

    // Function to get ALL jobs
    private fun getAllJobsData() {
        viewModelScope.launch {
            val call: Call<Jobs> = RetrofitInstance.apiService.getAllJobsData()
            call.enqueue(object : Callback<Jobs> {
                override fun onResponse(
                    call: Call<Jobs>,
                    response: Response<Jobs>
                ) {
                    if(response.isSuccessful){
                        val responseData: List<Job>? = response.body()?.jobs
                        if(responseData != null){
                            _allJobsData.value = responseData
                        }
                    }
                }
                override fun onFailure(call: Call<Jobs>, t: Throwable) {
                    println("Failed to Retrieve ALL Jobs Data")
                }
            })
        }
    }

    // Function to create a user
    fun createUser(
        user: UserCreation
    ) {
        viewModelScope.launch {
            val call: Call<UserCreationResponse> = RetrofitInstance.apiService.createUser(user)
            call.enqueue(object: Callback<UserCreationResponse> {
                override fun onResponse(call: Call<UserCreationResponse>, response: Response<UserCreationResponse>) {
                    if (response.isSuccessful) {
                        setSignInStatus(true)
                        getUser(user.username)

                        println("Created User Successfully!")
                    } else {
                        println("There was an error when creating the user.")
                    }
                }
                override fun onFailure(call: Call<UserCreationResponse>, t: Throwable) {
                    println("Failed to create user!")
                }
            })
        }
    }

    // Function to get a user's details
    fun getUser(
        username: String
    ) {
        viewModelScope.launch {
            val call: Call<User> = RetrofitInstance.apiService.getUserByUsername(username)
            call.enqueue(object: Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        // handle successful get_user request
                        currentUser = response.body()
                    } else {
                        println("There was an error when getting user.")
                    }
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    println("Failed to get user")
                }
            })
        }
    }

    // Function to add or remove a user's qualification
    fun manageQualification(
        qualification: Qualification
    ) {
        viewModelScope.launch {
            val call: Call<QualificationResponse> = RetrofitInstance.apiService.manageQualification(qualification)
            call.enqueue(object: Callback<QualificationResponse> {
                override fun onResponse(
                    call: Call<QualificationResponse>,
                    response: Response<QualificationResponse>
                ) {
                    getUser(qualification.username)
                    qualificationScreenLoading = false
                    println("Added user qualification")
                }

                override fun onFailure(call: Call<QualificationResponse>, t: Throwable) {
                    println("Failed to add user qualification")
                }
            })
        }
    }

    // Function to edit a user's qualification
    fun editQualification(
        qualification: Qualification
    ) {
        viewModelScope.launch {
            val call: Call<QualificationResponse> = RetrofitInstance.apiService.editQualification(qualification)
            call.enqueue(object: Callback<QualificationResponse> {
                override fun onResponse(
                    call: Call<QualificationResponse>,
                    response: Response<QualificationResponse>
                ) {
                    getUser(qualification.username)
                    qualificationScreenLoading = false
                    println("Edited user qualification")
                }

                override fun onFailure(call: Call<QualificationResponse>, t: Throwable) {
                    println("Failed to edit user qualification")
                }
            })
        }
    }

    // Function to add or remove a user's experience
    fun manageExperience(
        experience: Experience
    ) {
        viewModelScope.launch {
            val call: Call<ExperienceResponse> = RetrofitInstance.apiService.manageExperience(experience)
            call.enqueue(object: Callback<ExperienceResponse> {
                override fun onResponse(
                    call: Call<ExperienceResponse>,
                    response: Response<ExperienceResponse>
                ) {
                    getUser(experience.username)
                    println("Added user experience")
                }

                override fun onFailure(call: Call<ExperienceResponse>, t: Throwable) {
                    println("Failed to add user experience")
                }
            })
        }
    }

    // Function to edit a user's experience
    fun editExperience(
        experience: Experience
    ) {
        viewModelScope.launch {
            val call: Call<ExperienceResponse> = RetrofitInstance.apiService.editExperience(experience)
            call.enqueue(object: Callback<ExperienceResponse> {
                override fun onResponse(
                    call: Call<ExperienceResponse>,
                    response: Response<ExperienceResponse>
                ) {
                    getUser(experience.username)
                    println("Edited user experience")
                }

                override fun onFailure(call: Call<ExperienceResponse>, t: Throwable) {
                    println("Failed to add user experience")
                }
            })
        }
    }

    // Function to add bookmark
    fun manageBookmark(
        bookmark: Bookmark
    ) {
        viewModelScope.launch {
            val call: Call<BookmarkResponse> = RetrofitInstance.apiService.manageBookmark(bookmark)
            call.enqueue(object: Callback<BookmarkResponse> {
                override fun onResponse(
                    call: Call<BookmarkResponse>,
                    response: Response<BookmarkResponse>
                ) {
                    getUser(bookmark.username)
                    bookmarksScreenLoading = true
                    println("Added user bookmark")
                }

                override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                    println("Failed to add user bookmark")
                }
            })
        }
    }

    // Function to add a user's job application
    fun addUserJobApplication(
        application: JobApplication
    ) {
        viewModelScope.launch {
            getUser(application.username)
            val call: Call<JobApplicationResponse> = RetrofitInstance.apiService.addApplication(application)
            call.enqueue(object: Callback<JobApplicationResponse> {
                override fun onResponse(
                    call: Call<JobApplicationResponse>,
                    response: Response<JobApplicationResponse>
                ) {
                    applicationsScreenLoading = true
                    println("Added user's job application")
                }

                override fun onFailure(call: Call<JobApplicationResponse>, t: Throwable) {
                    println("Failed to add user's job application")
                }
            })
        }
    }

    // Function to get user's bookmarks
    fun getUserBookmarks(
        jobIds: JobIDs
    ) {
        viewModelScope.launch {
            val call: Call<Jobs> = RetrofitInstance.apiService.getSpecificJobs(jobIds)
            call.enqueue(object: Callback<Jobs> {
                override fun onResponse(
                    call: Call<Jobs>,
                    response: Response<Jobs>
                ) {
                    if(response.isSuccessful){
                        val responseData: List<Job>? = response.body()?.jobs
                        if(responseData != null){
                            _userBookmarks.value = responseData
                            bookmarksScreenLoading = false
                        }
                    }
                }

                override fun onFailure(call: Call<Jobs>, t: Throwable) {
                    println("Failed to get bookmarks.")
                }
            })
        }
    }

    // Function to get user's applied jobs
    fun getUserAppliedJobs(
        jobIds: JobIDs
    ) {
        viewModelScope.launch {
            val call: Call<Jobs> = RetrofitInstance.apiService.getSpecificJobs(jobIds)
            call.enqueue(object: Callback<Jobs> {
                override fun onResponse(
                    call: Call<Jobs>,
                    response: Response<Jobs>
                ) {
                    if(response.isSuccessful){
                        val responseData: List<Job>? = response.body()?.jobs
                        if(responseData != null){
                            _userAppliedJobs.value = responseData
                            applicationsScreenLoading = false
                        }
                    }
                }

                override fun onFailure(call: Call<Jobs>, t: Throwable) {
                    println("Failed to get user's applied jobs.")
                }
            })
        }
    }

}
