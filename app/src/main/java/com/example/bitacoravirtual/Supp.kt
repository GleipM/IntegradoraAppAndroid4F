package com.example.bitacoravirtual

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bitacoravirtual.databinding.ActivitySuppBinding
class Supp : AppCompatActivity() {
    private lateinit var binding: ActivitySuppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySuppBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
