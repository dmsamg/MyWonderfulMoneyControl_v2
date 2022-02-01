package edu.davidmarhuenda.mywonderfulmoneycontrol_v2.fragments

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.R
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.model.Movements
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.utils.ManagePermissions
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.utils.MyFunctions
import java.util.*

/**
 * clase para añadir un movimiento
 */
class AddMovFragment : Fragment() {

//    var dia : String? = null
//    var mes : String? = null
//    var anio : String? = null
//    var catSelected : String? = null
//
//    //cámara
//    private val CAMERA_REQUEST = 1001
//    private lateinit var managePermission: ManagePermissions
//
//    var newMovementList: MutableList<Movements> = ArrayList()
//
//    //atributo para "inflar" la vista
//    private lateinit var binding: AddingMovementsActivityBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        //"inflar" vista
//        binding = AddingMovementsActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        //cargamos las categorías en el spinner
//        setSpinner()
//
//        //botón del DatePicker
//        binding.btAddDate.setOnClickListener { datePicker() }
//
//        // botón para aceptar nuevo movimiento
//        binding.btAccept.setOnClickListener{ addNewMovement() }
//
//        //cancelar rellenar movimiento
//        binding.btAddCancel.setOnClickListener { limpiarFormulario() }
//
//        //boton abrir cámara
//        binding.btAddPhoto.setOnClickListener { openCamera() }
//
//    }
//
//
//    //---------SPINNER----------//
//
//    fun setSpinner() : String? {
//
//        /**
//         *  Spinner: se crea el Adapter, uniendo la fuente de datos con una vista
//         *  por defecto para el spinnerCategory de Android.
//         */
//        val adapter = ArrayAdapter(
//            this,
//            android.R.layout.simple_spinner_item,
//            MyFunctions().getCategoriesList()
//        )
//
//        //Spinner: Se especifica el diseño que debe utilizarse para mostrar la lista.
//        adapter.setDropDownViewResource(
//            android.R.layout.simple_spinner_dropdown_item
//        )
//
//        binding.spinnerCategory.adapter = adapter
//
//        //se carga el Adapter en el Spinner
//        binding.spinnerCategory.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onNothingSelected(p0: AdapterView<*>?) {}
//
//                override fun onItemSelected(
//                    p0: AdapterView<*>?, p1: View?,
//                    p2: Int, p3: Long
//                ) {
//                    if (p2 == 0)
//                        Toast.makeText(
//                            applicationContext,
//                            "Ningún elemento seleccionado.",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    else Toast.makeText(
//                        applicationContext,
//                        "${binding.spinnerCategory.getItemAtPosition(p2)}!",
//                        Toast.LENGTH_LONG
//                    ).show()
//
//                    catSelected = binding.spinnerCategory.getItemAtPosition(p2).toString()
//                }
//            }
//        return catSelected
//    }
//
//    //----------DATEPICKER--------//
//    /**
//     * DatePicker para introducir la fecha
//     */
//    fun datePicker(){
//        val cal = Calendar.getInstance()
//        val dateSetListener = DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
//            cal.set(Calendar.YEAR, i)
//            cal.set(Calendar.MONTH, i2)
//            cal.set(Calendar.DAY_OF_MONTH, i3)
//
//            val mm = cal.get(Calendar.MONTH)+1
//
//            dia = "${cal.get(Calendar.DAY_OF_MONTH)}"
//            mes = "$mm"
//            anio = "${cal.get(Calendar.YEAR)}"
//
//            binding.tvIntroDate.text = "${cal.get(Calendar.DAY_OF_MONTH)}" +
//                    "/$mm" +
//                    "/${cal.get(Calendar.YEAR)}"
//        }
//
//        // Dentro de with(binding) se especifica el contexto
//        DatePickerDialog(
//            this,
//            dateSetListener,
//            cal.get(Calendar.YEAR),
//            cal.get(Calendar.MONTH),
//            cal.get(Calendar.DAY_OF_MONTH)
//        ).show()
//
//    }
//
//    //----------NUEVO MOVIMIENTO-------------//
//    /**
//     * función para añadir el nuevo movimiento
//     */
//    fun addNewMovement(){
//
//        MyFunctions().addItemsToListOfMovements(
//            binding.etIntroTitle.text.toString(),
//            dia,
//            mes,
//            anio,
//            binding.etIntroType.text.toString(),
//            setSpinner(),
//            binding.etIntroAmount.text.toString(),
//            "https://i2.wp.com/www.actiludis.com/wp-content/uploads/2016/02/TICKET.png?w=372&ssl=1"
//        )
//
//        newMovementList = MyFunctions().getMovementsList()
//
//
//        val returningIntent = Intent(this, MainActivity::class.java)
//
//        // Se lanza la activity.
//        startActivity(returningIntent)
//
//    }
//
//    //-------------CÁMARA------------//
//    /**
//     * abrimos la cámara
//     */
//    fun openCamera(){
//        //comprobamos si el permiso está concedido
//        // Se comprueba si el permiso en cuestión está concedido.
//        if (ContextCompat.checkSelfPermission(
//                this, Manifest.permission.CAMERA
//            ) == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(
//                //cargo el mensaje desde string.xml usando el método getText()
//                //y las etiquetas <b> para las palabras en negrita
//                this, resources.getText(R.string.permission_granted),
//                Toast.LENGTH_SHORT
//            ).show()
//
//            val intent = Intent(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
//            startActivity(intent)
//        }else{
//            managePermission = ManagePermissions(this,
//                Manifest.permission.CAMERA,
//                CAMERA_REQUEST)
//            managePermission.checkPermissions()
//        }
//    }
//
//    //------------LIMPIAR FORMULARIO--------------//
//    /**
//     * función para limpiar el formulario
//     */
//    fun limpiarFormulario(){
//        binding.etIntroAmount.text.clear()
//        binding.etIntroTitle.text.clear()
//        binding.etIntroType.text.clear()
//        binding.tvIntroDate.text = getString(R.string.date)
//    }
}