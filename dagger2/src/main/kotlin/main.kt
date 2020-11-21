import calculator.DaggerCalculatorComponent
import car.DaggerCarComponent
import database.DaggerDatabaseComponent

fun main() {
    // コンストラクタインジェクション
    val calculator = DaggerCalculatorComponent.create().getCalculator()
    calculator.add(10, 15)

    // Binds
    val database = DaggerDatabaseComponent.create().getDatabase()
    println(database.loadMessage())

    // Provider
    val car = DaggerCarComponent.create().getCar()
    car.drive()
}