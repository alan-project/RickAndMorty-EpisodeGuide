package net.alanproject.rickandmorty.common

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.TestScheduler

class TestSchedulers : RxSchedulers {

    private val testScheduler: TestScheduler = TestScheduler()

    override val io: Scheduler
        get() = testScheduler

    override val ui: Scheduler
        get() = testScheduler

    fun triggerActions() {
        testScheduler.triggerActions()
    }

}