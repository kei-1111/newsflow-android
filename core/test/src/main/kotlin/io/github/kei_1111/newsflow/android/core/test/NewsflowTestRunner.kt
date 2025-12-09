package io.github.kei_1111.newsflow.android.core.test

import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

class NewsflowTestRunner(testClass: Class<*>) : RobolectricTestRunner(testClass) {

    override fun buildGlobalConfig(): Config {
        return Config.Builder()
            .setSdk(SDK_VERSION)
            .setQualifiers(QUALIFIERS)
            .build()
    }

    companion object {
        private const val SDK_VERSION = 36
        private const val QUALIFIERS = "w400dp-h800dp-xxhdpi"
    }
}
