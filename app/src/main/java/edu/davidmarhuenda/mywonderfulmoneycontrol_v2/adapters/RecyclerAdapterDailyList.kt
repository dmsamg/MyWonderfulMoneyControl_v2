package edu.davidmarhuenda.mywonderfulmoneycontrol_v2.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.databinding.ItemDailyListBinding
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.databinding.MovementsItemsListBinding
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.model.Movements

class RecyclerAdapterDailyList : RecyclerView.Adapter<RecyclerAdapterDailyList.ViewHolder>() {

    var movementsData: MutableList<Movements> = ArrayList()
    lateinit var context: Context

    // Constructor de la clase. Se pasa la fuente de datos y el contexto
    // sobre el que se mostrará.
    fun RecyclerAdapterDailyList(contxt: Context, movementsList: MutableList<Movements>)  {
        this.movementsData = movementsList
        this.context = contxt
    }

    // Este método se encarga de pasar los objetos, uno a uno al ViewHolder
    // personalizado.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("RV", "onBindViewHolder")
        val item = movementsData.get(position)
        holder.bind(item)
    }

    // Es el encargado de devolver el ViewHolder ya configurado.
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemDailyListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root,
        )
    }

    // Devuelve el tamaño del array.
    override fun getItemCount(): Int {
        return movementsData.size
    }

    // Esta clase se encarga de rellenar cada una de las vistas que se inflarán
    // en el RecyclerView.
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // Se usa View Binding para localizar los elementos en la vista.
        private val binding = MovementsItemsListBinding.bind(view)

        //se asignan los datos a los elementos de la vista
        fun bind(mov: Movements) {
            binding.tvTitle.text = mov.title
            binding.tvDate.text = mov.date
            binding.tvType.text = mov.type
            binding.tvCategory.text = mov.category
            binding.tvAmount.text = mov.amount.toString()
            Glide.with(itemView)
                .load(mov.imgUrl)
                .into(binding.ivTickets)
        }
    }
}