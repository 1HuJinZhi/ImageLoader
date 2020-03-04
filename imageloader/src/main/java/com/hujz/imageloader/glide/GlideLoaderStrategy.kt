package com.hujz.imageloader.glide

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.FileUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.hujz.imageloader.loader.ILoader
import com.hujz.imageloader.loader.LoadOptions
import com.hujz.imageloader.loader.LoaderCacheStrategy
import com.hujz.imageloader.loader.LoaderImageScaleType
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/09
 *     desc   :
 * </pre>
 */
class GlideLoaderStrategy : ILoader {

    private fun getCacheDir() = InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR

    override fun loadImage(target: View, loadOptions: LoadOptions, view: View?) {
        requestImage(target, loadOptions, view)
    }

    override fun loadImage(target: View, loadOptions: LoadOptions, activity: Activity) {
        requestImage(target, loadOptions, activity)
    }

    override fun loadImage(target: View, loadOptions: LoadOptions, context: Context) {
        requestImage(target, loadOptions, context)
    }

    override fun loadImage(target: View, loadOptions: LoadOptions, fragment: Fragment) {
        requestImage(target, loadOptions, fragment)
    }

    override fun loadImage(
        target: View,
        loadOptions: LoadOptions,
        fragmentActivity: FragmentActivity
    ) {
        requestImage(target, loadOptions, fragmentActivity)
    }

    override fun clearMemoryCache(context: Context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Glide.get(context).clearMemory()
        }
    }

    override fun clearDiskCache(context: Context) {
        Glide.get(context).clearDiskCache()
    }

    override fun pause(context: Context) {
        Glide.with(context).pauseRequests()
    }

    override fun resume(context: Context) {
        Glide.with(context).resumeRequests()
    }

    override fun getCacheSize(context: Context): Long {
        return getGlideCacheSize()
    }

    private fun getGlideCacheSize(): Long {
        return FileUtils.getLength(getCacheDir())
    }

    @SuppressLint("CheckResult")
    @Suppress("UNCHECKED_CAST")
    private fun requestImage(target: View, options: LoadOptions, with: Any? = null) {

        val builder =
            generateRequestBuilder(target, options, with) as RequestBuilder<Any>

        builder.listener(object : RequestListener<Any> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Any>?,
                isFirstResource: Boolean
            ): Boolean {
                options.loaderRequestCallback?.onFailed()
                return false
            }

            override fun onResourceReady(
                resource: Any?,
                model: Any?,
                target: Target<Any>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                options.loaderRequestCallback?.onSuccess()
                return false
            }

        })

//        if (!options.url.isBlank())
        builder.load(options.url).into(target as ImageView)
//        else
//            throw IllegalArgumentException("You should give an image resource URL first!")
    }

    /**
     * Apply loader options and generate image request builder.
     * You can see loader option detail from [LoadOptions]
     */
    private fun generateRequestBuilder(
        target: View,
        options: LoadOptions,
        with: Any? = null
    ): RequestBuilder<out Any> {

        val requestManager = when (with) {
            is View -> {
                Glide.with(with)
            }
            is Activity -> {
                Glide.with(with)
            }
            is Context -> {
                Glide.with(with)
            }
            is Fragment -> {
                Glide.with(with)
            }
            is FragmentActivity -> {
                Glide.with(with)
            }
            else -> {
                Glide.with(target)
            }
        }

        val requestBuilder = if (options.isGif)
            requestManager.asGif()
        else
            requestManager.asBitmap()

        return requestBuilder.apply(getLoaderOptions(options))
    }

    /**
     * generate glide options [com.bumptech.glide.request.RequestOptions] for displaying image.
     */
    @SuppressLint("CheckResult")
    private fun getLoaderOptions(options: LoadOptions): RequestOptions {

        val glideOptions = RequestOptions()

        glideOptions.format(options.decodeFormat)

        glideOptions.fallback(options.error)

        glideOptions.placeholder(options.placeholder)

        if (options.overrideWidth != 0 && options.overrideHeight != 0)
            glideOptions.override(options.overrideWidth, options.overrideHeight)

        when (options.cacheStyle) {
            LoaderCacheStrategy.NONE -> glideOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
            LoaderCacheStrategy.ALL -> glideOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
            LoaderCacheStrategy.SOURCE -> glideOptions.diskCacheStrategy(
                DiskCacheStrategy.RESOURCE
            )
            LoaderCacheStrategy.RESULT -> glideOptions.diskCacheStrategy(
                DiskCacheStrategy.DATA
            )
        }

        if (options.isSkipMemory)
            glideOptions.skipMemoryCache(true)

        if (options.roundedCorners != 0) {
            val transform = RoundedCornersTransformation(options.roundedCorners, 0)
            when (options.displayStyle) {
                LoaderImageScaleType.CENTER_CROP -> glideOptions.transform(
                    transform,
                    CenterCrop()
                )
                LoaderImageScaleType.CENTER_INSIDE -> glideOptions.transform(
                    transform,
                    CenterInside()
                )
                LoaderImageScaleType.FIT_CENTER -> glideOptions.transform(
                    transform,
                    FitCenter()
                )
                else -> {
                }
            }
        } else {
            when (options.displayStyle) {
                LoaderImageScaleType.CENTER_CROP -> glideOptions.centerCrop()
                LoaderImageScaleType.CENTER_INSIDE -> glideOptions.centerInside()
                LoaderImageScaleType.FIT_CENTER -> glideOptions.fitCenter()
                LoaderImageScaleType.CIRCLE_CROP -> glideOptions.circleCrop()
            }
        }

        return glideOptions
    }
}