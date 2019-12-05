package com.example.imageloader

import android.content.Context
import android.view.View

/**
 * <pre>
 *     author : Hjz
 *     time   : 2019/12/05
 *     desc   :
 * </pre>
 */
class ImageLoader private constructor() {

    private lateinit var mOptions: LoadOptions

    companion object {
        val INSTANCE: ImageLoader by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { ImageLoader() }
        private var mLoader: ILoader? = null
    }

    /**
     * 初始化并设置图片加载策略
     * 在[android.app.Application]中
     */
    fun init(loaderStrategy: ILoader) {
        mLoader = loaderStrategy
    }

//    fun with(data: LoadOptions.() -> Unit) = LoadOptions().apply(data){
//        mOptions = this
//    }

    /**
     * 借助于kotlin[apply]方法以及lambda表达式来构造生成[LoadOptions]
     * 简化代码，提升可读性
     */
    fun with(data: LoadOptions.() -> Unit): ImageLoader {
        mOptions = LoadOptions().apply(data)
        return this
    }

    /**
     * 将图片加载到目标视图[View]
     * 这里之所以用[View]，是由于第三方图片加载库很可能会出现自定义ImageView的情况
     * 例如：Facebook的Fresco
     * 需要注意的是：必须要确保此时[mOptions]以及初始化完毕
     */
    fun into(target: View) {
        mLoader?.loadImage(target, mOptions) ?: error()
    }

    /**
     * 清除磁盘缓存(子线程)
     */
    fun clearDiskCache(ctx: Context) {
        mLoader?.clearDiskCache(ctx) ?: error()
    }

    /**
     * 清除内存缓存(UI线程)
     */
    fun clearMemoryCache(ctx: Context) {
        mLoader?.clearMemoryCache(ctx) ?: error()
    }

    /**
     * 获取图片缓存大小
     */
    fun getCacheSize(ctx: Context): Long {
        return mLoader?.getCacheSize(ctx) ?: error()
    }


    private fun error(): Nothing =
        throw IllegalArgumentException("You must provide an image loader strategy first!")
}