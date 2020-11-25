package binds

import dagger.Component

@Component(
    modules = [DatabaseModule::class]
)
interface DatabaseComponent {
    fun getDatabase(): DatabaseInterface
}