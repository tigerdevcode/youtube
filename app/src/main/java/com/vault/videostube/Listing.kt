package com.vault.videostube

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

data class Listing<T> (
        // the LiveData of paged lists for the UI to observe
        val pagedList: LiveData<PagedList<T>>,
        val networkState: LiveData<NetworkState>
)