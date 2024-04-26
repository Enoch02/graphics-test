package com.enoch02.graphicstest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enoch02.graphicstest.ui.theme.GraphicsTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraphicsTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyCanvas()
                }
            }
        }
    }
}

@Composable
fun MyCanvas() {
    val colors = listOf(
        Color.Red,
        Color.Blue,
        Color.Green,
        Color.Black,
        Color.Cyan,
        Color.Magenta,
        Color.Yellow
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        content = {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f),
                onDraw = {
                    val canvasHeight = size.height
                    val canvasWidth = size.width
                    val pixelWidth = 10f
                    val pixelHeight = 10f

                    for (i in 0..(canvasWidth / pixelWidth).toInt()) {
                        for (j in 0..(canvasHeight / pixelHeight).toInt()) {
                            drawRect(
                                color = colors.random(),
                                size = Size(pixelWidth, pixelHeight),
                                topLeft = Offset(i * pixelWidth, j * pixelHeight)
                            )
                        }
                    }
                }
            )

            Column(modifier = Modifier.weight(0.5f)) {

            }
        }
    )
}

