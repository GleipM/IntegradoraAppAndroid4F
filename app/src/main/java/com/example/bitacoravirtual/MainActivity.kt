package com.example.bitacoravirtual

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bitacoravirtual.databinding.ActivityBitacoraBinding
import com.example.bitacoravirtual.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSiguiente.setOnClickListener{
            val intent = Intent(this@MainActivity,
                Bitacora::class.java)

            //enviar un valor de la pantalla a otra
            //intent.putExtra("valor", binding.edtTexto.text.toString())

            //stack de Activitys
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

            //si no se escribe el starActivity no hace nada
            startActivity(intent)
        }
    }
}