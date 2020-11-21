package builder

import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [SmartPhoneModule::class]
)
interface SmartPhoneComponent {
    fun getSmartPhone(): SmartPhone
}