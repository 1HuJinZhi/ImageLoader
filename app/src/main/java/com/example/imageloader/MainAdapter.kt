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
class MainAdapter(layoutResId: Int, data: MutableList<String>?) :
    BaseQuickAdapter<String, KViewHolder>(layoutResId, data) {

    override fun convert(helper: KViewHolder, item: String?) {
        val mIvMainItem = helper.itemView.mIvMainItem
        item?.let {
            ImageLoader.INSTANCE.with { url = item }.into(mIvMainItem)
        }
    }

}