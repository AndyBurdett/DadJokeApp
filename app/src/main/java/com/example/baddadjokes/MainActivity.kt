package com.example.baddadjokes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun hitme(view:View){
        val retrofit =Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://icanhazdadjoke.com/")
            .build()

        val api = retrofit.create(Api::class.java)

        val call = api.getJoke()

        call.enqueue(object:Callback<Joke>{
            override fun onFailure(call: Call<Joke>, t: Throwable) {
                jokeText.setText("Oh no it went wrong")
            }

            override fun onResponse(call: Call<Joke>, response: Response<Joke>) {
                jokeText.setText(response?.body()?.joke)
            }

        })
    }
}

interface Api {
    @GET("/")
    @Headers("Accept: application/json")
    fun getJoke() : Call<Joke>
}

data class Joke (val id:String, val joke:String, val status:String)