package tw.edu.pu.csim.tcyang.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {
    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set

    // 球的位置
    var ballX by mutableStateOf(100f)
        private set

    var ballY by mutableStateOf(100f)
        private set

    // 是否正在滾動
    var isRolling by mutableStateOf(false)
        private set

    // 分數
    var score by mutableStateOf(0)
        private set

    // 設定螢幕寬度與高度
    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h
        // 初始化球的位置在左下角
        ballX = 100f
        ballY = h - 100f
    }

    // 更新球的位置（拖移時使用）
    fun updateBallPosition(x: Float, y: Float) {
        ballX = x.coerceIn(100f, screenWidthPx - 100f)
        ballY = y.coerceIn(100f, screenHeightPx - 100f)
    }

    // 開始滾動
    fun startRolling() {
        if (isRolling) return

        isRolling = true

        viewModelScope.launch {
            while (isRolling) {
                delay(16) // 約 60 FPS
                ballX += 3f // 每次移動 3 像素

                // 碰到右邊界就從左邊重新開始（保持相同的 Y 座標）
                if (ballX >= screenWidthPx - 100f) {
                    ballX = 100f
                    score++ // 分數加 1
                }
            }
        }
    }

    // 停止滾動
    fun stopRolling() {
        isRolling = false
    }

    // 重置分數
    fun resetScore() {
        score = 0
    }
}