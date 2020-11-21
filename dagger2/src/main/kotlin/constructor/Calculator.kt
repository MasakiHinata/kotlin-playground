package constructor

import javax.inject.Inject

/**
 * コンストラクタインジェクション
 */
class Calculator @Inject constructor(
    private val screen: Screen
) {
    fun add(a: Int, b: Int): Int {
        screen.show("$a + $b = ${a+b}")
        return a + b
    }
}