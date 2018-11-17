package com.vault.videostube.ui.videos

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.vault.videostube.BaseActivity
import com.vault.videostube.R
import javax.inject.Inject

class VideosListActivity : BaseActivity() {

    @BindView(R.id.videosRecyclerList)
    lateinit var videosRecyclerView: RecyclerView

    @BindView(R.id.search_bar)
    lateinit var searchView: SearchView

    private val videosViewModel: VideosViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, viewModelFactory).get(VideosViewModel::class.java)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var unBinder: Unbinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.videos_list_activity)
        unBinder = ButterKnife.bind(this)


        val videosAdapter = VideosAdapter()
        videosRecyclerView.apply {
            adapter = videosAdapter
            layoutManager = LinearLayoutManager(this@VideosListActivity)
        }

        videosViewModel.allVideos().observe(this, Observer {
            videosAdapter.submitList(it)
        })
        var searchAdapter = SearchAdapter()
        searchView.setOnSearchClickListener {
            Log.e(TAG, "searchView onClick $it")
            videosViewModel.searching.value = true
            searchAdapter = SearchAdapter()
            videosRecyclerView.adapter = searchAdapter
        }
        searchView.setOnCloseListener {
            Log.e(TAG, "searchView onClose")
            videosViewModel.searching.value = false
            videosRecyclerView.adapter = videosAdapter
            false
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                videosViewModel.searchVideos(query!!).observe(this@VideosListActivity, Observer {
                    searchAdapter.submitList(it)
                })
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        unBinder.unbind()
    }

    companion object {
        private const val TAG = "VideosListActivity"
    }
}
