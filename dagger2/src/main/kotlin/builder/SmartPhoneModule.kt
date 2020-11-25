package builder

import dagger.Module
import dagger.Provides

@Module
class SmartPhoneModule(private val password: String) {
    @Provides
    fun provideSmartPhone(): SmartPhone {
        return SmartPhone(password)
    }
}