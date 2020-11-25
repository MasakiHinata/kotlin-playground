# Dagger2
- [準備](#--)
- [依存関係を管理するもの](#依存関係を管理するもの)
  * [Module](#module)
  * [Component](#component)
  * [Componentを使ってDIする](#Componentを使ってDIする)
- [DIする方法](#DIする方法)
  * [Constructor Injection](#constructor-injection)
  * [Field Injection](#field-injection)
- [インターフェースに対して注入](#インターフェースに対して注入)
  * [Binds](#binds)
  * [Provides](#provides)
  * [Qualifier](#qualifier)
- [Builder - 実行中にパラメータを指定](#Builder---実行中にパラメータを指定)
  * [builder](#builder)
  * [Binds Instance](#binds-instance)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>

## 準備
```groovy
apply plugin: 'kotlin-kapt'

dependencies {
		def daggerVersion = "2.28.3"
    implementation "com.google.dagger:dagger:$daggerVersion"
    kapt "com.google.dagger:dagger-compiler:$daggerVersion"
}
```
## 依存関係を管理するもの
### Module
`@Provides`や`@Binds`により依存関係を示す(コンストラクタインジェクションだけのときは必要ない)

```kotlin
@Module
class CarModule {
    @Provides
    fun provideEngine(): Engine {
        return HondaEngine()
    }
}
```

### Component
- Dagger2は全ての依存オブジェクトをComponentで管理する
    - daggerがコードを自動生成し、DIできるようにする
    - コードを生成するために、一度ビルドする必要がある

```kotlin
@Component(
    modules = [CarModule::class]
)
interface CarComponent {
    fun getCar(): Car
}
```

### Componentを使ってDIする
Daggerが生成したコンポーネントクラス(`Dagger<コンポーネント名>`)を利用する

```kotlin
// コンストラクタの代わりに、createを使ってインスタンス化
val component = DaggerCarComponent.create()
val car = component.getCar()
car.drive()
```

## DIする方法
### Constructor Injection
`@Inject`でコンストラクタをマークすると、Daggerによって注入される

```kotlin
// このコンストラクタを利用して他のクラスに注入される
class Screen @Inject constructor() {
    fun show(words: String) {
        println(words)
    }
}
```

```kotlin

// コンストラクタインジェクションされているクラスなら
// Daggerが注入してくれる
class Calculator @Inject constructor(
    private val keyboard: Keyboard
) {
    fun add(a: Int, b: Int): Int {
        return a + b
    }
}
```

#### コンポーネントを作成

```kotlin
@Component
interface CalculatorComponent {
    fun getCalculator(): Calculator
}
```
#### DI
```kotlin
val calculator = DaggerCalculatorComponent.create().getCalculator()
calculator.add(10, 15)
```

### Field Injection
Injectアノテーションを利用してフィールドインジェクションを行う

```kotlin
class FruitsApplication {
		// このフィールドをDIする
    @Inject lateinit var apple: Apple
    
    ...
}
```

#### Component

DIされるクラスを指定する

```kotlin
@Component
interface FruitsComponent {
    fun inject(application: FruitsApplication)
}
```

#### DI

injectメソッドを呼び出してField Injectionを行う

```java
class FruitsApplication {
    @Inject
    lateinit var apple: Apple

    init {
        DaggerFruitsComponent
            .builder()
            .build()
            .inject(this)
    }
}
```

## インターフェースに対して注入
### Binds
インターフェースやサードライブラリのオブジェクトにはコンストラクタインジェクションを指定することができない。
このため`@Provides`や`@Binds`を用いて依存関係をDaggerに教える
- `@Binds` を使うことで記述量を減らすことができる
    - 動作しないことがあるのでProvidesを使ったほうが簡単な場合がある
- 引数に注入するオブジェクト、戻り値にインターフェースを指定したabstractメソッド

#### DIされるクラスを作成

DIされるクラスは必ず、コンストラクタインジェクションを指定しないといけない

```kotlin
interface DatabaseInterface {
    fun loadMessage(): String
}
```

```kotlin
class AppleDatabase @Inject constructor(): DatabaseInterface {
    override fun loadMessage(): String {
        return "Apple Database"
    }
}
```

#### モジュールを作成

`@Binds`を指定したメソッドを作成

```kotlin
@Module
abstract class DatabaseModule {
    @Binds
    abstract fun bindDatabase(database: AppleDatabase): DatabaseInterface
}
```

#### コンポーネントを作成

```kotlin
@Component(
    modules = [DatabaseModule::class]
)
interface DatabaseComponent {
    fun getDatabase(): DatabaseInterface
}
```

#### 注入する

```kotlin
val database = DaggerDatabaseComponent.create().getDatabase()
println(database.loadMessage())
```

### Provides
#### Bindsの問題点
- コンストラクタに`@Inject`アノテーションをつけなければならないが
- インターフェースはコンストラクタを持たない
- サードパーティーのクラスは編集することができない

#### Providesでできること
- インターフェースの実装を注入する
- サードパーティーのクラスを注入する
- 設定可能なオブジェクトは設定する

#### DIされるクラスを作成

DIされるクラスには制限がない

```kotlin
interface Engine {
    val speed: Int
}
```

```kotlin
data class HondaEngine(override val speed: Int = 100) : Engine
```

#### ProvidsでDIする

```kotlin
@Module
class CarModule {
    @Provides
    fun provideEngine(): Engine {
        return HondaEngine()
    }
}
```

#### 引数にDIされるオブジェクトを指定する

```kotlin
@Module
class CarModule {
    ...

    // 引数にDIされるクラスを指定することで
    // 新たにDIを作成するときに利用できる
    @Provides
    fun provideCar(
        engine: Engine
    ): Car {
        return HondaCar(engine)
    }
}
```

#### Componentを作成

```kotlin
@Component(
    modules = [CarModule::class]
)
interface CarComponent {
    fun getCar(): Car
}
```

#### 注入する

```kotlin
val car = DaggerCarComponent.create().getCar()
car.drive()
```

### Qualifier
同じ型のインスタンスを区別する

```kotlin
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BatteryL

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BatteryS
```

```kotlin
interface Battery {
    val batteryLevel: Int
}

// 2つとも同じインターフェースを継承している
// -> インターフェースを指定して受け取るとき、どちらが渡されるかわからない
data class LargeBattery(
    override val batteryLevel: Int = 100
) : Battery

data class SmallBattery(
    override val batteryLevel: Int = 50
) : Battery
```

#### 提供するものを指定

```kotlin
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
```

#### 受け取るものを指定

```kotlin
@Provides
fun providePhone(
    // アノテーションにより指定
    @BatteryL battery: Battery
): Phone {
    return Phone(battery)
}
```

## Builder - 実行中にパラメータを指定
### builder
- モジュールをコンポーネントに注入する
#### Module

モジュールで引数を受け取り、インスタンス生成時に利用する

```kotlin
@Module
class SmartPhoneModule(private val password: String) {
    @Provides
    fun provideSmartPhone(): SmartPhone {
        return SmartPhone(password)
    }
}
```

```kotlin
data class SmartPhone(val password: String)
```

#### Component

Componentに関しては特別なことはしなくて良い

```kotlin
@Component(
    modules = [SmartPhoneModule::class]
)
interface SmartPhoneComponent {
    fun getSmartPhone(): SmartPhone
}
```

#### builder

Moduleに引数を指定するとcreateメソッドが利用できなくなる

`build`メソッドを利用して値を初期化する

```kotlin
// ビルダーに値を指定したモジュールを渡す
val spComponent = DaggerSmartPhoneComponent
        .builder()
        .smartPhoneModule(SmartPhoneModule("password"))
        .build()

val smartPhone = spComponent.getSmartPhone()
```

### Binds Instance
- 値をコンポーネントに注入する

#### Module

同じ型のオブジェクトがある場合は`＠Named`によって区別する

```kotlin
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
```

```kotlin
data class User(
    val name: String,
    val hobby: String,
    val age: Int
)
```

#### Component

- interfaceの`@Component.Builder`を作成
- `@BindsInstance` によって値を結びつける
- Componentを返すメソッドを用意する

```kotlin
@Component(modules = [UserModule::class])
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
```

#### Componentの作成

```kotlin
val userComponent = DaggerUserComponent
    .builder()
    .name("Alice")
    .hobby("reading")
    .age(18)
    .build()
```