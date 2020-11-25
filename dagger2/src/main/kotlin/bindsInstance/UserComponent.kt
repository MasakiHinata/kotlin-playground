package bindsInstance

import dagger.BindsInstance
import dagger.Component
import javax.inject.Named

/**
 * Binds Instance
 */

@Component(
    modules = [UserModule::class]
)
interface UserComponent {
    fun getUser(): User

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun name(@Named("name") name: String): Builder

        @BindsInstance
        fun hobby(@Named("hobby") hobby: String): Builder

        @BindsInstance
        fun age(age: Int): Builder

        fun build(): UserComponent
    }
}