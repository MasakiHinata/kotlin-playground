package database

import dagger.Component

@Component(
    modules = [DatabaseModule::class]
)
interface DatabaseComponent {
    fun getDatabase(): DatabaseInterface
}