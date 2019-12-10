package com.example.imageloader

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * <pre>
 *     author : Hjz
 *     time   : 2019/12/10
 *     desc   :
 * </pre>
 */
class MainAdapter(layoutResId: Int, data: List<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: String) {

    }
}