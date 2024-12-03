package com.example.bitacoravirtual

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
<<<<<<< HEAD
import com.example.bitacoravirtual.databinding.ActivityBitacoraBinding
import com.example.bitacoravirtual.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

=======
import com.example.bitacoravirtual.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSiguiente.setOnClickListener{
            //Intent es una clase que nos permite abrir componentes nuevos
            //intent necesita 2 requisitos
            //1 Componente origen(Contexto)
            //2 Componente destino(::class.java)
            val intent = Intent(this@MainActivity,
                Bitacora::class.java)

            //enviar un valor de la pantalla a otra
            //intent.putExtra("valor", binding.edtTexto.text.toString())

            //stack de Activitys
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

            //si no se escribe el starActivity no hace nada
            startActivity(intent)
        }
>>>>>>> 6166863954984e2d17b4db164e320b8db0e67a92
    }
}