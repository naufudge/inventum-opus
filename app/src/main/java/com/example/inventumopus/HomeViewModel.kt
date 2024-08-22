package com.example.inventumopus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventumopus.api.RetrofitInstance
import com.example.inventumopus.datamodels.Experience
import com.example.inventumopus.datamodels.ExperienceResponse
import com.example.inventumopus.datamodels.Job
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

    private val _allJobsData : MutableStateFlow<List<Job>> = MutableStateFlow(listOf())
    var allJobsData : StateFlow<List<Job>> = _allJobsData

    var selectedJob by mutableStateOf<Job?>(value = null)
        private set

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

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

//    var currentUser by mutableStateOf<User?>(value = User(
//        username = "nauf",
//        email = "nauf@gmail.com",
//        password = "1234",
//        qualifications = listOf(
//            Qualification(
//                username = "",
//                degree = "Bachelor's",
//                school = "Villa College",
//                field = "Computer Science"
//            ),
//            Qualification(
//                username = "",
//                degree = "Certificate IV",
//                school = "MNU",
//                field = "Computer Science"
//            ),
//        ),
//        experience = listOf(
//            Experience(
//                username = "",
//                jobTitle = "Computer Technician",
//                companyName = "MWSC",
//                months = 12,
//                responsibilities = "Managing the IT infrastructure"
//            )
//        ),
//        appliedJobs = listOf(),
//        bookmarks = listOf(),
//        picture = "",
//        userId = 1
//    ))
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

    // Function to add a user's qualification
    fun addQualification(
        qualification: Qualification
    ) {
        viewModelScope.launch {
            val call: Call<QualificationResponse> = RetrofitInstance.apiService.addQualification(qualification)
            call.enqueue(object: Callback<QualificationResponse> {
                override fun onResponse(
                    call: Call<QualificationResponse>,
                    response: Response<QualificationResponse>
                ) {
                    getUser(qualification.username)
                    println("Added user qualification")
                }

                override fun onFailure(call: Call<QualificationResponse>, t: Throwable) {
                    println("Failed to add user qualification")
                }
            })

        }
    }

    // Function to add a user's experience
    fun addExperience(
        experience: Experience
    ) {
        viewModelScope.launch {
            val call: Call<ExperienceResponse> = RetrofitInstance.apiService.addExperience(experience)
            call.enqueue(object: Callback<ExperienceResponse> {
                override fun onResponse(
                    call: Call<ExperienceResponse>,
                    response: Response<ExperienceResponse>
                ) {
                    getUser(experience.username)
                    println("Added user experience")
                }

                override fun onFailure(call: Call<ExperienceResponse>, t: Throwable) {
                    println("Failed to add user qualification")
                }
            })

        }
    }
}
