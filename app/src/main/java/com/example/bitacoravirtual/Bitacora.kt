package com.example.bitacoravirtual

import android.R
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
<<<<<<< HEAD
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bitacoravirtual.databinding.ActivityBitacoraBinding

class Bitacora : AppCompatActivity() {
private lateinit var binding: ActivityBitacoraBinding
=======
import com.example.bitacoravirtual.databinding.ActivityBitacoraBinding


class Bitacora : AppCompatActivity() {
    lateinit var binding: ActivityBitacoraBinding
>>>>>>> 6166863954984e2d17b4db164e320b8db0e67a92
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBitacoraBinding.inflate(layoutInflater)
        setContentView(binding.root)
<<<<<<< HEAD
=======
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
>>>>>>> 6166863954984e2d17b4db164e320b8db0e67a92
    }
}