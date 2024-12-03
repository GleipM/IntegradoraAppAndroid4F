package com.example.bitacoravirtual

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
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

            if (nombre.isEmpty() || apellido.isEmpty() || matricula.isEmpty() || correo.isEmpty()) {
                binding.etNombre.error = "Este campo es requerido"
                binding.edtApellido.error = "Este campo es requerido"
                binding.edtMaricula.error = "Este campo es requerido"
                binding.edtCorreo.error = "Este campo es requerido"

            }else{
                val endpoint = endpoint().endpoint
                val metodo = Request.Method.POST
                val body = JSONObject().apply {
                    put("nombre", nombre)
                    put("apellido", apellido)
                    put("matricula", matricula)
                    put("correo", correo)
                    put("contrasena", "contrasena")
                }

                val listener = Response.Listener<JSONObject> { response ->
                    val codigo = response.getInt("codigo")
                    if (codigo == 200) {
                }else{
                    Toast.makeText(this, codigo, Toast.LENGTH_SHORT).show()


                    }
                }


            }

        }


    }

}