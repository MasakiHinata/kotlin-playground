package database

import dagger.Binds
import dagger.Module

/**
 * Binds
 * インターフェースなどに対してDIが可能
 * 引数に注入するオブジェクト、戻り値にインターフェースを指定する
 */
@Module
abstract class DatabaseModule {
    @Binds
    abstract fun bindDatabase(database: AppleDatabase): DatabaseInterface
}