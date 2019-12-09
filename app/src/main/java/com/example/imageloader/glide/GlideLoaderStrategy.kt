package com.example.imageloader.glide

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
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.imageloader.loader.ILoader
import com.example.imageloader.loader.LoadOptions
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

    private fun requestImage(target: View, options: LoadOptions) {
        val builder: RequestBuilder<Any> =
            generateRequestBuilder(target, options) as RequestBuilder<Any>
        builder.listener(object : RequestListener<Any> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Any>?,
                isFirstResource: Boolean
            ): Boolean {
                if (options.loaderRequestCallback != null) {
                    options.loaderRequestCallback?.onFailed()
                }
                return false
            }

            override fun onResourceReady(
                resource: Any?,
                model: Any?,
                target: Target<Any>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                if (options.loaderRequestCallback != null) {
                    options.loaderRequestCallback?.onSuccess()
                }
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
    private fun getLoaderOptions(options: LoadOptions): RequestOptions {

        val glideOptions = RequestOptions()

        if (options.error != LoadOptions.DEFAULT_ERROR_OR_PLACEHOLDER)
            glideOptions.fallback(options.error)

        if (options.placeholder != LoadOptions.DEFAULT_ERROR_OR_PLACEHOLDER)
            glideOptions.placeholder(options.placeholder)

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

        if (options.isCircle)
            glideOptions.apply(RequestOptions.circleCropTransform())
        else if (options.radius != 0)
            glideOptions.transform(RoundedCornersTransformation(options.radius, 0))

        if (options.isSkipMemory)
            glideOptions.skipMemoryCache(true)

        when (options.displayStyle) {
            LoadOptions.LoaderImageScaleType.CENTER_CROP -> glideOptions.centerCrop()
            LoadOptions.LoaderImageScaleType.CENTER_INSIDE -> glideOptions.centerInside()
            LoadOptions.LoaderImageScaleType.CENTER_FIT -> glideOptions.fitCenter()
        }

        return glideOptions
    }
}