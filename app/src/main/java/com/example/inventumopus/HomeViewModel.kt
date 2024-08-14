package com.example.inventumopus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventumopus.api.RetrofitInstance
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
                    // it.name.contains(text, ignoreCase = true)
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
            println(status)
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

}




