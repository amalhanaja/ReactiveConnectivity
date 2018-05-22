package io.github.amalhanaja.reactiveconnectivity

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.annotation.RequiresPermission
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
                emitter.onNext(it.getConnectivityManager().connectivityType)
            } else {
                emitter.onNext(it.getConnectivityManager().connectivityType)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isIdle(context: Context): Boolean {
        val manager = context.getPowerManager()
        return manager.isDeviceIdleMode && !manager.isIgnoringBatteryOptimizations(context.packageName)
    }
}