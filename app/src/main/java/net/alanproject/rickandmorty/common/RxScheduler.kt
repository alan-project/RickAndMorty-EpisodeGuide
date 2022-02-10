package net.alanproject.rickandmorty.common

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

interface RxSchedulers {
    val io: Scheduler
    val ui: Scheduler
}

class RxSchedulerImpl @Inject constructor() : RxSchedulers {
    override val io: Scheduler
        get() = Schedulers.io()

    override val ui: Scheduler
        get() = AndroidSchedulers.mainThread()
}