package com.example.pahta

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.pahta.gameData.Game
import com.example.pahta.genreData.Genre
import com.example.pahta.genreData.Result
import com.google.gson.Gson

class ViewModel(application: Application): AndroidViewModel(application){
    private val _genresList : MutableLiveData<List<Result>> by lazy {
        MutableLiveData<List<Result>>()
    }
    val genresList: LiveData<List<Result>>
        get() = _genresList

    private val _gamesList : MutableLiveData<List<com.example.pahta.gameData.Result>> by lazy {
        MutableLiveData<List<com.example.pahta.gameData.Result>>()
    }
    val gamesList: LiveData<List<com.example.pahta.gameData.Result>>
        get() = _gamesList

    private val _favGenresList : MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }
    val favGenresList: LiveData<List<String>>
        get() = _favGenresList

    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val RAWG_URL = "https://api.rawg.io/api/"
        private const val API_KEY = "c2ac4fe89eb04b45b98d674b61825878"
    }

    fun CallOnChild() {
        Log.println(Log.WARN,"FLOW","GETTTING GAMES nullcheck");
        if(::gf.isInitialized)
            gf.Test();
    }

    lateinit var gf:GameFragment;

    fun getGenreData(){
        val context = getApplication<Application>().applicationContext
        val queue = Volley.newRequestQueue(context)
        val url = "${RAWG_URL}genres?key=$API_KEY"
        var gson = Gson()

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null, { response ->
                var genre = gson.fromJson(response.toString(), Genre::class.java)
                var genres = genre.results
                _genresList.value = genres
                requestSuccess()
            },
            {
                Toast.makeText(getApplication<Application>().applicationContext, "NEMA INTERNETA",Toast.LENGTH_SHORT).show()
                requestFailOrReset()
            })
        queue.add(jsonObjectRequest)
    }

    fun getGameData(){


        Log.println(Log.WARN,"FLOW","GETTTING GAMES START");
        val context = getApplication<Application>().applicationContext
        val queue = Volley.newRequestQueue(context)
        val url = "${RAWG_URL}games?genres="
        val url2 = "&key=$API_KEY"
        val sredina = _favGenresList.value?.joinToString()
        var gson = Gson()

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url+sredina+url2, null, { response ->
                var game = gson.fromJson(response.toString(), Game::class.java)
                var games = game.results
                _gamesList.value = games
                Log.println(Log.WARN,"FLOW","GETTTING GAMES RQ DONE");

                CallOnChild();

                requestSuccess()
            },
            {
                Toast.makeText(getApplication<Application>().applicationContext, "NEMA INTERNETA",Toast.LENGTH_SHORT).show()
                requestFailOrReset()
            })
        queue.add(jsonObjectRequest)


        Log.println(Log.WARN,"FLOW","GETTTING GAMES END");
    }


    fun saveSettings(lista: ArrayList<Result>):Boolean
    {
        sharedPreferences = getApplication<Application>().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE);
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        var listazanrova = listOf<String>()


        for (rez in lista)
            if (rez.flag)
                listazanrova += rez.id.toString()


        editor.putStringSet("ZANROVI",listazanrova.toSet())
        editor.apply()

        Toast.makeText(getApplication<Application>().applicationContext, "SPREMLJENO: " + listazanrova,Toast.LENGTH_SHORT).show()

        return !lista.isNullOrEmpty()
    }

    fun loadSettings(): Boolean{
//        requestFailOrReset()

        sharedPreferences = getApplication<Application>().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE);
        var listazanrova = listOf<String>()
        var token = sharedPreferences.getStringSet("ZANROVI", listazanrova.toSet())

        _favGenresList.value = token?.toList()

        return !_favGenresList.value.isNullOrEmpty()
    }

    fun clearSettings()
    {
        sharedPreferences = getApplication<Application>().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE);
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.clear()
        editor.apply()

        Toast.makeText(getApplication<Application>().applicationContext, "POBRISANO!",Toast.LENGTH_SHORT).show()
    }

    fun isNightMode(context: Context): Boolean {
        val nightModeFlags: Int =
            context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }

    fun getGames(): ArrayList<com.example.pahta.gameData.Result> {
        val arrayList: ArrayList<com.example.pahta.gameData.Result> = ArrayList()

        for (rez in gamesList.value!!)
            arrayList.add(rez)

        return arrayList
    }


    private val _requestStatus : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val requestStatus: LiveData<Boolean>
        get() = _requestStatus

    fun requestSuccess(){
        _requestStatus.value = true
    }

    fun requestFailOrReset(){
        _requestStatus.value = false
    }

}