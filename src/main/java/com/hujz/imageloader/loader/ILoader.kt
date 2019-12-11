package com.hujz.imageloader.loader

import android.content.Context
import android.view.View

/**
 * <pre>
 *     author : Hjz
 *     time   : 2019/12/05
 *     desc   :
 * </pre>
 */
interface ILoader {
    /**
     *  Load the image resource and apply options into target view.
     */
    fun loadImage(target: View, loadOptions: LoadOptions)

    /**
     * Clear the cache from app cache.
     */
    fun clearMemoryCache(context: Context)

    /**
     * Clear the cache from disk.
     */
    fun clearDiskCache(context: Context)

    /**
     * pause the image loading process.
     */
    fun pause(context: Context)

    /**
     * resume the image loading process.
     */
    fun resume(context: Context)


    /**
     * get size of image cache.
     */
    fun getCacheSize(context: Context): Long

}