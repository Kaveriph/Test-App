package com.kaveri.byjutestapp.model.retrofit

import com.kaveri.byjutestapp.model.dataobject.Test
import retrofit2.Call
import retrofit2.http.GET

interface ITestDataService {

    //Bse url - https://assessed-da.s3-ap-southeast-1.amazonaws.com/exam/exam.json
    @GET("/exam/exam.json")
    fun getTestData(): Call<Test>

}