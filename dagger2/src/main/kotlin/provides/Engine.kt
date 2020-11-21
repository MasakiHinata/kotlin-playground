package provides

interface Engine {
    val speed: Int
}

data class HondaEngine(
    override val speed: Int = 100
) : Engine