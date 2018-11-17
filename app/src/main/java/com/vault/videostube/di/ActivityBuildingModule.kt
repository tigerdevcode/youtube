package com.vault.videostube.di

import android.support.v7.app.AppCompatActivity
import com.vault.videostube.ui.player.PlayerActivity
import com.vault.videostube.ui.player.PlayerModule
import com.vault.videostube.ui.videos.VideosListActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildingModule {

    @ContributesAndroidInjector
    abstract fun contributeVideosListActivity(): VideosListActivity

    /*@ContributesAndroidInjector(modules = [PlayerModule::class])
    abstract fun contributePlayerActivity(): PlayerActivity

    @Binds
    abstract fun provideActivity(activity: PlayerActivity): AppCompatActivity*/
}