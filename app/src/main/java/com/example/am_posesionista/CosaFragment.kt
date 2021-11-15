package com.example.am_posesionista

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "debug"
class CosaFragment: Fragment() {
    private lateinit var cosa:Cosa
    lateinit var nameField: EditText
    private lateinit var priceField: EditText
    private lateinit var serialField: EditText
    private lateinit var dateField: TextView
    private var nameControl: String = ""
    private var serialControl: String = ""
    private lateinit var photoview: ImageView
    private lateinit var btncamera: ImageButton
    private lateinit var archivoFoto: File

    var cameraResponse = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
            if(result.resultCode == Activity.RESULT_OK){
                var data = result.data
                //photoview.setImageBitmap(data?.extras?.get("data") as Bitmap)
                photoview.setImageBitmap(BitmapFactory.decodeFile(archivoFoto.absolutePath))
            }
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        cosa = Cosa()
        cosa = arguments?.getParcelable("CosaRecibida")!!
    }

    override fun onStart(){
        super.onStart()

        if(nameControl.isEmpty()){
            nameControl = nameField.text.toString()
        }

        if(serialControl.isEmpty()){
            serialControl = serialField.text.toString()
        }

        val observador = object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(s.hashCode() == nameField.text.hashCode()) {
                    if(s.toString().isEmpty()){
                        cosa.nombreCosa = nameControl
                        Toast.makeText(context, "Nombre no puede estar vacío",Toast.LENGTH_LONG).show()
                    }else{
                        cosa.nombreCosa = s.toString()
                    }
                }else if (s.hashCode() == priceField.text.hashCode()){
                    if(s.toString().isNotEmpty()){
                        cosa.valorPesos = s.toString().toInt()
                    }else{
                        cosa.valorPesos = 0
                    }
                }else if (s.hashCode() == serialField.text.hashCode()){
                    if(s.toString().isEmpty()){
                        cosa.serialnum = serialControl
                    }else{
                        cosa.serialnum = s.toString()
                    }
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        }
        nameField.addTextChangedListener(observador)
        serialField.addTextChangedListener(observador)
        priceField.addTextChangedListener(observador)
        val barraActividad = activity as AppCompatActivity
        barraActividad.supportActionBar?.setTitle(R.string.detalle_cosa)

        btncamera.apply{
            setOnClickListener{
                val intentTakePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                archivoFoto = getPhotoFile("${cosa.idCosa}.jpg")
                val fileProvider = FileProvider.getUriForFile(context, "com.example.am_posesionista.fileprovider",archivoFoto)
                intentTakePicture.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
                try {
                    cameraResponse.launch(intentTakePicture)
                }catch(e: ActivityNotFoundException){
                    Log.d(TAG, "No se encontró la camara")
                }
        } }
    }

    private fun getPhotoFile(filename: String): File{
        var photoPath = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(photoPath,filename)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "Aqui andamos")
        val view = inflater.inflate(R.layout.cosa_fragment, container, false)
        nameField = view.findViewById(R.id.campoNombreCosa) as EditText
        priceField = view.findViewById(R.id.campoPrecioCosa) as EditText
        serialField = view.findViewById(R.id.campoSerialCosa) as EditText
        dateField = view.findViewById(R.id.labelFecha) as TextView
        photoview = view.findViewById(R.id.foto_cosa) as ImageView
        btncamera = view.findViewById(R.id.btn_camara) as ImageButton
        archivoFoto = File(context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "${cosa.idCosa}.jpg")
        photoview.setImageBitmap(BitmapFactory.decodeFile(archivoFoto.absolutePath))
        nameField.setText(cosa.nombreCosa)
        priceField.setText(cosa.valorPesos.toString())
        serialField.setText(cosa.serialnum)
        dateField.text = processDate(cosa.fechaCreacion)

        return view
    }

    fun processDate(date: Date): String{
        val sdf = SimpleDateFormat.getDateInstance()
        return sdf.format(date)
    }

    companion object {
        fun nuevaInstancia(unaCosa: Cosa): CosaFragment{
            val args = Bundle().apply {
                Log.d(TAG, "COSA: ${unaCosa.nombreCosa}")
                putParcelable("CosaRecibida", unaCosa)
            }
            return CosaFragment().apply {
                arguments = args
            }
        }
    }
}