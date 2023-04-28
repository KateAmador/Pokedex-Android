package com.example.pokeapi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.pokeapi.R
import com.example.pokeapi.model.DescripcionPokemon
import com.example.pokeapi.model.PokeType
import com.example.pokeapi.model.TypeName
import com.example.pokeapi.service.PokeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class TypeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_type)

        val type = intent.getStringExtra("type") ?: "defaultType"
        val retrofit = Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val pokeApiService = retrofit.create(PokeApiService::class.java)

        pokeApiService.obtenerType(type).enqueue(object : Callback<PokeType> {
            override fun onResponse(
                call: Call<PokeType>,
                response: Response<PokeType>
            ) {
                if (response.isSuccessful) {
                    val pokeType = response.body()
                    val pokemonList = response.body()?.pokemon
                    val filteredList = pokemonList?.take(5)
                    if (filteredList != null) {

                        val pokemon1 = filteredList[0].pokemon.name
                        val pokemon2 = filteredList[1].pokemon.name
                        val pokemon3 = filteredList[2].pokemon.name
                        val pokemon4 = filteredList[3].pokemon.name
                        val pokemon5 = filteredList[4].pokemon.name

                        findViewById<TextView>(R.id.tipo1).text = "1. ".plus(pokemon1.capitalize(Locale.getDefault()))
                        findViewById<TextView>(R.id.tipo2).text = "2. ".plus(pokemon2.capitalize(Locale.getDefault()))
                        findViewById<TextView>(R.id.tipo3).text = "3. ".plus(pokemon3.capitalize(Locale.getDefault()))
                        findViewById<TextView>(R.id.tipo4).text = "4. ".plus(pokemon4.capitalize(Locale.getDefault()))
                        findViewById<TextView>(R.id.tipo5).text = "5. ".plus(pokemon5.capitalize(Locale.getDefault()))

                        findViewById<TextView>(R.id.nombre).text = pokeType?.name!!.capitalize(Locale.getDefault()).plus(" Type")
                    }
                }
            }

            override fun onFailure(call: Call<PokeType>, t: Throwable) {
            }
        })
    }
}
