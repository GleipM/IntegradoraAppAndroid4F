package com.example.bitacoravirtual

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.MessageQueue
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.bitacoravirtual.databinding.ActivityBitacoraBinding
import java.io.File
import java.util.Queue
import java.util.UUID

class Bitacora : AppCompatActivity() {
    private lateinit var binding: ActivityBitacoraBinding
    private lateinit var photo: File
    private lateinit var queue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBitacoraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        queue = Volley.newRequestQueue(this)

        binding.edtFecha.setOnClickListener {
            showDatePickerDialog()
        }


        binding.btnFoto.setOnClickListener {
            photo = createBlankTempFile()
            val uri = FileProvider.getUriForFile(
                this,
                "com.example.bitacoravirtual.fileprovider",
                photo
            )
            cameraLauncher.launch(uri)

        }

        binding.btnEnviar.setOnClickListener {

        }
    }

    private val cameraLauncher =
        registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { result ->
            if (result) {
                // Mostrar la imagen en el ImageView
                binding.imgPreview.setImageURI(photo.toUri())

                // Actualizar el formulario con la URI de la imagen
                binding.edtObservaciones.setText(photo.absolutePath)
            } else {
                Toast.makeText(this, "No se pudo tomar la foto", Toast.LENGTH_SHORT).show()
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



    private fun createBlankTempFile(): File {
        val tempFolder = File(applicationContext.filesDir, "photos")
        tempFolder.deleteRecursively()
        tempFolder.mkdir()
        val tempPhoto = File(tempFolder, "tempPhoto${UUID.randomUUID()}.jpg")
        return tempPhoto
    }

    private fun uploadMultipartToServer(uri: android.net.Uri) {
        val url = endpoint().endpoint + "bitacoras"
        val inputStream = contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes() ?: byteArrayOf()

        val params = mapOf(
            "nombre" to "hanna",
            "apellido" to "lopez",
            "fecha" to "2024-11-27",
            "lugar" to "UTEZ"
        )

        val multipartRequest = MultipartRequest(
            url,
            bytes,
            params,
            { response ->
                Toast.makeText(this, "Persona agregada correctamente", Toast.LENGTH_SHORT).show()
            },
            { error ->
                Toast.makeText(this, "Error al agregar persona", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(multipartRequest)
    }



}