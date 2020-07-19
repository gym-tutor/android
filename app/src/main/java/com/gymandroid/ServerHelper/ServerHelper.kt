package com.example.yogacomponentdemo.ServerHelper

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class ServerHelper {
    var result: JSONObject? = null

    interface APIService {
        @GET("/users/{user}")
        fun greetUser(@Path("user") user: String): Call<ResponseBody>

        @Headers("Content-type: application/json")
        @POST("/api/post_some_data")
        fun getVectors(@Body body: JsonObject): Call<ResponseBody>

        @Headers("Content-type: application/json")
        @POST("/api/get_pose_instruction")
        fun GetStepMessages(@Body body: JsonObject): Call<ResponseBody>
    }

    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl(Config.address)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

        var service = retrofit.create(APIService::class.java)
    }

    fun getEvaluateMessage(jsonObj: JsonObject) {
        var msg: String? = ""
        service.getVectors(jsonObj).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("service post", t.message)
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: retrofit2.Response<ResponseBody>
            ) {
                msg = response.body()?.string()
                result = JSONObject(msg)
                Log.e(
                    "In getEvaluateMessage",
                    "---TTTT :: POST msg from server :: " + result.toString()
                )
            }

        })
        Log.e("in Post", msg)
    }

    fun getStepMessages(jsonObj: JsonObject) {
        var msg: String? = ""
        service.GetStepMessages(jsonObj).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("service post", t.message)
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: retrofit2.Response<ResponseBody>
            ) {
                msg = response.body()?.string()
                result = JSONObject(msg)
                Log.e(
                    "In getStepMessages",
                    "---TTTT :: POST msg from server :: " + result.toString()
                )
            }

        })


    }

    fun GET(): JSONObject {
        var msg: String? = ""
        service.greetUser("HI").enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("service get", t.message)
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: retrofit2.Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    msg = response.body()?.string()
                    println("---TTTT :: GET msg from server :: " + msg)

                }
            }

        })
        return JSONObject(msg!!)
    }


}