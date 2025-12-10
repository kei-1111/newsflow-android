package io.github.kei_1111.newsflow.android.core.test

import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Qualifiersも共通して指定したいが、
 * カスタムテストランナーで指定したものはRoborazziに上手く反映されないため各テストで指定する
 */
class NewsflowTestRunner(testClass: Class<*>) : RobolectricTestRunner(testClass) {

    override fun buildGlobalConfig(): Config {
        return Config.Builder()
            .setSdk(SDK_VERSION)
            .build()
    }

    companion object {
        private const val SDK_VERSION = 36
    }
}
