package com.example.pahta

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import coil.load
import com.example.pahta.gameData.Result

class GameAdapter (private val context: Activity, private  val arrayList: ArrayList<Result>) : ArrayAdapter<Result>(context,R.layout.game_item, arrayList){

    @SuppressLint("ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var inflater: LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.game_item, null)

        val naziv_igra : TextView = view.findViewById(R.id.icons_names)
        val slika :ImageView = view.findViewById(R.id.icons)
        val kartica : CardView = view.findViewById(R.id.cardviewGame)

        naziv_igra.text = arrayList[position].name

//        slika.setImageBitmap(ucitajSlike(arrayList[position].background_image))

        bindImage(slika,arrayList[position].background_image)

        kartica.setOnClickListener {

//            val action = ExamInformationFragmentDirections.actionExamInformationFragmentToExamScoreFragment(exam)
//            val action = G

            val bundle = bundleOf("ID" to position)

            view.findNavController().navigate(com.example.pahta.R.id.action_gameFragment_to_gameInfoFragment,bundle)
        }

        return view
    }

//    fun ucitajSlike(url: String): Bitmap? {
//        val imageURL = url
//        var image: Bitmap? = null
//        try {
//            val `in` = java.net.URL(imageURL).openStream()
//            image = BitmapFactory.decodeStream(`in`)
//        }
//        catch (e: Exception) {
//            Log.e("Error Message", e.message.toString())
//            e.printStackTrace()
//        }
//        return image
//    }

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