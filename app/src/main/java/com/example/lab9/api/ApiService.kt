package com.example.lab9.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbApiService {
    @GET("/")
    suspend fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") query: String,
        @Query("y") year: String? = null
    ): Response<OMDbSearchResponse>

    @GET("/")
    suspend fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("i") imdbID: String,
        @Query("plot") plot: String = "short"
    ): Response<MovieDetails>
}

data class MovieDetails(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Poster") val poster: String
)
