package com.example.machinetestandroid.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.machinetestandroid.data.network.responses.Movie

@Dao
interface MovieDao {

    @Insert
    suspend fun addMovie(movie: List<Movie>)

    @Query("SELECT * FROM movie")
    suspend fun getAllMovies() : List<Movie>

    @Query("DELETE FROM movie")
    suspend fun deleteAllMovies()
}