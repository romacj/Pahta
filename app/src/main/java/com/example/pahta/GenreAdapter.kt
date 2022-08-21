package com.example.pahta

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import coil.load
import com.example.pahta.genreData.Result


class GenreAdapter(private val context: Activity, private  val arrayList: ArrayList<Result>) : ArrayAdapter<Result>(context,R.layout.genre_item, arrayList){

    @SuppressLint("ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var inflater: LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.genre_item, null)
        val pic : ImageView = view.findViewById(R.id.imgBtn)

        val zanr : CheckBox = view.findViewById(R.id.cb_genre)

        bindImage(pic,arrayList[position].image_background)

        zanr.text = arrayList[position].name

        zanr.isChecked = arrayList[position].flag

        zanr.setOnCheckedChangeListener { buttonView, isChecked ->
            arrayList[position].flag = isChecked
        }

        return view
    }

    fun ucitajSlike(url: String): Bitmap? {
        val imageURL = url
        var image: Bitmap? = null
        try {
            val `in` = java.net.URL(imageURL).openStream()
            image = BitmapFactory.decodeStream(`in`)
        }
        catch (e: Exception) {
            Log.e("Error Message", e.message.toString())
            e.printStackTrace()
        }
        return image
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