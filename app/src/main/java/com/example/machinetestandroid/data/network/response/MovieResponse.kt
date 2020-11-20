package com.example.machinetestandroid.data.network.response

data class MovieResponse(
    var page: Int?,
    var totalPages: Int?,
    var pageSize: Int?,
    var totalMovies: Int?,
    var movie: List<Movie>
)