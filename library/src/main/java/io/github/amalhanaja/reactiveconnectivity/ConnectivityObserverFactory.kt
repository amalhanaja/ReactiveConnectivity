package io.github.amalhanaja.reactiveconnectivity

import android.Manifest
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Looper
import android.os.PowerManager
import android.support.annotation.RequiresApi
import android.support.annotation.RequiresPermission
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.functions.Action

/**
 * Created by Alfian Akmal Hanantio on 13/01/18.
 * Email : amalhanaja@gmail.com
 * Github: https://github.com/amalhanaja/
 */
internal object ConnectivityObserverFactory {

    @RequiresApi(Build.VERSION_CODES.M)
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun createMarsmallowObserver(context: Context): Observable<ConnectivityType> {
        val manager = context.getConnectivityManager()
        val idleFilter = IntentFilter(PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED)
        val networkRequest = NetworkRequest.Builder().apply {
            addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
        }.build()
        return Observable.create {
            val callback = ConnectivityCallback(context, it)
            val idleReceiver = IdleReceiver(it)
            manager.registerNetworkCallback(networkRequest, callback)
            context.registerReceiver(idleReceiver, idleFilter)
            it.setDisposable(Disposables.fromAction {
                try {
                    manager.unregisterNetworkCallback(callback)
                } catch (e: Exception) {
                    it.onError(Exception("Couldn't Unregister Network Callback", e))
                }
                try {
                    context.unregisterReceiver(idleReceiver)
                } catch (e: Exception) {
                    it.onError(Exception("Couldn't Unregister IDLE "))
                }
            })
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun createLollipopObserver(context: Context): Observable<ConnectivityType> {
        val manager = context.getConnectivityManager()
        return Observable.create {
            val callback = ConnectivityCallback(context, it)
            manager.registerNetworkCallback(NetworkRequest.Builder().build(), callback)
            it.setDisposable(Disposables.fromAction {
                try {
                    manager.unregisterNetworkCallback(callback)
                } catch (e: Exception) {
                    it.onError(Exception("Couldn't Unregister Network Callback", e))
                }
            })
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun createPreLollipopObserver(context: Context): Observable<ConnectivityType> {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        return Observable.create {
            val receiver = ConnectivityReceiver(it)
            context.registerReceiver(receiver, intentFilter)
            it.setDisposable(disposeInUiThread(it, Action {
                try {
                    context.unregisterReceiver(receiver)
                } catch (e: Exception) {
                    e.printStackTrace()
                    it.onError(Exception("Receiver was Already Unregistered", e))
                }
            }))
        }
    }

    private fun <T> disposeInUiThread(emitter: ObservableEmitter<T>, action: Action): Disposable {
        return Disposables.fromAction {
            when {
                Looper.getMainLooper() == Looper.myLooper() -> {
                    action.run()
                }
                else -> {
                    val inner = AndroidSchedulers.mainThread().createWorker()
                    inner.schedule {
                        try {
                            action.run()
                        } catch (e: Exception) {
                            e.printStackTrace()
                            emitter.onError(Exception("Could not unregister receiver in UI Thread", e))
                        } finally {
                            inner.dispose()
                        }
                    }
                }
            }
        }
    }
}