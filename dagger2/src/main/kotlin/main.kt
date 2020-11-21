import constructor.DaggerCalculatorComponent
import provides.DaggerCarComponent
import binds.DaggerDatabaseComponent
import bindsInstance.DaggerUserComponent
import builder.DaggerSmartPhoneComponent
import builder.SmartPhoneModule
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

    // builder
    val spComponent = DaggerSmartPhoneComponent
        .builder()
        .smartPhoneModule(SmartPhoneModule("password"))
        .build()
    val smartPhone = spComponent.getSmartPhone()
    println(smartPhone.password)

    // Binds Instance
    val userComponent = DaggerUserComponent
        .builder()
        .name("Alice")
        .hobby("reading")
        .age(18)
        .build()
    val user = userComponent.getUser()
    println(user.toString())
}