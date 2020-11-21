package phone

import dagger.Component

@Component(
    modules = [PhoneModule::class]
)
interface PhoneComponent {
    fun getPhone(): Phone
}