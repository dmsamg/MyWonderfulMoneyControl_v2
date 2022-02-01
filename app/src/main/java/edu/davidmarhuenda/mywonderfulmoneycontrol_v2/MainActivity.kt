package edu.davidmarhuenda.mywonderfulmoneycontrol_v2

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.adapters.ViewPager2Adapter
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.databinding.ActivityMainBinding
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.fragments.*

class MainActivity : AppCompatActivity() {

    //atributo para "inflar" la vista
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //"inflar" vista
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se carga la barra de acción.
        setSupportActionBar(binding.myToolBar)

        // Se añade el botón hamburgesa a la toolbar y
        // se vincula con el DrawerLayout.
        val toggle = ActionBarDrawerToggle(
            this,
            binding.myDrawerLayout,
            binding.myToolBar,
            R.string.txt_open,
            R.string.txt_close
        )

        binding.myDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Se crea el adapter.
        val adapter = ViewPager2Adapter(supportFragmentManager, lifecycle)

        // Se añaden los fragments.
        adapter.addFragment(
            InitFragment()
        )
        adapter.addFragment(
            DailyListFragment()
        )
        adapter.addFragment(
            AddMovFragment()
        )
        adapter.addFragment(
            ExpensesMonthYearFragment()
        )
        adapter.addFragment(
            ExpensesCategFragment()
        )
        adapter.addFragment(
            CategoriesFragment()
        )

        // Se asocia el adapter al ViewPager2
        binding.myViewPager2.adapter = adapter

        // Control sobre la opción seleccionada.
        binding.myNavigationView.setNavigationItemSelectedListener {
            it.isChecked = true

            when (it.itemId) {
                R.id.item1 -> {
                    // Se carga el fragment en el ViewPager2.
                    binding.myViewPager2.currentItem = 1
                    // Se cierra el Drawer Layout.
                    binding.myDrawerLayout.close()
                    true
                }
                R.id.item2 -> {
                    binding.myViewPager2.currentItem = 2
                    binding.myDrawerLayout.close()
                    true
                }
                R.id.item3 -> {
                    binding.myViewPager2.currentItem = 3
                    binding.myDrawerLayout.close()
                    true
                }
                R.id.item4 -> {
                    binding.myViewPager2.currentItem = 4
                    binding.myDrawerLayout.close()
                    true
                }
                R.id.item5 -> {
                    binding.myViewPager2.currentItem = 5
                    binding.myDrawerLayout.close()
                    true
                }
                else -> false
            }
        }
    }

    //si el menú está abierto y se pulsa atrás,
    //este se cierre, y si se pulsa con el menú cerrado, se cierre la aplicación
    override fun onBackPressed() {
        if(binding.myDrawerLayout.isOpen){
            binding.myDrawerLayout.close()
        }else super.onBackPressed()
    }

}