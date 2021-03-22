package com.example.hwlife.viewmodel

import android.app.Activity
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.hwlife.model.Map
import com.example.hwlife.repository.FirebaseStorageRepo

class MapLevelViewModel(val ac: Activity, val title : String) : ViewModel() {

    private val TAG = "***MapLevelViewModel"

    private val firebaseStorageRepo = FirebaseStorageRepo.getInstance(ac.applicationContext)

    fun back() = ac.finish()

    fun fecthMapData(level: String) : LiveData<MutableList<Map>> {
        val mutableData = MutableLiveData<MutableList<Map>>()
        firebaseStorageRepo.getMapData(level).observeForever {
            mutableData.value = it
        }
        return mutableData
    }

/*
    @BindingAdapter("imageFromUrl")
    fun bindImageFromUrl(view : ImageView, imageUrl: String?){
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view) }
    }
 */




}

class MapLevelViewModelFactory(private val ac: Activity, private  val title:String) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MapLevelViewModel::class.java)) {
            MapLevelViewModel(ac, title) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}