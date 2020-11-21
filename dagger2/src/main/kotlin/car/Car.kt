package car

interface Car {
    fun drive()
}

class HondaCar(
    private val engine: Engine
) : Car {
    override fun drive() {
        println("driving at ${engine.speed}km/h")
    }
}