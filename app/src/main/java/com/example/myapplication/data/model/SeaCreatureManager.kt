package com.example.myapplication.data.model

import android.graphics.Color
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class SeaCreatureManager(private val layout: ConstraintLayout) {
    private val seaCreatureList = mutableListOf<SeaCreature>()

    private val tankJob = SupervisorJob()
    private val seaCreatureJob = CoroutineScope(Dispatchers.Main + tankJob)
    private val fishJobs = mutableMapOf<Long, Job>()

    fun addSeaCreature(seaCreature: SeaCreature) {
        val fishJob = Job(tankJob)
        fishJobs[seaCreature.id] = fishJob

        val imageView = ImageView(layout.context).apply {
            setBackgroundColor(Color.BLUE)
            setImageResource(R.drawable.nemo)
            layoutParams = ConstraintLayout.LayoutParams(50, 50)
        }

        val randomX = Random.nextInt(0, layout.width - 200)
        val randomY = Random.nextInt(0, layout.height - 200)

        imageView.x = randomX.toFloat()
        imageView.y = randomY.toFloat()

        layout.addView(imageView)

        seaCreatureJob.launch(fishJob) {
            while (isActive) {
                delay(16)
                seaCreature.swim(imageView)
            }
        }

        seaCreatureList.add(seaCreature)
    }

    fun removeSeaCreature(seaCreatureId: Long) {
        fishJobs[seaCreatureId]?.cancel()
        fishJobs.remove(seaCreatureId)
    }
}