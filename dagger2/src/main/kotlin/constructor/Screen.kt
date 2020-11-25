package constructor

import javax.inject.Inject

class Screen @Inject constructor() {
    fun show(words: String) {
        println(words)
    }
}