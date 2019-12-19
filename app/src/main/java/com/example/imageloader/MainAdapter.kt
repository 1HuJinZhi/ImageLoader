package com.example.imageloader

import com.chad.library.adapter.base.BaseQuickAdapter
import com.hujz.imageloader.loader.ImageLoader
import kotlinx.android.synthetic.main.item_main.view.*

/**
 * <pre>
 *     author : Hjz
 *     time   : 2019/12/10
 *     desc   :
 * </pre>
 */
class MainAdapter(layoutResId: Int, data: List<String>?) :
    BaseQuickAdapter<String, KViewHolder>(layoutResId, data) {

    override fun convert(helper: KViewHolder, item: String) {

        val mIvMainItem = helper.itemView.mIvMainItem

        ImageLoader.instance.with { url = item }.into(mIvMainItem)

    }

}