package edu.davidmarhuenda.mywonderfulmoneycontrol_v2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.R
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.databinding.FragmentCategoriesBinding
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.db.DBOpenHelper
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.model.Categories
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.utils.MyFunctions
import kotlinx.android.synthetic.main.dialog_category_layout.*


/**
 * clase para editar las categorías
 */
class CategoriesFragment : Fragment() {

    var listOfCatg: MutableList<Categories> = mutableListOf()
    var listaCategorias : MutableList<String> = mutableListOf()

    private lateinit var binding: FragmentCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCategoriesBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //llamamos al método para cargar el ListView
        loadListView()

        /**
         * botón añadir categoría
         */
        binding.btCatAdd.setOnClickListener {
            categoriesAlertDialog()
        }

        /**
         * botón refrescar ListView categorías
         */
        binding.btRefresh.setOnClickListener {
            //llamamos al método para cargar el ListView
            loadListView()
        }
    }

    private fun loadCategories() : MutableList<String>{

        //cargamos la lista de categorías
        listOfCatg = DBOpenHelper(requireContext(), null).getDBCategories(requireContext())

        for ( it in 0 .. listOfCatg.size-1){
            listaCategorias.add(listOfCatg[it].category.toString())
        }

        return  listaCategorias
    }

    /**
     * función para cargar el ListView
     */
    private fun loadListView(){

        // Se crea el Adapter uniendo la vista y los datos
        // para el 2º parámetro utilizo la vista predefinida
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, loadCategories())

        // Se asigna el Adapter al Listview.
        binding.lvListOfCatg.adapter = adapter

        // Se utiliza un AdapterView para conocer el elemento pulsado.
        binding.lvListOfCatg.onItemClickListener =
            object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val textoPosicion = "${binding.lvListOfCatg.getItemAtPosition(position)}"
                    deleteCategoryDialog(textoPosicion)
                    Toast.makeText(
                        requireContext(),
                        "${binding.lvListOfCatg.getItemAtPosition(position)}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    /**
     * dialog borrar categoría
     */
    private fun deleteCategoryDialog(textoPosicion : String){

        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("DELETE CATEGORY")

        builder.setMessage("Delete category: $textoPosicion?")

        builder.apply {
            val inflater = layoutInflater
            setView(inflater.inflate(R.layout.dialog_delete_category_layout, null))

            setPositiveButton(android.R.string.ok) { dialog, _ ->
                val index = listaCategorias.indexOf(textoPosicion)
                listaCategorias.removeAt(index)//al no usar persistencia se recupera al volver a entrar
                loadListView()
            }
            setNegativeButton(android.R.string.cancel) { dialog, _ ->
                Toast.makeText(
                    context,
                    android.R.string.cancel,
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }
        }
        builder.show()
    }


    /**
     * dialog para añadir una categoría
     */
    private fun categoriesAlertDialog() {

        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("ENTER NEW CATEGORY")

        builder.apply {
            val inflater = layoutInflater
            setView(inflater.inflate(R.layout.dialog_category_layout, null))

            setPositiveButton(android.R.string.ok) { dialog, _ ->
                // En este caso, se accede a los elementos del layout
                // haciendo uso de Synthetic Binding.
                val name = (dialog as AlertDialog).etIntroNewCatg.text.toString()
                DBOpenHelper(requireContext(),null).addCategory(name)
                loadListView()
            }
            setNegativeButton(android.R.string.cancel) { dialog, _ ->
                Toast.makeText(
                    context,
                    android.R.string.cancel,
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }
        }
        builder.show()
    }
}