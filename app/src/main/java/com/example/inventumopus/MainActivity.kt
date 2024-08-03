package com.example.inventumopus

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inventumopus.ui.theme.InventumOpusTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : ComponentActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var jobsAdapter: JobsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.jobsRecyclerView)


        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://inventumopusapi.up.railway.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val jobsData = retrofitBuilder.getRandomJobs()
        val randomJobData = retrofitBuilder.getRandomJob()

        jobsData.enqueue(object : Callback<Jobs?> {
            override fun onResponse(call: Call<Jobs?>, response: Response<Jobs?>) {
                var responseBody = response.body()
                var jobs = responseBody?.jobs!!

                jobsAdapter = JobsAdapter(this@MainActivity, jobs)
                recyclerView.adapter = jobsAdapter
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            }

            override fun onFailure(call: Call<Jobs?>, t: Throwable) {
                println(t.message)
            }
        })


//        Random Job data handler
        randomJobData.enqueue(object : Callback<Job?> {
            override fun onResponse(call: Call<Job?>, response: Response<Job?>) {
                var responseBody = response.body()


            }

            override fun onFailure(call: Call<Job?>, t: Throwable) {
                println(t.message)
            }
        })
    }
}

