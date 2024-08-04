package com.example.inventumopus

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class DetailedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detailed)
        supportActionBar?.hide()
        val job = intent.getParcelableExtra<Job>("job")

        if (job != null) {
            println("Test")
            val jobTitle : TextView = findViewById(R.id.jobTitle)
            val jobDesc : TextView = findViewById(R.id.jobDesc)
            val jobCompany : TextView = findViewById(R.id.jobCompany)
            val jobQual : TextView = findViewById(R.id.jobQual)
            val jobImage : ImageView = findViewById(R.id.jobImage)
            val jobExp : TextView = findViewById(R.id.jobExp)


            // show image
            Picasso.get().load(job.image).into(jobImage)

            // show text
            jobTitle.text = job.name
            jobExp.text = job.experience
            jobDesc.text = job.description
            jobCompany.text = job.company
            jobQual.text = job.qualification
        }

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}