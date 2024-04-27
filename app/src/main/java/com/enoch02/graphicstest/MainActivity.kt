package com.enoch02.graphicstest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
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
                    //MyCanvas()
                    MyCanvasWithViewModel()
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

@Composable
fun MyCanvasWithViewModel(viewModel: MainViewModel = viewModel()) {
    val pixels = viewModel.pixels
    var initAgain by rememberSaveable {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        content = {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f)
                    .align(Alignment.CenterHorizontally),
                onDraw = {
                    val canvasHeight = size.height
                    val canvasWidth = size.width

                    if (initAgain) {
                        //TODO: use dependency injection to get the canvas width and height to the viewModel
                        viewModel.initPixels(
                            canvasWidth = canvasWidth,
                            canvasHeight = canvasHeight,
                            pixelWidth = 10f,
                            pixelHeight = 10f
                        )

                        initAgain = false
                    }

                    pixels.value.forEachIndexed { i, pixelList ->
                        pixelList.forEachIndexed { j, pixel ->
                            drawRect(
                                color = pixel.color,
                                size = Size(pixel.width, pixel.height),
                                topLeft = Offset(i * pixel.width, j * pixel.height)
                            )
                        }
                    }
                }
            )

            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Button(
                        onClick = { viewModel.shuffleColors() },
                        content = {
                            Text(text = "Shuffle Colors")
                        }
                    )
                }
            )
        }
    )
}