package com.example.truckercore.view.expressions

import android.content.Context
import android.os.Build
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load

fun ImageView.loadGif(url: Any? = null, context: Context){
    val imageLoaderGifSupport = getGifLoader(context)
    load(url, imageLoaderGifSupport)
}

private fun getGifLoader(context: Context): ImageLoader {
    val imageLoaderGifSupport = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()
    return imageLoaderGifSupport
}
