package com.kerko.ne.kuran.AsyncTasks

import android.os.AsyncTask
import com.google.gson.GsonBuilder
import com.kerko.ne.kuran.interfaces.KNKAPIInterface
import com.kerko.ne.kuran.models.ResponseModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class KNKAsyncTask :
    AsyncTask<String?, Int?, String>() {

    val API_URL = "https://ivasija.com"

    override fun doInBackground(vararg p0: String?): String {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val gerritAPI: KNKAPIInterface = retrofit.create(KNKAPIInterface::class.java)

        val call: Call<ResponseModel?>? =
            gerritAPI.sendAPI(p0[0],p0[1])
        if (call != null) {
            lateinit var test: Call<ResponseModel?>
            val contributors: ResponseModel? = call.execute().body()
            return contributors?.Message.toString()
        }
        return ""
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }
}
