package com.kaveri.byjutestapp.model.repository

import com.kaveri.byjutestapp.model.dataobject.Test
import com.kaveri.byjutestapp.model.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Response

class NetworkRepository() : INetworkRepository {

    /**
     * Network request to read the Test data from the backend
     * @param successCallback to send the test result back to the caller - In this case, the Repository
     * */
    override fun getTestData(successCallback: (test: Test?) -> Unit, failureCallback: ((errorMessage:String) -> Unit)) {

        RetrofitBuilder.retrofitService.getTestData().enqueue(object : retrofit2.Callback<Test> {
            override fun onResponse(call: Call<Test>, response: Response<Test>) {
                print("Call response received ${response.body()}")
                if(response.isSuccessful) {
                    successCallback.invoke(response.body())
                } else {
                    failureCallback.invoke(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<Test>, t: Throwable) {
                print("Call failure ${t.stackTrace}")
            }

        })

    }
}