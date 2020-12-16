package com.example.machinetestandroid.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.machinetestandroid.data.network.responses.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: List<Movie>)

    @Query("SELECT * FROM movie")
    fun getAllMovies() : LiveData<List<Movie>>

    @Query("DELETE FROM movie")
    suspend fun deleteAllMovies()
}