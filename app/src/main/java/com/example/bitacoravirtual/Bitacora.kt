package com.example.bitacoravirtual

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.bitacoravirtual.databinding.ActivityBitacoraBinding
import java.io.File
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
            val fecha = binding.edtFecha.text.toString().trim()
            val horaEntrada = binding.edtHoraEntrada.text.toString()
            val horaSalida = binding.edtHoraSalida.text.toString()
            val maestro = binding.edtMaestro.text.toString().trim()
            val grado = binding.edtGrado.text.toString().trim()
            val grupo = binding.edtGrupo.text.toString().trim()
            val observaciones = binding.edtObservaciones.text.toString().trim()

            if (fecha.isEmpty() || horaEntrada.isEmpty() || horaSalida.isEmpty() || maestro.isEmpty() || grado.isEmpty() || grupo.isEmpty() || observaciones.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Llamar a la función para subir datos y foto
                uploadMultipartToServer(photo.toUri())
                val intent = Intent(this, Registrar::class.java)
                startActivity(intent)
                finish()
            }
        }


    }

    private val cameraLauncher =
        registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { result ->
            if (result) {
                // Mostrar la imagen en el ImageView
                binding.imgPreview.setImageURI(photo.toUri())
            } else {
                Toast.makeText(this, "No se pudo tomar la foto", Toast.LENGTH_SHORT).show()
            }
        }




    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // Formatear la fecha como yyyy-MM-dd
            val selectedDate = String.format("%04d-%02d-%02d", year, month + 1, day)
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
        val url = Endpoint().endpoint + "bitacoras"
        val inputStream = contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes() ?: byteArrayOf()

        val idSalon = intent.getIntExtra("id_salon", -1)
        val idEquipo = intent.getIntExtra("id_equipo", -1)
        val correo = intent.getStringExtra("correo")

        val horaEntrada = binding.edtHoraEntrada.text.toString().trim()
        val horaSalida = binding.edtHoraSalida.text.toString().trim()

// Formatear las horas para asegurarse de que sean en formato HH:mm:ss
        val horaEntradaFormateada = formatHora(horaEntrada)
        val horaSalidaFormateada = formatHora(horaSalida)


        // Campos adicionales requeridos por el backend
        val params = mapOf(
            "id_salon" to idSalon.toString(),
            "id_equipo" to idEquipo.toString(),
            "correo" to correo,
            "fecha" to binding.edtFecha.text.toString(),
            "horaEntrada" to horaEntradaFormateada,
            "horaSalida" to horaSalidaFormateada,
            "maestro" to binding.edtMaestro.text.toString().trim(),
            "grado" to binding.edtGrado.text.toString().trim(),
            "grupo" to binding.edtGrupo.text.toString().trim(),
            "observaciones" to binding.edtObservaciones.text.toString().trim()
        )

        // Crear la solicitud multipart
        val multipartRequest = object : MultipartRequest(
            url,
            bytes,
            params,
            { response ->
                Toast.makeText(this, "Bitácora registrada correctamente", Toast.LENGTH_SHORT).show()
                finish() // Cerrar la actividad si es necesario
            },
            { error ->
                Log.e("Error Volley", error.toString())
                Toast.makeText(this, "Error al registrar bitácora", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getByteData(): Map<String, ByteArray> {
                val fileData = HashMap<String, ByteArray>()
                fileData["imagen"] = bytes // Usa "imagen" como el nombre del campo del archivo
                return fileData
            }
        }

        // Agregar la solicitud a la cola de Volley
        queue.add(multipartRequest)
    }


    private fun formatHora(hora: String): String {
        val horaParts = hora.split(":")
        return when (horaParts.size) {
            1 -> {
                // Si solo tienes la hora (por ejemplo "8"), añade los minutos y segundos
                String.format("%02d:%02d:%02d", horaParts[0].toInt(), 0, 0)
            }
            2 -> {
                // Si tienes hora y minutos (por ejemplo "8:00"), añade los segundos
                String.format("%02d:%02d:%02d", horaParts[0].toInt(), horaParts[1].toInt(), 0)
            }
            3 -> {
                // Si tienes hora, minutos y segundos (por ejemplo "08:00:00")
                String.format("%02d:%02d:%02d", horaParts[0].toInt(), horaParts[1].toInt(), horaParts[2].toInt())
            }
            else -> {
                // Si el formato es incorrecto, devuelves una hora por defecto
                "00:00:00"
            }
        }
    }






}