package com.example.myapplication.data.model

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.ext.flip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SeaCreature(
    val id: Long = 0L,
    val size: Float = 50F,
    var velocity: Pair<Int, Int> = Pair(0, 0),
    val isAggressive: Boolean = false
) {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    fun swim(imageView: ImageView) {
        val (parentWidth, parentHeight) = (imageView.parent as ConstraintLayout).width to (imageView.parent as ConstraintLayout).height
        var (newX, newY) = imageView.x + velocity.first to imageView.y + velocity.second

        if (newX <= 0 || newX + size >= parentWidth) {
            velocity = Pair(-velocity.first, velocity.second)
            imageView.flip()
            newX += velocity.first
        }

        if (newY <= 0 || newY + size >= parentHeight) {
            velocity = Pair(velocity.first, -velocity.second)
            newY += velocity.second
        }

        imageView.x = newX
        imageView.y = newY
    }

    fun startSwim(imageView: ImageView) {
        scope.launch {
            while (scope.isActive) {
                delay(16)
                swim(imageView)
            }
        }
    }
}