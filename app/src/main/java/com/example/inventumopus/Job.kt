package com.example.inventumopus

import android.os.Parcel
import android.os.Parcelable

data class Job(
    val _id: String,
    val category: String,
    val company: String,
    val deadline: String,
    val description: String,
    val experience: String,
    val image: String,
    val location: String,
    val name: String,
    val qualification: String,
    val salary: String,
    val type: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(category)
        parcel.writeString(company)
        parcel.writeString(deadline)
        parcel.writeString(description)
        parcel.writeString(experience)
        parcel.writeString(image)
        parcel.writeString(location)
        parcel.writeString(name)
        parcel.writeString(qualification)
        parcel.writeString(salary)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Job> {
        override fun createFromParcel(parcel: Parcel): Job {
            return Job(parcel)
        }

        override fun newArray(size: Int): Array<Job?> {
            return arrayOfNulls(size)
        }
    }
}