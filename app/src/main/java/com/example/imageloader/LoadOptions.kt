package com.example.imageloader


/**
 * <pre>
 *     author : Hjz
 *     time   : 2019/12/05
 *     desc   :
 * </pre>
 */
data class LoadOptions(
    val url: String = "",
    val error: Int = DEFAULT_ERROR_OR_PLACEHOLDER,
    val placeholder: Int = DEFAULT_ERROR_OR_PLACEHOLDER,
    val isGif: Boolean = false,
    val radius: Int = 0,
    val isSkipMemory: Boolean = false,
    val displayStyle: LoaderImageScaleType = LoaderImageScaleType.CENTER_CROP,
    val cacheStyle: LoaderCacheStrategy = LoaderCacheStrategy.RESULT,
    val loaderRequestCallback: LoaderRequestCallback? = null
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
        CENTER_FIT
    }
}
