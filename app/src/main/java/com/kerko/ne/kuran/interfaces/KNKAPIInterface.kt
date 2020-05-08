package com.kerko.ne.kuran.interfaces

import com.kerko.ne.kuran.models.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query

interface KNKAPIInterface {
    @GET("/KerkoNeKuran/KNKAPI.php")
    fun sendAPI(
        @Query("form") form: String?,
        @Query("message") message: String?
    ): Call<ResponseModel?>?
}