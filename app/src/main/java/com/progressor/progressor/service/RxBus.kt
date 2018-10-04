package com.progressor.progressor.service

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

object RxBus {

    val disposables = mutableMapOf<Any, CompositeDisposable>()
    val publishSubject = PublishSubject.create<Any>()!!

    inline fun <reified T : Any> subscribe(subscriber: Any, noinline consumer: (T) -> Unit) {
        val observer = publishSubject.ofType(T::class.java).subscribe(consumer)
        val disposable = disposables[subscriber]
                ?: CompositeDisposable().apply { disposables[subscriber] = this }
        disposable.add(observer)
    }

    fun unsubscribe(subscriber: Any) {
        disposables[subscriber]?.clear()
        disposables.remove(subscriber)
    }

    fun publish(event: Any) = publishSubject.onNext(event)
}