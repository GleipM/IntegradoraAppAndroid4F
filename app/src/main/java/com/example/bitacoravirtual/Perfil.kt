package com.example.bitacoravirtual

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bitacoravirtual.databinding.ActivityPerfilBinding
import com.example.bitacoravirtual.databinding.ActivitySuppBinding

class Perfil : AppCompatActivity() {
    private lateinit var binding: ActivityPerfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}