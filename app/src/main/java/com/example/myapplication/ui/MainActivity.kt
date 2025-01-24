package com.example.myapplication.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.data.model.SeaCreature
import com.example.myapplication.data.model.SeaCreatureManager
import com.example.myapplication.ext.flip
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var seaCreatureManager: SeaCreatureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val layout = findViewById<ConstraintLayout>(R.id.main)
        val button = findViewById<Button>(R.id.btn)

        seaCreatureManager = SeaCreatureManager(layout)

        button.setOnClickListener {
            seaCreatureManager.addSeaCreature(SeaCreature(0, 1F, Pair(2, 2), false))
        }
    }

    private fun addRandomImageView(layout: ConstraintLayout) {
        val imageView = ImageView(this).apply {
            setImageResource(R.drawable.nemo)
            setBackgroundColor(Color.BLUE)
            layoutParams = ConstraintLayout.LayoutParams(50, 50)
        }

        val randomX = Random.nextInt(0, layout.width - 200)
        val randomY = Random.nextInt(0, layout.height - 200)

        imageView.x = randomX.toFloat()
        imageView.y = randomY.toFloat()

        layout.addView(imageView)

        val velocityX = Random.nextInt(-10, 10)
        val velocityY = Random.nextInt(-10, 10)

        if (velocityX < 0f) imageView.flip()

        val f = SeaCreature(0, 1F, Pair(velocityX, velocityY), false)
        f.startSwim(imageView)
    }
}