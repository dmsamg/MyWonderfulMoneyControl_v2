package edu.davidmarhuenda.mywonderfulmoneycontrol_v2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.adapters.RecyclerAdapterDailyList
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.databinding.FragmentDailyListBinding
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.db.DBOpenHelper


class DailyListFragment : Fragment() {

    lateinit var binding: FragmentDailyListBinding
    val myAdapter: RecyclerAdapterDailyList = RecyclerAdapterDailyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //se infla el layout para este fragment
        binding = FragmentDailyListBinding.inflate(inflater, container, false)
        val view = binding.root

        myAdapter.RecyclerAdapterDailyList(requireContext(),
            DBOpenHelper(requireContext(), null).getDBRegistros(requireContext()))
        binding.rvDailyList.setHasFixedSize(true)
        binding.rvDailyList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDailyList.adapter = myAdapter

        return view
    }
}