package provides

import dagger.Module
import dagger.Provides

/**
 * Provides
 * インターフェースなどに対してDIできるようになる
 */

@Module
class CarModule {
    @Provides
    fun provideEngine(): Engine {
        return HondaEngine()
    }

    @Provides
    fun provideCar(
        engine: Engine
    ): Car {
        return HondaCar(engine)
    }
}