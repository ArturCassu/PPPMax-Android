package com.example.pppmax


import android.os.AsyncTask
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class testeApi : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiKey = "14d85696826338f3be7b526bcea0ea65"
        val url = "https://api.themoviedb.org/3/movie/popular"

        FetchMoviesTask().execute(url, apiKey)
    }

    private inner class FetchMoviesTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            val url = params[0]
            val apiKey = params[1]

            val request = Request.Builder()
                .url("$url?api_key=$apiKey")
                .build()

            val client = OkHttpClient()
            val response = client.newCall(request).execute()

            return response.body?.string() ?: ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            if (!result.isNullOrBlank()) {
                val moviesResponse = Gson().fromJson(result, MoviesResponse::class.java)
                moviesResponse.results.forEach { movie ->
                    println("Título: ${movie.title}")
                }
            } else {
                println("Erro na solicitação.")

            }
        }
    }
}

data class MoviesResponse(val results: List<Movie>)

data class Movie(val title: String)