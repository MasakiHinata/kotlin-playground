import calculator.DaggerCalculatorComponent

fun main() {
    // コンストラクタインジェクション
    val calculator = DaggerCalculatorComponent.create().getCalculator()
    println(calculator.add(10, 15))
}