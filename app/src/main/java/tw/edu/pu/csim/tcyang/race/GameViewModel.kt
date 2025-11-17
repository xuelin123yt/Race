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

    // 改用馬的列表（產生 3 匹馬）
    val horses = mutableListOf<Horse>()

    // 設定螢幕寬度與高度
    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h

        // 設定紅圈的圓心（避開按鈕區域，放在螢幕下方中間）
        ballX = w / 2f
        ballY = h - 100f

        // 產生 3 匹馬的物件
        for (i in 0..2) {
            horses.add(Horse(i))
        }
    }

    // 更新球的位置（拖移時使用）
    fun updateBallPosition(x: Float, y: Float) {
        ballX = x.coerceIn(100f, screenWidthPx - 100f)
        ballY = y.coerceIn(100f, screenHeightPx - 100f)
    }

    // 開始遊戲
    fun StartGame() {
        if (isRolling) return

        isRolling = true

        viewModelScope.launch {
            while (isRolling) { // 無限循環，每秒增加一次
                delay(16) // 約 60 FPS

                // 球的移動
                ballX += 3f
                if (ballX >= screenWidthPx - 100f) {
                    ballX = 100f
                    score++
                }

                // 讓每匹馬都能奔跑
                for (i in 0..2) {
                    horses[i].HorseRun()
                    if (horses[i].horseX >= screenWidthPx - 200) {
                        horses[i].horseX = 0
                    }
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