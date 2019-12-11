package com.hujz.imageloader.glide

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.view.View
import android.widget.ImageView
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

    override fun loadImage(target: View, loadOptions: LoadOptions) {
        requestImage(target, loadOptions)
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
    private fun requestImage(target: View, options: LoadOptions) {

        val builder = generateRequestBuilder(target, options) as RequestBuilder<Any>

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

        if (!options.url.isBlank())
            builder.load(options.url).into(target as ImageView)
        else
            throw IllegalArgumentException("You should give an image resource URL first!")
    }

    /**
     * Apply loader options and generate image request builder.
     * You can see loader option detail from [LoadOptions]
     */
    private fun generateRequestBuilder(
        targetView: View,
        options: LoadOptions
    ): RequestBuilder<out Any> {

        val requestBuilder = if (options.isGif)
            Glide.with(targetView).asGif()
        else
            Glide.with(targetView).asBitmap()

        return requestBuilder.apply(getLoaderOptions(options))
    }

    /**
     * generate glide options [com.bumptech.glide.request.RequestOptions] for displaying image.
     */
    @SuppressLint("CheckResult")
    private fun getLoaderOptions(options: LoadOptions): RequestOptions {

        val glideOptions = RequestOptions()

        glideOptions.format(options.decodeFormat)

        if (options.error != LoadOptions.DEFAULT_ERROR_OR_PLACEHOLDER)
            glideOptions.fallback(options.error)

        if (options.placeholder != LoadOptions.DEFAULT_ERROR_OR_PLACEHOLDER)
            glideOptions.placeholder(options.placeholder)

        if (options.overrideWidth != 0 && options.overrideHeight != 0)
            glideOptions.override(options.overrideWidth, options.overrideHeight)

        glideOptions.override(Target.SIZE_ORIGINAL)

        when (options.cacheStyle) {
            LoadOptions.LoaderCacheStrategy.NONE -> glideOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
            LoadOptions.LoaderCacheStrategy.ALL -> glideOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
            LoadOptions.LoaderCacheStrategy.SOURCE -> glideOptions.diskCacheStrategy(
                DiskCacheStrategy.RESOURCE
            )
            LoadOptions.LoaderCacheStrategy.RESULT -> glideOptions.diskCacheStrategy(
                DiskCacheStrategy.DATA
            )
        }

        if (options.isSkipMemory)
            glideOptions.skipMemoryCache(true)

        if (options.roundedCorners != 0) {
            val transform = RoundedCornersTransformation(options.roundedCorners, 0)
            when (options.displayStyle) {
                LoadOptions.LoaderImageScaleType.CENTER_CROP -> glideOptions.transform(
                    transform,
                    CenterCrop()
                )
                LoadOptions.LoaderImageScaleType.CENTER_INSIDE -> glideOptions.transform(
                    transform,
                    CenterInside()
                )
                LoadOptions.LoaderImageScaleType.FIT_CENTER -> glideOptions.transform(
                    transform,
                    FitCenter()
                )
                else -> {
                }
            }
        } else {
            when (options.displayStyle) {
                LoadOptions.LoaderImageScaleType.CENTER_CROP -> glideOptions.centerCrop()
                LoadOptions.LoaderImageScaleType.CENTER_INSIDE -> glideOptions.centerInside()
                LoadOptions.LoaderImageScaleType.FIT_CENTER -> glideOptions.fitCenter()
                LoadOptions.LoaderImageScaleType.CIRCLE_CROP -> glideOptions.circleCrop()
            }
        }

        return glideOptions
    }
}