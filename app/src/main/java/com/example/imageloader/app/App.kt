package com.example.imageloader.app

import android.app.Application
import android.content.ComponentCallbacks2
import com.blankj.utilcode.util.Utils
import com.hujz.imageloader.glide.GlideLoaderStrategy
import com.hujz.imageloader.loader.ImageLoader

/**
 * <pre>
 *     author : Hjz
 *     time   : 2019/12/05
 *     desc   :
 * </pre>
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        ImageLoader.INSTANCE.init(GlideLoaderStrategy())
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN)
            ImageLoader.INSTANCE.clearMemoryCache(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        ImageLoader.INSTANCE.clearMemoryCache(this)
    }
}