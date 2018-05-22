package io.github.amalhanaja.reactiveconnectivity.library.factory

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.support.annotation.RequiresApi
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
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class ConnectivityCallback(private val context: Context, private val emitter: ObservableEmitter<ConnectivityType>):
        ConnectivityManager.NetworkCallback() {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onCapabilitiesChanged(network: Network?, networkCapabilities: NetworkCapabilities?) {
        emitter.onNext(context.getConnectivityManager().getNetworkType())
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onLinkPropertiesChanged(network: Network?, linkProperties: LinkProperties?) {
        emitter.onNext(context.getConnectivityManager().getNetworkType())
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onUnavailable() {
        emitter.onNext(context.getConnectivityManager().getNetworkType())
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onLost(network: Network?) {
        emitter.onNext(context.getConnectivityManager().getNetworkType())
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onLosing(network: Network?, maxMsToLive: Int) {
        emitter.onNext(context.getConnectivityManager().getNetworkType())
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onAvailable(network: Network?) {
        emitter.onNext(context.getConnectivityManager().getNetworkType())
    }
}