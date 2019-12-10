package com.example.imageloader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.Nullable
import com.hujz.imageloader.loader.ImageLoader
import com.hujz.imageloader.loader.LoadOptions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ImageLoader.INSTANCE
            .with {
                url =
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575895394333&di=6bfc5b54006e3d3b4262f11680b38ab5&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F9922720e0cf3d7ca810f3732f81fbe096a63a9fd.jpg"
                overrideWidth = 500
                overrideHeight = 600
            }
            .into(mIvMain)

        ImageLoader.INSTANCE
            .with {
                url =
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575895394333&di=6bfc5b54006e3d3b4262f11680b38ab5&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F9922720e0cf3d7ca810f3732f81fbe096a63a9fd.jpg"
                overrideWidth = 500
                overrideHeight = 600
                roundedCorners = 20
            }
            .into(mIvMainRoundedCorners)

        ImageLoader.INSTANCE
            .with {
                url =
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575895394333&di=6bfc5b54006e3d3b4262f11680b38ab5&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F9922720e0cf3d7ca810f3732f81fbe096a63a9fd.jpg"
                overrideWidth = 500
                overrideHeight = 600
                displayStyle = LoadOptions.LoaderImageScaleType.CIRCLE_CROP
            }
            .into(mIvMainCircle)
    }
}