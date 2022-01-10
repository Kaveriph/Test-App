package com.kaveri.byjutestapp.model.repository

import com.kaveri.byjutestapp.model.dataobject.Test

interface INetworkRepository {

    fun getTestData(
        successCallback: (test: Test?) -> Unit,
        failureCallback: (errorMessage: String) -> Unit
    )
}
