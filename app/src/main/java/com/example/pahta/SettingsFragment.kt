package com.example.pahta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.pahta.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private val viewModel: ViewModel by activityViewModels()
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(viewModel.isNightMode(requireContext())) {
            binding.switch1.isChecked = true
            binding.switch1.text = "Dark"

        }
        else {
            binding.switch1.isChecked = false
            binding.switch1.text = "Light"
        }

        binding.switch1.setOnClickListener {
            if(viewModel.isNightMode(requireContext())) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switch1.text = "Light"

            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switch1.text = "Dark"
            }

        }

        binding.btnEditFavorites.setOnClickListener {
            viewModel.genresList.observe(viewLifecycleOwner, Observer {
                view.findNavController().navigate(com.example.pahta.R.id.action_settingsFragment_to_genreFragment)
            })
            viewModel.getGenreData()
        }

        binding.btnAbout.setOnClickListener {
            Toast.makeText(requireContext(), "PAHTA 2022.",Toast.LENGTH_SHORT).show()

        }

        binding.btnClear.setOnClickListener {
            viewModel.clearSettings()
        }

    }

}