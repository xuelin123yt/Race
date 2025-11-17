package tw.edu.pu.csim.tcyang.race

class Horse(n: Int) {
    var horseX = 0
    var horseY = 0
    var horseNumber = n

    var number = 0

    fun HorseRun() {
        number++
        if (number > 3) {
            number = 0
        }
        horseX += (10..30).random()
    }

    fun setInitialY(screenHeight: Float) {
        horseY = (screenHeight * 0.25f + 220 * horseNumber).toInt()
    }
}