package com.kaveri.byjutestapp.model.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    val retrofitService: ITestDataService by lazy {
        getRetrofit().create(ITestDataService::class.java)
    }
    private const val BASE_URL = "https://assessed-da.s3-ap-southeast-1.amazonaws.com"

    private fun getRetrofit() : Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}