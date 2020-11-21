package fieldinjection

import javax.inject.Inject

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