package qualifier

import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

/**
 * Qualifier
 * 同じ型に複数のバインディングを提供したいときは
 * アノテーションを付けることによって区別をする
 */
@Module
object PhoneModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BatteryL

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BatteryS

    @Provides
    @BatteryL
    fun provideLargeBattery(): Battery {
        return LargeBattery()
    }

    @Provides
    @BatteryS
    fun provideSmallBattery(): Battery {
        return SmallBattery()
    }

    @Provides
    fun providePhone(
        // アノテーションにより指定
        @BatteryL battery: Battery
    ): Phone {
        return Phone(battery)
    }
}