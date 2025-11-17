package tw.edu.pu.csim.tcyang.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sqrt

@Composable
fun GameScreen(message: String, gameViewModel: GameViewModel) {
    val imageBitmaps = listOf(
        ImageBitmap.imageResource(R.drawable.horse0),
        ImageBitmap.imageResource(R.drawable.horse1),
        ImageBitmap.imageResource(R.drawable.horse2),
        ImageBitmap.imageResource(R.drawable.horse3)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()

                            val dx = change.position.x - gameViewModel.ballX
                            val dy = change.position.y - gameViewModel.ballY
                            val distance = sqrt(dx * dx + dy * dy)

                            if (distance <= 100f) {
                                gameViewModel.updateBallPosition(
                                    gameViewModel.ballX + dragAmount.x,
                                    gameViewModel.ballY + dragAmount.y
                                )
                            }
                        }
                    )
                }
        ) {
            drawCircle(
                color = Color.Red,
                radius = 100f,
                center = Offset(gameViewModel.ballX, gameViewModel.ballY)
            )

            for (i in 0..2) {
                drawImage(
                    image = imageBitmaps[gameViewModel.horses[i].number],
                    dstOffset = IntOffset(
                        gameViewModel.horses[i].horseX,
                        gameViewModel.horses[i].horseY
                    ),
                    dstSize = IntSize(200, 200)
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Text(
                text = "賽馬遊戲(作者：王奕翔)。" + gameViewModel.screenWidthPx.toString() + "*" + gameViewModel.screenHeightPx.toString() + " 分數: ${gameViewModel.score}",
                fontSize = 16.sp,
                color = Color.Black
            )

            Button(
                onClick = {
                    if (gameViewModel.isRolling) {
                        gameViewModel.stopRolling()
                    } else {
                        gameViewModel.StartGame()
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = if (gameViewModel.isRolling) "停止滾動" else "開始滾動")
            }

            // 顯示獲勝訊息
            if (gameViewModel.winnerMessage.isNotEmpty()) {
                Text(
                    text = gameViewModel.winnerMessage,
                    fontSize = 32.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}