package com.example.pahta

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.pahta.databinding.FragmentWelcomeBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase


class WelcomeFragment : Fragment() {
    private val viewModel: ViewModel by activityViewModels()
    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.loadSettings())
        {


            Log.println(Log.WARN,"FLOW","GETTTING GAMES in");
            viewModel.getGameData()


            Log.println(Log.WARN,"FLOW","GETTTING GAMES out");
            viewModel.gamesList.observe(viewLifecycleOwner, Observer {

                view.findNavController().navigate(com.example.pahta.R.id.action_welcomeFragment_to_gameFragment)
            })
        }
        else{
            viewModel.genresList.observe(viewLifecycleOwner, Observer {
                view.findNavController().navigate(com.example.pahta.R.id.action_welcomeFragment_to_genreFragment)
            })

            viewModel.getGenreData()

        }

        viewModel.requestStatus.observe(viewLifecycleOwner, Observer {
            if (viewModel.requestStatus.value!!){
                binding.progressBar.visibility = View.VISIBLE
                binding.btnRetry.visibility = View.INVISIBLE
            }
            else{
                binding.progressBar.visibility = View.VISIBLE
                binding.btnRetry.visibility = View.INVISIBLE
            }

        })
    }
}