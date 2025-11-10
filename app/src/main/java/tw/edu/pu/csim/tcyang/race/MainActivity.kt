package tw.edu.pu.csim.tcyang.race

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.window.layout.WindowMetricsCalculator
import tw.edu.pu.csim.tcyang.race.ui.theme.RaceTheme

class MainActivity : ComponentActivity() {

    // 實例化 GameViewModel
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 強迫橫式螢幕
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // 隱藏狀態列與下方導覽列
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())

        // 確保內容延伸到邊緣
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 步驟 1: 獲取 WindowMetricsCalculator 實例
        val windowMetricsCalculator = WindowMetricsCalculator.getOrCreate()

        // 步驟 2: 計算當前視窗的 WindowMetrics
        val currentWindowMetrics = windowMetricsCalculator.computeCurrentWindowMetrics(this)

        // 步驟 3: 從 bounds 獲取像素尺寸
        val bounds = currentWindowMetrics.bounds
        val screenWidthPx = bounds.width().toFloat()
        val screenHeightPx = bounds.height().toFloat()

        // 設置螢幕尺寸到 ViewModel
        gameViewModel.SetGameSize(screenWidthPx, screenHeightPx)

        // 顯示畫面
        setContent {
            RaceTheme {
                GameScreen(message="橫式螢幕，隱藏狀態列.", gameViewModel)
            }
        }
    }
}
