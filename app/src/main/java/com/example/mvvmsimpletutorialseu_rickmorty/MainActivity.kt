package com.example.mvvmsimpletutorialseu_rickmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mvvmsimpletutorialseu_rickmorty.network.ApiClient
import com.example.mvvmsimpletutorialseu_rickmorty.network.CharacterResponse
import com.example.mvvmsimpletutorialseu_rickmorty.network.MainAdapter
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = ApiClient.apiService.fetchCharacters("1")

        client.enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("SUCCESSFULLY", ""+response.body())
                    val result = response.body()?.result
                    result?.let {
                        val adapter = MainAdapter(result)
                        val recyclerview = findViewById<RecyclerView>(R.id.charactersRv)

                        recyclerview?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        recyclerview?.adapter = adapter
                    }
                    Toast.makeText(this@MainActivity, "Todo chachi ", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                Log.e("ERRORO", ""+t.message)
                var error = ""
                if (t.message == "Unable to resolve host \"rickandmortyapi.com\": No address associated with hostname"){
                    error = "Te falla el internet, colega"
                }
                Toast.makeText(this@MainActivity, "Failed: $error", Toast.LENGTH_LONG).show()
            }
        })
    }
}