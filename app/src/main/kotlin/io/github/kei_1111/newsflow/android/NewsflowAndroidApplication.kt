package io.github.kei_1111.newsflow.android

import android.app.Application
import io.github.kei_1111.newsflow.library.shared.initKoin

class NewsflowAndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(this)
    }
}
