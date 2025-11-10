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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sqrt

@Composable
fun GameScreen(message: String, gameViewModel: GameViewModel) {
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

                            // 檢查是否在拖動球
                            val dx = change.position.x - gameViewModel.ballX
                            val dy = change.position.y - gameViewModel.ballY
                            val distance = sqrt(dx * dx + dy * dy)

                            if (distance <= 100f) {
                                // 拖動球時更新位置
                                gameViewModel.updateBallPosition(
                                    gameViewModel.ballX + dragAmount.x,
                                    gameViewModel.ballY + dragAmount.y
                                )
                            }
                        }
                    )
                }
        ) {
            // 繪製圓形（使用 ViewModel 中的位置）
            drawCircle(
                color = Color.Red,
                radius = 100f,
                center = Offset(gameViewModel.ballX, gameViewModel.ballY)
            )
        }

        // 左上角的文字和按鈕（垂直排列）
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Text(
                text = message + gameViewModel.screenWidthPx.toString() + "*" + gameViewModel.screenHeightPx.toString() + " 王奕翔 分數: ${gameViewModel.score}",
                fontSize = 16.sp,
                color = Color.Black
            )

            Button(
                onClick = {
                    if (gameViewModel.isRolling) {
                        gameViewModel.stopRolling()
                    } else {
                        gameViewModel.startRolling()
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = if (gameViewModel.isRolling) "停止滾動" else "開始滾動")
            }
        }
    }
}