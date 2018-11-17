package com.vault.videostube

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.vault.videostube.data.VideosDataSource
import com.vault.videostube.data.VideosRepository
import com.vault.videostube.ui.videos.VideosViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: VideosDataSource): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideosViewModel::class.java)) {
            return VideosViewModel(repository) as T
        } else {
            throw IllegalArgumentException("viewmodel $modelClass not found!" )
        }
    }
}