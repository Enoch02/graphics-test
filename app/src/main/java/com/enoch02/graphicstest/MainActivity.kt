package com.enoch02.graphicstest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.enoch02.graphicstest.ui.theme.GraphicsTestTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraphicsTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
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
        Color.Red, Color.Blue, Color.Green, Color.Black, Color.Cyan, Color.Magenta, Color.Yellow
    )

    Column(modifier = Modifier.fillMaxSize(), content = {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .weight(0.5f), onDraw = {
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
        })

        Column(modifier = Modifier.weight(0.5f)) {

        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCanvasWithViewModel(viewModel: MainViewModel = viewModel()) {
    val pixels = viewModel.pixels
    var initAgain by rememberSaveable {
        mutableStateOf(true)
    }

    Column(modifier = Modifier.fillMaxSize(), content = {
        Canvas(modifier = Modifier
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
            })

        Column(modifier = Modifier
            .weight(0.5f)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                var xCoord by rememberSaveable {
                    mutableStateOf("0")
                }
                var yCoord by rememberSaveable {
                    mutableStateOf("0")
                }
                val context = LocalContext.current

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    content = {
                        OutlinedTextField(
                            value = xCoord,
                            onValueChange = { xCoord = it },
                            label = {
                                Text(text = "X")
                            },
                            modifier = Modifier.weight(0.5f)
                        )

                        OutlinedTextField(
                            value = yCoord,
                            onValueChange = { yCoord = it },
                            label = {
                                Text(text = "Y")
                            },
                            modifier = Modifier.weight(0.5f)
                        )
                    }
                )

                Button(
                    onClick = {
                        try {
                            val x = xCoord.toInt()
                            val y = yCoord.toInt()

                            if (x <= viewModel.pixels.value.size && y <= viewModel.pixels.value.first().size) {
                                viewModel.changePixel(x, y, Color(0xFFEFB8C8))
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }
                    },
                    content = {
                        Text(text = "Change Coord")
                    }
                )

                Button(
                    onClick = { viewModel.shuffleColors() },
                    content = {
                        Text(text = "Shuffle Colors")
                    }
                )

                Button(
                    onClick = {
                        val index = Random.nextInt(viewModel.pixels.value.size)

                        viewModel.changeColumn(index, Color.White)
                    },
                    content = {
                        Text(text = "Change Random Column")
                    }
                )

                Button(
                    onClick = {
                        val index = Random.nextInt(viewModel.pixels.value.size)

                        viewModel.changeRow(index, Color.DarkGray)
                    },
                    content = {
                        Text(text = "Change Random Row")
                    }
                )
            }
        )
    }
    )
}