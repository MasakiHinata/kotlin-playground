package calculator

import javax.inject.Inject

/**
 * コンストラクタインジェクション
 */
class Calculator @Inject constructor(
    private val keyboard: Keyboard
) {
    fun add(a: Int, b: Int): Int {
        return a + b
    }
}