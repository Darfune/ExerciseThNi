package com.example.threenitasapp.ui.screens.list.components


import android.widget.ProgressBar
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.threenitasapp.ui.theme.forest_green_2
import com.example.threenitasapp.ui.theme.tufts_blue

@Preview
@Composable
fun BookItem() {

    Box(
        modifier = Modifier
            .height(170.dp)
            .width(130.dp),
        contentAlignment = Alignment.BottomEnd
    ) {

        AsyncImage(
            model = "http://dummyimage.com/250x250.png/5fa2dd/ffffff",
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp),
            contentAlignment = Alignment.BottomEnd,
        ) {
            Canvas(modifier = Modifier.fillMaxHeight().width(40.dp), onDraw = {
                val rectSize = Size (100f,100f)

                val rect = Rect(Offset.Zero, rectSize)
                val trianglePath = Path().apply {
                    moveTo(rect.bottomRight.x, rect.bottomCenter.y)
                    lineTo(rect.topRight.x , rect.topCenter.y)
                    lineTo(rect.topLeft.x, rect.bottomRight.y)
                    close()
                }

                drawIntoCanvas { canvas ->
                        canvas.drawOutline(
                            outline = Outline.Generic(trianglePath),
                            paint = Paint().apply {
                                color = forest_green_2
                            }
                        )

                }


            })
            LinearProgressIndicator(
                progress = 4f,
                modifier = Modifier.fillMaxWidth(),
                color = tufts_blue
            )

        }
    }

}