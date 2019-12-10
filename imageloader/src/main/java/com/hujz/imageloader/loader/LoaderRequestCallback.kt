package com.hujz.imageloader.loader

/**
 * <pre>
 *     author : Hjz
 *     time   : 2019/12/05
 *     desc   : Image load callback
 * </pre>
 */
interface LoaderRequestCallback {
    fun onSuccess()
    fun onFailed()
}