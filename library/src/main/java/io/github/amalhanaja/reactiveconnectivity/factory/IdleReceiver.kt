package io.github.amalhanaja.reactiveconnectivity.factory

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.annotation.RequiresPermission
import io.github.amalhanaja.reactiveconnectivity.ConnectivityType
import io.github.amalhanaja.reactiveconnectivity.getConnectivityManager
import io.github.amalhanaja.reactiveconnectivity.getNetworkType
import io.github.amalhanaja.reactiveconnectivity.getPowerManager
import io.reactivex.ObservableEmitter

/**
 * Created by Alfian Akmal Hanantio on 13/01/18.
 * Email : amalhanaja@gmail.com
 * Github: https://github.com/amalhanaja/
 */
class IdleReceiver(private val emitter: ObservableEmitter<ConnectivityType>): BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.M)
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onReceive(p0: Context?, p1: Intent?) {
        p0?.let {
            if (isIdle(it)){
                emitter.onNext(it.getConnectivityManager().getNetworkType())
            } else {
                emitter.onNext(it.getConnectivityManager().getNetworkType())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isIdle(context: Context): Boolean {
        val manager = context.getPowerManager()
        return manager.isDeviceIdleMode && !manager.isIgnoringBatteryOptimizations(context.packageName)
    }
}