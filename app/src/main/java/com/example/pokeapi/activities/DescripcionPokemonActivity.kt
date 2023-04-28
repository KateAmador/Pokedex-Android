package com.example.pokeapi.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.pokeapi.R
import com.example.pokeapi.model.DescripcionPokemon
import com.example.pokeapi.service.PokeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class DescripcionPokemonActivity : AppCompatActivity() {

    lateinit var pokeImagen: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descripcion_pokemon)

        val url = intent.getStringExtra("url")
        pokeImagen = findViewById(R.id.ImagenPokemon)

        val btnEstadisticas = findViewById<Button>(R.id.btnStast)
        btnEstadisticas.setOnClickListener {
            val intent = Intent(this, StatsPokemonActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }



        val retrofit = Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val pokeApiService = retrofit.create(PokeApiService::class.java)

        pokeApiService.obtenerPokemon(obtenerNumeroUrl(url.toString()))
            .enqueue(object : Callback<DescripcionPokemon> {
                override fun onResponse(
                    call: Call<DescripcionPokemon>,
                    response: Response<DescripcionPokemon>
                ) {
                    if (response.isSuccessful) {
                        runOnUiThread {
                            val pokemon = response.body()
                            if (pokemon != null) {

                                cargarImagen(pokemon.sprites.front_default)

                                findViewById<TextView>(R.id.nombre).text = pokemon.name.capitalize(Locale.getDefault())
                                findViewById<TextView>(R.id.height).text = "HEIGHT: " + pokemon.height.toString()
                                findViewById<TextView>(R.id.weight).text = "WEIGHT: " + pokemon.weight.toString()

                                val type = pokemon.types
                                val typePoke = type[0].type.name

                                val btnType = findViewById<Button>(R.id.btnType)
                                btnType.text = typePoke.capitalize(Locale.getDefault())

                                btnType.setOnClickListener {
                                    val intent = Intent(this@DescripcionPokemonActivity, TypeActivity::class.java)
                                    intent.putExtra("type", typePoke)
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<DescripcionPokemon>, t: Throwable) {
                }
            })
    }

    fun obtenerNumeroUrl(url: String): String {
        val urlSinBarra = url.substringBeforeLast("/")
        val urlNumero = urlSinBarra.substringAfterLast("/")
        return urlNumero
    }

    fun cargarImagen(url: String) {
        Glide.with(this).load(url).into(pokeImagen)
    }
}



