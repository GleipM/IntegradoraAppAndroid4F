package com.example.bitacoravirtual

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bitacoravirtual.databinding.ActivityPerfilBinding
import org.json.JSONObject

@Suppress("UNREACHABLE_CODE")
class Perfil : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.bottomAppBar)
        val correo = intent.getStringExtra("correo")

        val queue = Volley.newRequestQueue(this)
        val metodo = Request.Method.GET
        val endpoint = Endpoint().endpoint + "alumnos/" + correo
        val listener = Response.Listener<JSONObject> { response ->
            val codigo = response.getInt("codigo")
            if (codigo == 200) {
                val data = response.getJSONObject("data")
                val nombre = data.getString("nombre")
                val apellido = data.getString("apellido")
                val matricula = data.getString("matricula")
                val correo = data.getString("correo")

                binding.txtNombres.setText(nombre)
                binding.txtApellidos.setText(apellido)
                binding.txtMatricula.setText(matricula)
                binding.txtCorreo.setText(correo)


            } else {
                Toast.makeText(this, "Error al obtener los datos", Toast.LENGTH_SHORT).show()
            }
        }
        val errorListener = Response.ErrorListener { error ->
            Log.e("Error Volley", error.toString())
            Toast.makeText(this, "Error al obtener los datos", Toast.LENGTH_SHORT).show()
        }
        val request = JsonObjectRequest(metodo, endpoint, null, listener, errorListener)
        queue.add(request)



        binding.btnEditarPerfil.setOnClickListener {
            val nombre = binding.txtNombres.text.toString().trim()
            val apellido = binding.txtApellidos.text.toString().trim()
            val matricula = binding.txtMatricula.text.toString().trim()

            if (nombre.isEmpty() || apellido.isEmpty() || matricula.isEmpty()) {
                binding.txtNombres.error = "Este campo es requerido"
                binding.txtApellidos.error = "Este campo es requerido"
                binding.txtMatricula.error = "Este campo es requerido"

            } else {
                val endpoint = Endpoint().endpoint + "alumnos/" + correo
                val metodo = Request.Method.PUT
                val body = JSONObject().apply {
                    put("nombre", nombre)
                    put("apellido", apellido)
                    put("matricula", matricula)
                }

                val listener = Response.Listener<JSONObject> { response ->
                    val codigo = response.getInt("codigo")
                    if (codigo == 200) {
                        Toast.makeText(this, "Actualizacion exitosa", Toast.LENGTH_SHORT).show()


                    } else {
                        Toast.makeText(this, "Error al Actualizar", Toast.LENGTH_SHORT).show()
                    }


                }
                val errorListener = Response.ErrorListener { error ->
                    Log.e("Error Volley", error.toString())
                    Toast.makeText(this, "Error al Actualizar", Toast.LENGTH_SHORT).show()


                }
                val request = JsonObjectRequest(metodo, endpoint, body, listener, errorListener)
                queue.add(request)

            }

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_borrar, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnuBorrar -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Eliminar Cuenta")
                builder.setMessage("¿Estás seguro de que deseas eliminar tu cuenta?")
                builder.setPositiveButton("Sí") { _, _ ->
                    val correo = intent.getStringExtra("correo")
                    val queue = Volley.newRequestQueue(this)
                    val metodo = Request.Method.DELETE
                    val endpoint = Endpoint().endpoint + "alumnos/" + correo
                    val listener = Response.Listener<JSONObject> { response ->
                        val codigo = response.getInt("codigo")
                        if (codigo == 204) {
                            Toast.makeText(this, "Cuenta eliminada exitosamente", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    val errorListener = Response.ErrorListener { error ->
                        Log.e("Error Volley", error.toString())
                        Toast.makeText(this, "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show()
                    }
                    val request = JsonObjectRequest(metodo, endpoint, null, listener, errorListener)
                    queue.add(request)
                }
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
