package com.example.pahta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.pahta.databinding.FragmentGameInfoBinding

class GameInfoFragment : Fragment() {

    private val viewModel: ViewModel by activityViewModels()
    private lateinit var binding: FragmentGameInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameInfoBinding.inflate(inflater,container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("ID")

        val selected = viewModel.gamesList.value?.get(id!!)

        binding.gameInfoReleased.text = selected?.released
        binding.gameInfoName.text = selected?.name

        var temp = listOf<String>()
        for (a in selected?.genres!!)
            temp += a.name
        binding.gameInfoGenres.text = temp.joinToString()


        bindImage(binding.gameInfoIcon, selected?.background_image)

        binding.gameInfoRating.text = selected?.rating.toString()
        binding.gameInfoRatingbar.rating = selected?.rating!!.toFloat()

        binding.gameInfoMetacritic.text = selected.metacritic.toString()
        binding.gameInfoProgressbar.progress = selected.metacritic


        temp = listOf<String>()
        for (a in selected?.stores!!)
            temp += a.store.name
        binding.gameInfoStores.text = temp.joinToString()

        temp = listOf<String>()
        for (a in selected?.platforms!!)
            temp += a.platform.name
        binding.gameInfoPlatforms.text = temp.joinToString()

    }

    fun bindImage(imgView: ImageView, imgUrl: String?) {
        imgUrl?.let {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            imgView.load(imgUri)
            {
//                placeholder(R.drawable.loading_animation)
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_launcher_foreground)
            }
        }
    }

}