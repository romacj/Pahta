package com.example.pahta

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.pahta.databinding.FragmentGameBinding
import com.example.pahta.gameData.Result

class GameFragment : Fragment() {
    private val viewModel: ViewModel by activityViewModels()
    private lateinit var binding: FragmentGameBinding

//    val arrayList: ArrayList<com.example.pahta.gameData.Result> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.println(Log.WARN,"TEt","dsad");


        viewModel.gf = this;
//        arrayList.clear()
//
//        for (rez in viewModel.rezultati2.value!!)
//            arrayList.add(rez)
//
//        binding.gridviewGame.adapter = GameAdapter(requireActivity(),arrayList)


        binding.gridviewGame.adapter = GameAdapter(requireActivity(), viewModel.getGames())

        binding.gridviewGame.onFinishTemporaryDetach()
        binding.btnSettings.setOnClickListener {
            view.findNavController()
                .navigate(com.example.pahta.R.id.action_gameFragment_to_settingsFragment)

        }
    }

    fun Test()
    {

        Log.println(Log.WARN,"TEt","GETTTING GAMES Bind");
        binding.gridviewGame.adapter = GameAdapter(requireActivity(), viewModel.getGames())
    }
}