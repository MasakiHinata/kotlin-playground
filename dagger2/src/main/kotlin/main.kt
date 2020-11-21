import constructor.DaggerCalculatorComponent
import provides.DaggerCarComponent
import binds.DaggerDatabaseComponent
import fieldinjection.FruitsApplication
import qualifier.DaggerPhoneComponent

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

    // Qualifier
    val phone = DaggerPhoneComponent.create().getPhone()
    println(phone.batteryLevel())

    // フィールドインジェクション
    val fruitsApp = FruitsApplication()
    println(fruitsApp.apple.name)
}