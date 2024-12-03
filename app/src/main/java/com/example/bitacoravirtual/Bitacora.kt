package com.example.bitacoravirtual

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bitacoravirtual.databinding.ActivityBitacoraBinding

class Bitacora : AppCompatActivity() {
    lateinit var binding: ActivityBitacoraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBitacoraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.edtFecha.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            binding.edtFecha.setText(selectedDate)
        })

        newFragment.show(supportFragmentManager, "datePicker")
    }
}