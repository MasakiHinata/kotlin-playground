package fieldinjection

import dagger.Component


/**
 * フィールドインジェクション
 */
@Component
interface FruitsComponent {
    fun inject(application: FruitsApplication)
}