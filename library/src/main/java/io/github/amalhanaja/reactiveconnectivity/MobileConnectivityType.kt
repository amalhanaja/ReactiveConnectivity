package io.github.amalhanaja.reactiveconnectivity

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

/**
 * Created by Alfian Akmal Hanantio on 13/01/18.
 * Email : amalhanaja@gmail.com
 * Github: https://github.com/amalhanaja/
 */

class MobileConnectivityType : ServiceConnection {
    override fun onServiceDisconnected(p0: ComponentName?) {

    }

    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {

    }
}