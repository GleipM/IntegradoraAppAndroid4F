package com.example.bitacoravirtual

import android.app.DatePickerDialog
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

        var idSalon = intent.getIntExtra("id_salon", -1)
        var idEquipo = intent.getIntExtra("id_equipo", -1)
        var correo = intent.getStringExtra("correo")

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
            val horaEntrada = binding.edtHoraEntrada.text.toString().trim()
            val horaSalida = binding.edtHoraSalida.text.toString().trim()
            val maestro = binding.edtMaestro.text.toString().trim()
            val grado = binding.edtGrado.text.toString().trim()
            val grupo = binding.edtGrupo.text.toString().trim()
            val observaciones = binding.edtObservaciones.text.toString().trim()

            if (fecha.isEmpty() || horaEntrada.isEmpty() || horaSalida.isEmpty() || maestro.isEmpty() || grado.isEmpty() || grupo.isEmpty() || observaciones.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Llamar a la función para subir datos y foto
                uploadMultipartToServer(photo.toUri())
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

                // Actualizar el formulario con la URI de la imagen
                binding.edtObservaciones.setText(photo.absolutePath)
            } else {
                Toast.makeText(this, "No se pudo tomar la foto", Toast.LENGTH_SHORT).show()
            }
        }




    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // Formatear la fecha como yyyy-MM-dd
            val selectedDate = "$year-${month + 1}-$day"
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

        val idSalon = intent.getIntExtra("id_salon", -1)
        val idEquipo = intent.getIntExtra("id_equipo", -1)
        val correo = intent.getStringExtra("correo")

        // Campos adicionales requeridos por el backend
        val params = mapOf(
            "id_salon" to idSalon.toString(),
            "id_equipo" to idEquipo.toString(),
            "correo" to correo,
            "fecha" to binding.edtFecha.text.toString().trim(),
            "horaEntrada" to binding.edtHoraEntrada.text.toString().trim(),
            "horaSalida" to binding.edtHoraSalida.text.toString().trim(),
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








}