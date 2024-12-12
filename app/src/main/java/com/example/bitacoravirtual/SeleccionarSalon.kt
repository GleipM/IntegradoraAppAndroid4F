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
import com.example.bitacoravirtual.databinding.ActivitySeleccionarSalonBinding
import org.json.JSONObject

class SeleccionarSalon : AppCompatActivity() {
    private lateinit var binding: ActivitySeleccionarSalonBinding
    private lateinit var listaSalones: MutableList<JSONObject>  // Lista para almacenar los objetos de salón

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeleccionarSalonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val correo = intent.getStringExtra("correo")

        val queue = Volley.newRequestQueue(this)
        val metodo = Request.Method.GET
        val endpoint = endpoint().endpoint + "salones"
        val body = null

        val listener = Response.Listener<JSONObject> { resultado ->
            listaSalones = mutableListOf()  // Inicializamos la lista vacía
            val lista = mutableListOf<String>()
            val arreglo = resultado.getJSONArray("data")  // Accedemos a "data"

            // Llenamos la lista con los objetos JSON de salones
            for (i in 0 until arreglo.length()) {
                val salon = arreglo.getJSONObject(i)
                listaSalones.add(salon)  // Agregamos el objeto JSON a la lista
                lista.add(
                    salon.getString("nombre") + "\n" +
                            salon.getString("ubicacion") + "\n" +
                            salon.getString("capacidad")
                )
            }

            // Establecemos el adaptador con la lista de salones
            val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista)
            binding.lvSalon.adapter = adaptador
        }

        val error = Response.ErrorListener { error ->
            // Manejo de errores
        }

        val request = JsonObjectRequest(metodo, endpoint, body, listener, error)
        queue.add(request)

        // Aquí accedemos a la lista de salones para obtener el objeto correcto
        binding.lvSalon.setOnItemClickListener { parent, view, position, id ->
            val personaSeleccionada = listaSalones[position]  // Accedemos al salón desde la lista
            val intent = Intent(this, SeleccionarEquipo::class.java)
            intent.putExtra("correo", correo)
            intent.putExtra("id_salon", personaSeleccionada.getInt("id"))  // Enviamos el "id" del salón
            startActivity(intent)
        }
    }
}

