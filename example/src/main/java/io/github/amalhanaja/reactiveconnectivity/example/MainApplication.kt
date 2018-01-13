package io.github.amalhanaja.reactiveconnectivity.example

import android.app.Application
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Alfian Akmal Hanantio on 12/01/18.
 * Email : amalhanaja@gmail.com
 * Github: https://github.com/amalhanaja/
 */
class MainApplication: Application() {

    private val disposables by lazy { CompositeDisposable() }

    override fun onCreate() {
        super.onCreate()
//        val observer = ReactiveConnectivity.observeConnection(this)
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnDispose {
//                    println("DISPOSED")
//                }
//                .subscribe {
//                    println(it.getDetailType().name)
//                }
//        disposables.add(observer)


    }


    override fun onTerminate() {
        println("TERMINATED")
        super.onTerminate()
//        disposables.dispose()
    }
}