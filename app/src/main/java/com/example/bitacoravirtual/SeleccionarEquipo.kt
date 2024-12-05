package com.example.bitacoravirtual

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bitacoravirtual.databinding.ActivitySeleccionarEquipoBinding
import org.json.JSONObject

class SeleccionarEquipo : AppCompatActivity() {
    private lateinit var binding: ActivitySeleccionarEquipoBinding
    private lateinit var listaEquipos: MutableList<JSONObject>  // Lista para almacenar los objetos de salón

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeleccionarEquipoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSiguiente2.setOnClickListener {
            val intent = Intent(this, Bitacora::class.java)
            startActivity(intent)
        }


        val queue = Volley.newRequestQueue(this)
        val metodo = Request.Method.GET
        val endpoint = endpoint().endpoint + "equipos"
        val body = null

        val listener = Response.Listener<JSONObject> { resultado ->
            listaEquipos = mutableListOf()  // Inicializamos la lista vacía
            val lista = mutableListOf<String>()
            val arreglo = resultado.getJSONArray("data")  // Accedemos a "data"

            // Llenamos la lista con los objetos JSON de salones
            for (i in 0 until arreglo.length()) {
                val equipo = arreglo.getJSONObject(i)
                listaEquipos.add(equipo)  // Agregamos el objeto JSON a la lista
                lista.add(
                    equipo.getString("numeroSerie") + "\n" +
                            equipo.getString("marca") + "\n" +
                            equipo.getString("modelo")
                )
            }

            // Establecemos el adaptador con la lista de salones
            val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista)
            binding.lvEquipos.adapter = adaptador
        }

        val error = Response.ErrorListener { error ->
            // Manejo de errores
        }

        val request = JsonObjectRequest(metodo, endpoint, body, listener, error)
        queue.add(request)

        // Aquí accedemos a la lista de salones para obtener el objeto correcto
        binding.lvEquipos.setOnItemClickListener { parent, view, position, id ->
            val equipoSeleccionado = listaEquipos[position]  // Accedemos al salón desde la lista
            val intent = Intent(this, Bitacora::class.java)
            intent.putExtra("id", equipoSeleccionado.getInt("id"))  // Enviamos el "id" del salón
            startActivity(intent)
        }

    }
}