package tw.edu.pu.csim.tcyang.race

class Horse(n: Int) {
    var horseX = 0
    var horseY = 300 + 220 * n  // 從 300 開始，讓馬在按鈕下方

    var number = 0

    fun HorseRun() {
        // 賽馬圖片處理
        number++
        if (number > 3) {
            number = 0
        }
        horseX += (10..30).random()
    }
}