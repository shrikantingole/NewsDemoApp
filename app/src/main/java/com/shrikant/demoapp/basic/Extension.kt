package com.shrikant.demoapp.basic

import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.shrikant.demoapp.R

@BindingAdapter("android:srcImage")
fun setImageViewResource(imageView: ImageView, imageUrl: Any?) {
    if (imageUrl == null) return
    Glide.with(imageView.context)
        .load(imageUrl).apply(RequestOptions().centerCrop())
        .placeholder(R.drawable.ic_newspaper)
        .into(imageView)
}


fun Context.isConnectedToNetwork(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
}
