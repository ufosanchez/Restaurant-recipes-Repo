package com.project.v1.testing.recipesproject1.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstanceRestaurant {

    private val BASE_URL:String = "https://travel-advisor.p.rapidapi.com/"

    // setup a client with logging
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger { message ->
                println("LOG-APP: $message")
            }).apply {
            level= HttpLoggingInterceptor.Level.BODY
        })
        .build()

    // used to ensure Moshi annotations work with Kotlin
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // instantiate a Retrofit instance with Moshi as the data converter
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    // update this to return an instance of the Retrofit instance associated
    // with your base url
    val retrofitService: IAPIRestaurantResponse by lazy {
        retrofit.create(IAPIRestaurantResponse::class.java)
    }

}

