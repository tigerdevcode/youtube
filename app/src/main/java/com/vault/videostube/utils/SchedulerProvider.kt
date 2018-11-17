package com.vault.videostube.utils

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulerProvider @Inject constructor(): BaseSchedulerProvider {

    override fun computation() = Schedulers.computation()

    override fun io() = Schedulers.io()

    override fun ui() = AndroidSchedulers.mainThread()
}