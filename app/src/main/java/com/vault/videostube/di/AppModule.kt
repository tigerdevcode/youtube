package com.vault.videostube.di

import android.app.Application
import android.arch.persistence.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.vault.videostube.data.VideosDataSource
import com.vault.videostube.data.VideosRepository
import com.vault.videostube.data.local.VideosDao
import com.vault.videostube.data.local.VideosDatabase
import com.vault.videostube.data.remote.YoutubeService
import com.vault.videostube.utils.BaseSchedulerProvider
import com.vault.videostube.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideYoutubeService(): YoutubeService {
        val apiKey = "AIzaSyDvQ9mvwwzPJVSiqN6s7GyXidH6N0R9aNQ"
        val baseURL = "https://www.googleapis.com/youtube/v3/"
        val timeOut: Long = 30

        val builder = OkHttpClient.Builder()

        builder.connectTimeout(timeOut, TimeUnit.SECONDS)
        builder.readTimeout(timeOut, TimeUnit.SECONDS)
        builder.writeTimeout(timeOut, TimeUnit.SECONDS)

        // Headers
        fun headersInterceptor() = Interceptor {
            chain -> chain.proceed(chain.request().newBuilder()
                .addHeader("X-Android-Package", "packageName")
                .build()) }

        // builder.interceptors().add(headersInterceptor())

        // Logging
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.interceptors().add(interceptor)

        val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd")
                .create()

        // RX Adapter
        val rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

        return Retrofit.Builder()
                .baseUrl(baseURL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .build()
                .create(YoutubeService::class.java)
    }

    @Singleton
    @Provides
    fun provideVideosDatabase(application: Application): VideosDatabase {
        return Room
                .databaseBuilder(application, VideosDatabase::class.java, "videos.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideVideosDao(db: VideosDatabase): VideosDao = db.videoDao()

    @Singleton
    @Provides
    fun provideVideosRepository(youtubeService: YoutubeService, videosDao: VideosDao, schedulerProvider: BaseSchedulerProvider): VideosDataSource {
        return VideosRepository(youtubeService, videosDao, schedulerProvider)
    }

    @Singleton
    @Provides
    fun provideSchedulers():BaseSchedulerProvider = SchedulerProvider()

}