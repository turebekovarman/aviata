package com.example.aviatickets.model.entity

data class Location(
    @SerializedName("city_name")
    val cityName: String,
    val code: String
)