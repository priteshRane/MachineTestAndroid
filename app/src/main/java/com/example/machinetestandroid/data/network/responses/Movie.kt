package com.example.machinetestandroid.data.network.responses

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Movie(
    @PrimaryKey var _id: String,
    var name: String,
    var year: Int,
    var rating: Double,
    var yourRating: Int,
    var genres: String,
    var description: String,
    var duration: String,
    var directors: String,
    var writers: String,
    var stars: String,
    var posterUrl: String,
    var createdAt: String,
    var updatedAt: String,
    var _v: Int?
) : Parcelable