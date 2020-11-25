package binds

import javax.inject.Inject

class AppleDatabase @Inject constructor(): DatabaseInterface {
    override fun loadMessage(): String {
        return "Apple Database"
    }
}