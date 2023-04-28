package com.example.pokeapi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import java.util.Locale

class StatsPokemonActivity : AppCompatActivity() {

    lateinit var pokeImagen: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats_pokemon)

        val url = intent.getStringExtra("url")
        pokeImagen = findViewById(R.id.ImagenPokemon)

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

                                val tvNombrePokemon: TextView = findViewById(R.id.nombre)
                                tvNombrePokemon.text = pokemon.name.capitalize(Locale.getDefault())

                                val stats = pokemon.stats
                                val hp = stats[0].base_stat
                                val atk = stats[1].base_stat
                                val def = stats[2].base_stat
                                val spd = stats[5].base_stat
                                val satk = stats[3].base_stat
                                val sdef = stats[4].base_stat

                                findViewById<TextView?>(R.id.hp).text = "HP: $hp"
                                findViewById<TextView?>(R.id.atk).text = "ATK: $atk"
                                findViewById<TextView?>(R.id.def).text = "DEF: $def"
                                findViewById<TextView?>(R.id.spd).text = "SPD: $spd"
                                findViewById<TextView?>(R.id.satk).text = "S-ATK: $satk"
                                findViewById<TextView?>(R.id.sdef).text = "S-DEF: $sdef"
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