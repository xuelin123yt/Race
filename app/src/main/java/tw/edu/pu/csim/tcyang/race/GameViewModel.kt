package tw.edu.pu.csim.tcyang.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sqrt

class GameViewModel: ViewModel() {
    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set

    var ballX by mutableStateOf(100f)
        private set

    var ballY by mutableStateOf(100f)
        private set

    var isRolling by mutableStateOf(false)
        private set

    var score by mutableStateOf(0)
        private set

    val horses = mutableListOf<Horse>()

    // 新增：獲勝訊息
    var winnerMessage by mutableStateOf("")
        private set

    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h

        ballX = w / 2f
        ballY = h - 100f

        horses.clear()
        for (i in 0..2) {
            val horse = Horse(i)
            horse.setInitialY(h)
            horses.add(horse)
        }
    }

    fun updateBallPosition(x: Float, y: Float) {
        ballX = x.coerceIn(100f, screenWidthPx - 100f)
        ballY = y.coerceIn(100f, screenHeightPx - 100f)
    }

    fun StartGame() {
        if (isRolling) return

        isRolling = true
        winnerMessage = ""  // 清空獲勝訊息

        viewModelScope.launch {
            while (isRolling) {
                delay(16)

                ballX += 3f
                if (ballX >= screenWidthPx - 100f) {
                    ballX = 100f
                    score++
                }

                for (i in 0..2) {
                    horses[i].HorseRun()

                    // 檢查是否抵達終點
                    if (horses[i].horseX >= screenWidthPx - 200) {
                        // 有馬獲勝！
                        winnerMessage = "第${i + 1}馬獲勝"

                        // 三匹馬回到起點
                        for (j in 0..2) {
                            horses[j].horseX = 0
                        }

                        // 停止遊戲
                        isRolling = false
                        break
                    }
                }
            }
        }
    }

    fun stopRolling() {
        isRolling = false
    }

    fun resetScore() {
        score = 0
    }
}