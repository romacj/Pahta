package com.example.pahta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var backPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }

    override fun onBackPressed() {

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
            navHost?.let { navFragment ->

                navFragment.childFragmentManager.primaryNavigationFragment?.let {
                    fragment ->
                        if (fragment is GameFragment){

                            if(backPressedTime + 2000 > System.currentTimeMillis()){
                                finish()
                                System.out.close()
                            }
                            else{
                                Toast.makeText(applicationContext, "Press back again to close app", Toast.LENGTH_SHORT).show()
                            }
                            backPressedTime = System.currentTimeMillis()
                        }
                        else{
                            super.onBackPressed()
                        }
                }
            }


    }

}