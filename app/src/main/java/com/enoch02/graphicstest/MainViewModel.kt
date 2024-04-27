package com.enoch02.graphicstest

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {
    private val colors = listOf(
        Color.Red,
        Color.Blue,
        Color.Green,
        Color.Black,
        Color.Cyan,
        Color.Magenta,
        Color.Yellow
    )
    val pixels = mutableStateOf<Array<Array<Pixel>>>(emptyArray())

    fun initPixels(canvasWidth: Float, canvasHeight: Float, pixelWidth: Float, pixelHeight: Float) {
        val rows = (canvasWidth / pixelWidth).toInt()
        val cols = (canvasHeight / pixelHeight).toInt()

        val temp = Array(rows) { _ ->
            Array(cols) { _ ->
                Pixel(color = colors.random())
            }
        }
        pixels.value = temp
    }

    fun shuffleColors() {
        val temp = pixels.value.clone()

        temp.forEach { row ->
            row.forEach { pixel ->
                pixel.color = colors.random()
            }
        }

        pixels.value = temp
    }
}

data class Pixel(
    var color: Color,
    /*TODO: move somewhere else*/
    val width: Float = 10f,
    val height: Float = 10f
)