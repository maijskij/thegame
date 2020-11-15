package com.example.balloongame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.score).setText("Score: $score")

        findViewById<BalloonField>(R.id.balloonField).setBaloonPopped {
            score = score + 1

            findViewById<TextView>(R.id.score).setText("Score: $score")
        }


    }
}