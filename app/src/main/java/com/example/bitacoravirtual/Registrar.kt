package com.example.bitacoravirtual

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bitacoravirtual.databinding.ActivityRegistrarBinding
import org.json.JSONObject

class Registrar : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val queue = Volley.newRequestQueue(this)

        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.etNombre.text.toString().trim()
            val apellido = binding.edtApellido.text.toString().trim()
            val matricula = binding.edtMaricula.text.toString().trim()
            val correo = binding.edtCorreo.text.toString().trim()
            val contra = binding.edtContra.text.toString().trim()

            if (nombre.isEmpty() || apellido.isEmpty() || matricula.isEmpty() || correo.isEmpty()) {
                binding.etNombre.error = "Este campo es requerido"
                binding.edtApellido.error = "Este campo es requerido"
                binding.edtMaricula.error = "Este campo es requerido"
                binding.edtCorreo.error = "Este campo es requerido"
                binding.edtContra.error = "Este campo es requerido"

            }else{
                val endpoint = endpoint().endpoint + "alumnos"
                val metodo = Request.Method.POST
                val body = JSONObject().apply {
                    put("nombre", nombre)
                    put("apellido", apellido)
                    put("matricula", matricula)
                    put("correo", correo)
                    put("contrasena", contra)
                }

                val listener = Response.Listener<JSONObject> { response ->
                    val codigo = response.getInt("codigo")
                    if (codigo == 201) {
                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        finish()

                }else{
                        Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
                    }
                }
                val errorListener = Response.ErrorListener { error ->
                    Log.e("Error Volley", error.toString())
                    Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
                }
                val request = JsonObjectRequest(metodo, endpoint, body, listener, errorListener)
                queue.add(request)

            }

        }



    }

}