package com.hujz.imageloader.loader

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

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
    fun loadImage(target: View, loadOptions: LoadOptions, view: View? = null)

    fun loadImage(target: View, loadOptions: LoadOptions, activity: Activity)

    fun loadImage(target: View, loadOptions: LoadOptions, context: Context)

    fun loadImage(target: View, loadOptions: LoadOptions, fragment: Fragment)

    fun loadImage(target: View, loadOptions: LoadOptions, fragmentActivity: FragmentActivity)

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