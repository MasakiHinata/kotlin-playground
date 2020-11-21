package bindsInstance

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class UserModule{
    @Provides
    fun provideUser(
        @Named("name") name: String,
        @Named("hobby") hobby: String,
        age: Int
    ): User {
        return User(name, hobby, age)
    }
}