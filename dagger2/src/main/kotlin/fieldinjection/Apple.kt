package fieldinjection

import javax.inject.Inject

class Apple @Inject constructor(){
    val name: String = "Apple"
}