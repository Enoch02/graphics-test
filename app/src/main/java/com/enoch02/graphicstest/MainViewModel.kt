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
    val currentPosition = mutableStateOf(Position(0, 0))

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

    fun changePixel(x: Int, y: Int, color: Color) {
        val temp = pixels.value.clone()

        temp[x][y].color = color

        pixels.value = temp
    }

    fun changeColumn(index: Int, color: Color) {
        val temp = pixels.value.clone()

        temp[index].forEach { pixel ->
            pixel.color = color
        }

        pixels.value = temp
    }

    fun changeRow(index: Int, color: Color) {
        val temp = pixels.value.clone()

        temp.forEach { column ->
            column[index].color = color
        }

        pixels.value = temp
    }

    fun clearCanvasForMoveDemo() {
        val temp = pixels.value

        temp.forEach { row ->
            row.forEach { pixel ->
                pixel.color = Color.White
            }
        }

        // pixel to be moved
        temp[0][0].color = Color.Black

        pixels.value = temp
    }

    fun clearRowByRow() {

    }

    fun movePixel(direction: Direction) {
        val temp = pixels.value.clone()

        if (currentPosition.value.x < pixels.value.size && currentPosition.value.y < pixels.value.first().size) {
            when (direction) {
                Direction.LEFT -> {

                }

                Direction.RIGHT -> {
                    // increment x to move the pixel to the right
                    val currentPositionPixel =
                        temp[currentPosition.value.x][currentPosition.value.y]
                    currentPosition.value.x += 1
                    val nextPositionPixel =
                        temp[currentPosition.value.x + 1][currentPosition.value.y]

                    nextPositionPixel.color = currentPositionPixel.color
                    currentPositionPixel.color = Color.White

                    /*temp[currentPosition.first + 1][currentPosition.second].color = currentPositionPixel.color
                    temp[currentPosition.first][currentPosition.second].color = Color.White*/
                }

                Direction.UP -> {

                }

                Direction.DOWN -> {

                }
            }

            pixels.value = temp
        }
    }
}

data class Pixel(
    var color: Color,
    /*TODO: move somewhere else*/
    val width: Float = 10f,
    val height: Float = 10f
)

data class Position(
    var x: Int,
    var y: Int
)

enum class Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN
}