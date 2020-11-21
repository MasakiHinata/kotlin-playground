package car

interface Engine {
    val speed: Int
}

data class HondaEngine(
    override val speed: Int = 100
) : Engine