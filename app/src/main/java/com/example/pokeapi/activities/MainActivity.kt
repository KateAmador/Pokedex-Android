package com.example.pokeapi.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import com.example.pokeapi.R
import com.example.pokeapi.model.Pokemon
import com.example.pokeapi.model.PokemonListResponse
import com.example.pokeapi.service.PokeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var buttonPokemonLayout: LinearLayout
    lateinit var mostrarMasButton: Button
    var offset: Int = 0
    var limit: Int = 20

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonPokemonLayout = findViewById(R.id.layout_boton_pokemon)
        mostrarMasButton = findViewById(R.id.btn_mostrar_mas)

        mostrarMasButton.setOnClickListener {
            offset += limit
            cargarPokemon(limit, offset)
        }

        cargarPokemon(limit, offset)
    }

    private fun cargarPokemon(offset: Int, limit: Int) {
        val retrofit = Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val pokeApiService = retrofit.create(PokeApiService::class.java)

        pokeApiService.obtenerListaPokemon(offset, limit)
            .enqueue(object : Callback<PokemonListResponse> {
                override fun onResponse(
                    call: Call<PokemonListResponse>,
                    response: Response<PokemonListResponse>
                ) {
                    if (response.isSuccessful) {
                        val pokeList = response.body()?.results
                        pokeList?.forEach {
                            crearBoton(it)
                        }
                    }
                }

                override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                    Log.e("MainActivity", "Error al obtener la lista de Pokémon", t)
                }
            })
    }

    private fun crearBoton(pokemon: Pokemon) {
        val button = Button(this)
        button.text = "${pokemon.name}"
        button.setOnClickListener {
            val intent = Intent(this, DescripcionPokemonActivity::class.java)
            intent.putExtra("url", pokemon.url)
            startActivity(intent)
        }
        buttonPokemonLayout.addView(button)
    }
}