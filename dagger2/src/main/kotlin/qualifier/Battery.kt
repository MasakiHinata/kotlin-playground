package qualifier

interface Battery {
    val batteryLevel: Int
}

data class LargeBattery(
    override val batteryLevel: Int = 100
) : Battery

data class SmallBattery(
    override val batteryLevel: Int = 50
) : Battery