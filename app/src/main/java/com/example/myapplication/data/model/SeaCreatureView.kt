package com.example.myapplication.data.model

import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.ext.flip

class SeaCreatureView(
    private val seaCreature: SeaCreature,
    private val imageView: ImageView
) {
    fun updateView() {
        Log.d("SonLN", "updateView: ")
    }

    fun flipIfNeed() {
        if (seaCreature.velocity.first < 0) imageView.flip()
    }
}