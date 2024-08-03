package com.example.inventumopus

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import org.w3c.dom.Text


class JobsAdapter(val context: Activity, val JobsList: List<Job>) :
    RecyclerView.Adapter<JobsAdapter.JobsViewHolder>() {

    // If layout manager fails to create a view for some data then this method is used
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsAdapter.JobsViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.each_listing, parent, false)
        return JobsViewHolder(itemView)
    }

    // populate the view with data
    override fun onBindViewHolder(holder: JobsAdapter.JobsViewHolder, position: Int) {
        val currentItem = JobsList[position]
        holder.title.text = currentItem.name
        holder.salary.text = currentItem.salary
        holder.location.text = currentItem.location

        // show image
        Picasso.get().load(currentItem.image).into(holder.image)
    }

    // returns the total number of job listings
    override fun getItemCount(): Int {
        return JobsList.size
    }

    class JobsViewHolder(jobView : View) : RecyclerView.ViewHolder(jobView) {
        var image: ShapeableImageView
        var title: TextView
        var salary: TextView
        var location: TextView

        init {
            image = itemView.findViewById(R.id.jobImage)
            title = itemView.findViewById(R.id.jobTitle)
            salary = itemView.findViewById(R.id.jobSalary)
            location = itemView.findViewById(R.id.jobLocation)
        }
    }
}