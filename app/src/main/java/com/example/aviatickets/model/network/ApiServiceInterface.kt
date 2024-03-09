package com.example.aviatickets.model.network

interface ApiServiceInterface {
    @GET("fake-api-demo/offer_list")
    fun getFlights(): Call<List<Offer>>
}