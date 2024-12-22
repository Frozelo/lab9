package com.example.lab9.api

import com.google.gson.annotations.SerializedName


data class OMDbSearchResponse(
    @SerializedName("Search") val search: List<OMDbMovie>?,
    @SerializedName("totalResults") val totalResults: String?,
    @SerializedName("Response") val response: String,
    @SerializedName("Error") val error: String?
)

data class OMDbMovie(

    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Poster") val poster: String
)
