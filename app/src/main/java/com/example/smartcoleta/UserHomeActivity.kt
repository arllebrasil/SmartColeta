package com.example.smartcoleta

import androidx.appcompat.app.AppCompatActivity
import  kotlinx.coroutines.*
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.beust.klaxon.Klaxon
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class UserHomeActivity : AppCompatActivity() {

    lateinit var spinerEstados:AutoCompleteTextView
    lateinit var spinerCidades:AutoCompleteTextView

    var estados: ArrayList<String> = ArrayList<String>()
    var cidades: ArrayList<String> = ArrayList<String>()

    lateinit var estadosAdapter:ArrayAdapter<String>
    lateinit var cidadesAdapter:ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)

        spinerEstados = findViewById(R.id.spiner_estados)
        spinerCidades = findViewById(R.id.spiner_cidades)

        spinerEstados.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            if (spinerCidades.text.toString().isNotBlank()){
                CoroutineScope(Dispatchers.IO).launch {
                    getUFS()
                }
            }
        })
    }



    suspend fun getUFS() = coroutineScope {
        launch {
            val localidadesApiService = HttpClient()
            val response = localidadesApiService.get("https://servicodados.ibge.gov.br/api/v1/localidades/estados")
            val data = response.bodyAsText()
            cidades = Klaxon().parse<ArrayList<String>>("[\"us1\",\"us2\"]") as ArrayList<String>
        }
    }
}