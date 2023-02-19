package com.project.v1.testing.recipesproject1.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


// TODO: Code to create a Retrofit Instance
object RetrofitInstanceSpoontacular {

    // TODO: Define the URL you want to connect to
    private val BASE_URL:String = "https://api.spoonacular.com/"

    // setup a client with logging
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger { message ->
                println("LOG-APP: $message")
            }).apply {
            level= HttpLoggingInterceptor.Level.BODY
        }).build()
    //.build()

//    private val httpClientNew = OkHttpClient.Builder()
//        .addInterceptor(Interceptor {
//
//            val original = it.request()
//
//            val originalHttpUrl = original.url
//
//            val url = originalHttpUrl.newBuilder()
//                .addQueryParameter("apiKey", "d01c0f4e6a324d2c861e9b967a6e5d87")
//                .build()
//
//            val requestBuilder = original.newBuilder().url(url)
//
//            val request = requestBuilder.build()
//
//            it.proceed(request)
//        }).build()

    object NULL_TO_EMPTY_STRING_ADAPTER {
        @FromJson
        fun fromJson(reader: JsonReader): String {
            if (reader.peek() != JsonReader.Token.NULL) {
                return reader.nextString()
            }
            reader.nextNull<Unit>()
            return ""
        }
    }

    // used to ensure Moshi annotations work with Kotlin
    private val moshi = Moshi.Builder()
        .add(NULL_TO_EMPTY_STRING_ADAPTER)
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
    val retrofitRecipeService: ISpoontacularResponse by lazy {
        retrofit.create(ISpoontacularResponse::class.java)
    }

}

