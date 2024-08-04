package com.example.inventumopus

import android.content.Intent
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
            .baseUrl("https://nauf.wheredoc.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val jobsData = retrofitBuilder.getRandomJobs()

        jobsData.enqueue(object : Callback<Jobs?> {
            override fun onResponse(call: Call<Jobs?>, response: Response<Jobs?>) {
                val responseBody = response.body()
                val jobs = responseBody?.jobs!!

                jobsAdapter = JobsAdapter(this@MainActivity, jobs)
                recyclerView.adapter = jobsAdapter
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

                jobsAdapter.onItemClick = {
                    val intent = Intent(this@MainActivity, DetailedActivity::class.java)
                    intent.putExtra("job", it)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<Jobs?>, t: Throwable) {
                println(t.message)
            }
        })
    }
}

