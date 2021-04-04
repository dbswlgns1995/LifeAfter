package com.example.hwlife.binding

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.squareup.picasso.Picasso

object BindingAdapters {

    /*
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String, error : Drawable) {
        Picasso.get().load(url).error(error).into(view)
    }*/

    @BindingAdapter("imageUri")
    fun loadImage(view : ImageView, uri: Uri?){
        Glide.with(view.context)
            .load(uri)
            .into(view)
    }
/*
    @BindingAdapter("setMargin")
    fun setMargin(view : View){

    }
*/

}