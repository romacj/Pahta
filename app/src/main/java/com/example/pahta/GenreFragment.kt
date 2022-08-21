package com.example.pahta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.pahta.databinding.FragmentGenreBinding
import com.example.pahta.genreData.Result

class GenreFragment : Fragment() {
    private val viewModel: ViewModel by activityViewModels()
    private lateinit var binding: FragmentGenreBinding

    val arrayList: ArrayList<Result> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arrayList.clear()

        binding = FragmentGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (rez in viewModel.genresList.value!!)
        {
            if (viewModel.favGenresList.value!!.contains(rez.id.toString()))
                rez.flag = true

            arrayList.add(rez)
        }

        binding.listview.adapter = GenreAdapter(requireActivity(),arrayList)

        binding.btnSpremi.setOnClickListener {
            if(viewModel.saveSettings(arrayList))
                view.findNavController().navigate(com.example.pahta.R.id.action_genreFragment_to_welcomeFragment)
        }
    }

}