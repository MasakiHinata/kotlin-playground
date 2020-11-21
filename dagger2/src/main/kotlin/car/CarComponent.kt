package car

import dagger.Component

@Component(
    modules = [CarModule::class]
)
interface CarComponent {
    fun getCar(): Car
}