package io.github.amalhanaja.reactiveconnectivity.library.factory

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.annotation.RequiresPermission
import io.github.amalhanaja.reactiveconnectivity.ConnectivityType
import io.github.amalhanaja.reactiveconnectivity.getConnectivityManager
import io.github.amalhanaja.reactiveconnectivity.getNetworkType
import io.reactivex.ObservableEmitter

/**
 * Created by Alfian Akmal Hanantio on 13/01/18.
 * Email : amalhanaja@gmail.com
 * Github: https://github.com/amalhanaja/
 */
class ConnectivityReceiver
constructor(private val emitter: ObservableEmitter<ConnectivityType>): BroadcastReceiver() {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onReceive(context: Context?, p1: Intent?) {
        context?.let {
            emitter.onNext(it.getConnectivityManager().getNetworkType())
        }
    }
}