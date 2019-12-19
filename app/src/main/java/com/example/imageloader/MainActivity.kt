package com.example.imageloader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DecodeFormat
import com.hujz.imageloader.loader.ImageLoader
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val imag1 =
        "https://cdn.wispx.cn/wallpaper/bing/2018/08/31/09117f7e49ca128ad92e9b4408db56a4.jpg"
    val imag2 =
        "https://cdn.wispx.cn/wallpaper/bing/2018/11/18/6a6ba118883f7c11df5a13daa23003b7.jpg"
    val imag3 =
        "https://cdn.wispx.cn/wallpaper/bing/2018/11/28/deadd2d87057acec977da5ced2c2970a.jpg"

    private val data: List<String> by lazy {
        listOf(
            imag1,
            imag2,
            imag3,
            imag3,
            imag2,
            imag1,
            imag3,
            imag2,
            imag1,
            imag2,
            imag2,
            imag1,
            imag3,
            imag2,
            imag2,
            imag1,
            imag2,
            imag3,
            imag1,
            imag2,
            imag3,
            imag3,
            imag3,
            imag1
        )
    }
    private val mMainAdapter: MainAdapter by lazy { MainAdapter(R.layout.item_main, data) }

    private val mScrollListener: RecyclerView.OnScrollListener by lazy {
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    ImageLoader.instance.resume(this@MainActivity)
                } else {
                    ImageLoader.instance.pause(this@MainActivity)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRvMain.layoutManager = LinearLayoutManager(this)
        mMainAdapter.bindToRecyclerView(mRvMain)

        mRvMain.addOnScrollListener(mScrollListener)

        ImageLoader.instance.with {
            url = imag1
            decodeFormat = DecodeFormat.PREFER_ARGB_8888
        }.into(mIvMain)

    }
}