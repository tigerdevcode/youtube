package com.vault.videostube.utils

import android.support.annotation.NonNull
import io.reactivex.Scheduler



interface BaseSchedulerProvider {

    @NonNull
    fun computation(): Scheduler

    @NonNull
    fun io(): Scheduler

    @NonNull
    fun ui(): Scheduler
}