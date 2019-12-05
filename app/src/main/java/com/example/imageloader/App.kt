package com.example.imageloader

import android.app.Application
import android.content.Context
import android.view.View

/**
 * <pre>
 *     author : Hjz
 *     time   : 2019/12/05
 *     desc   :
 * </pre>
 */
class App : Application(), ILoader {
    override fun loadImage(target: View, loadOptions: LoadOptions) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearMemoryCache(context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearDiskCache(context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pause(context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resume(context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCacheSize(context: Context): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        super.onCreate()
        ImageLoader.init(ILoader(){

        })
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

    }

    override fun onLowMemory() {
        super.onLowMemory()
    }
}