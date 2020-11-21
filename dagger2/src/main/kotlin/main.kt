import calculator.DaggerCalculatorComponent
import car.DaggerCarComponent

fun main() {
    // コンストラクタインジェクション
    val calculator = DaggerCalculatorComponent.create().getCalculator()
    println(calculator.add(10, 15))

    // Provider
    val car = DaggerCarComponent.create().getCar()
    car.drive()
}