package com.example.balloongame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
    }

    fun playTheGame(view: View)
    {
        val intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    fun goToSettings(view:View)
    {
        val intent= Intent(this,SettingsScreen::class.java)
        startActivity(intent)
    }

    fun goToTutorialScreen(view: View)
    {
        val intent = Intent(this,TutorialScreen::class.java)
        startActivity(intent)
    }
}