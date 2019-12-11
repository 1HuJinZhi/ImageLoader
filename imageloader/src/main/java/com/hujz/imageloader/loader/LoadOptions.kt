package com.hujz.imageloader.loader

import com.bumptech.glide.load.DecodeFormat


/**
 * <pre>
 *     author : Hjz
 *     time   : 2019/12/05
 *     desc   :
 * </pre>
 */
data class LoadOptions(
    var url: String = "",
    var error: Int = DEFAULT_ERROR_OR_PLACEHOLDER,
    var placeholder: Int = DEFAULT_ERROR_OR_PLACEHOLDER,
    var isGif: Boolean = false,
    var roundedCorners: Int = 0,
    var isSkipMemory: Boolean = false,
    var overrideWidth: Int = 0,
    var overrideHeight: Int = 0,
    var displayStyle: LoaderImageScaleType = LoaderImageScaleType.CENTER_CROP,
    var cacheStyle: LoaderCacheStrategy = LoaderCacheStrategy.RESULT,
    var loaderRequestCallback: LoaderRequestCallback? = null,
    var decodeFormat: DecodeFormat = DecodeFormat.PREFER_RGB_565
) {
    companion object {
        const val DEFAULT_ERROR_OR_PLACEHOLDER = -1
    }

    /**
     * 图片缓存策略
     */
    enum class LoaderCacheStrategy {
        NONE,
        ALL,
        SOURCE,
        RESULT
    }

    enum class LoaderImageScaleType {
        CENTER_CROP,
        CENTER_INSIDE,
        FIT_CENTER,
        CIRCLE_CROP,
    }
}
