package io.github.amalhanaja.reactiveconnectivity

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.PowerManager
import android.support.annotation.RequiresPermission
import android.telephony.TelephonyManager

/**
 * Created by Alfian Akmal Hanantio on 12/01/18.
 * Email : amalhanaja@gmail.com
 * Github: https://github.com/amalhanaja/
 */
fun Context.getConnectivityManager(): ConnectivityManager =
    getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
fun Context.getPowerManager(): PowerManager =
        getSystemService(Context.POWER_SERVICE) as PowerManager

val ConnectivityManager.connectivityType : ConnectivityType
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    get() = when {
        this.activeNetworkInfo != null && this.activeNetworkInfo.isConnectedOrConnecting ->
            activeNetworkInfo.connectivityType
        else -> ConnectivityType.NOT_CONNECTED
    }

val NetworkInfo.connectivityType: ConnectivityType
    get() = when (type) {
        ConnectivityManager.TYPE_WIFI -> ConnectivityType.WIFI
        ConnectivityManager.TYPE_MOBILE -> when (subtype) {
            TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE,
            TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT,
            TelephonyManager.NETWORK_TYPE_IDEN ->
                ConnectivityType.MOBILE_2G

            TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0,
            TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA,
            TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA,
            TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD,
            TelephonyManager.NETWORK_TYPE_HSPAP ->
                ConnectivityType.MOBILE_3G

            TelephonyManager.NETWORK_TYPE_LTE ->
                ConnectivityType.MOBILE_4G

            else -> ConnectivityType.UNKNOWN_MOBILE_CONNECTION
        }
        ConnectivityManager.TYPE_BLUETOOTH -> ConnectivityType.BLUETOOTH
        ConnectivityManager.TYPE_VPN -> ConnectivityType.VPN
        ConnectivityManager.TYPE_ETHERNET -> ConnectivityType.ETHERNET
        ConnectivityManager.TYPE_WIMAX -> ConnectivityType.WIMAX
        else -> ConnectivityType.NOT_CONNECTED
    }