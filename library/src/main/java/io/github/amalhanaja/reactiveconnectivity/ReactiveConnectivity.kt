package io.github.amalhanaja.reactiveconnectivity

import android.Manifest
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresPermission
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Alfian Akmal Hanantio on 12/01/18.
 * Email : amalhanaja@gmail.com
 * Github: https://github.com/amalhanaja/
 */
class ReactiveConnectivity
(
        private val context: Context,
        private val onChange: (ConnectivityType) -> Unit,
        private val onError: ((e: Throwable) -> Unit)? = null
) {
    private var disposable: Disposable? = null

    companion object {

        @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
        fun buildObserver(context: Context): Observable<ConnectivityType> = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> ConnectivityObserverFactory.createMarsmallowObserver(context)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> ConnectivityObserverFactory.createLollipopObserver(context)
            else -> ConnectivityObserverFactory.createPreLollipopObserver(context)
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun subscribe() {
        disposable = buildObserver(context)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onChange.invoke(it) }) { onError?.invoke(it) ?: it.printStackTrace() }
    }

    fun dispose() {
        disposable?.dispose()
    }
}