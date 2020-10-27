package com.example.yogacomponentdemo.ServerHelper

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class ServerHelper {

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

    interface ReceiveMessageCallback{
        fun onReceive(result:JSONObject)
        fun onFailure()
    }

    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl(Config.address)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

        var service = retrofit.create(APIService::class.java)
    }



    fun getEvaluateMessage(jsonObj: JsonObject,callback:ReceiveMessageCallback) {
        Log.w("ServerHelper","getEvaluateMessage" )
        service.getVectors(jsonObj).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("service post", t.message)
                callback.onFailure()
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: retrofit2.Response<ResponseBody>
            ) {

                Log.w("getEvaluateMessage", response.toString());
                if(response.code() != 200){
                    callback.onFailure()
                }
                val msg = response.body()?.string()
                msg?.apply {

                    val result = JSONObject(this)
                    Log.e(
                        "In getEvaluateMessage",
                        "---TTTT :: POST msg from server :: " + result.toString()
                    )

                    callback.onReceive(result);
                }

            }

        })
    }

    fun getStepMessages(jsonObj: JsonObject,callback:ReceiveMessageCallback) {
        service.GetStepMessages(jsonObj).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("service post", t.message)
                callback.onFailure()
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: retrofit2.Response<ResponseBody>
            ) {
                Log.w("ServerHelper", response.toString())
                val msg = response.body()?.string()

                msg?.apply {

                    val result = JSONObject(this)
                    Log.e(
                        "In getEvaluateMessage",
                        "---TTTT :: POST msg from server :: " + result.toString()
                    )
                    callback.onReceive(result);
                }
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