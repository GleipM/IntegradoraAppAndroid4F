package com.example.bitacoravirtual

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bitacoravirtual.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the Volley request queue
        queue = Volley.newRequestQueue(this)

        // Set up the "Siguiente" button to trigger authentication
        binding.btnSiguiente.setOnClickListener {
            val metodo = Request.Method.POST
            val endpoint = endpoint().endpoint + "authenticate"
            val body = JSONObject().apply {
                put("usuario", binding.edtCorreo.text.toString().trim())
                put("contrasenia", binding.edtContrasena.text.toString().trim())
            }

            val jsonObjectRequest = object : JsonObjectRequest(
                metodo, endpoint, body,
                Response.Listener { response ->
                    try {
                        val authToken = response.getString("jwtToken")


                        Log.d("Token", "Token recibido: $authToken")

                        val intent = Intent(this@MainActivity, Home::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    } catch (e: Exception) {
                        Log.e("Error", "Error al obtener el token", e)
                    }
                },
                Response.ErrorListener { error ->
                    Log.e("Volley Error", "Error en la solicitud: ${error.message}")
                    Toast.makeText(this, "Error en la solicitud de autenticación", Toast.LENGTH_SHORT).show()
                }
            ) {}

            queue.add(jsonObjectRequest)
        }

        binding.btnRegistrarse.setOnClickListener {
            val intent = Intent(this, Registrar::class.java)
            startActivity(intent)
        }
    }
}
