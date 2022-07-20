package com.estimote.proximity.estimote

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import com.estimote.proximity.MainActivity
import com.estimote.proximity.MyApplication
import com.estimote.proximity.R
import com.estimote.proximity_sdk.api.ProximityObserver
import com.estimote.proximity_sdk.api.ProximityObserverBuilder
import com.estimote.proximity_sdk.api.ProximityZoneBuilder
import java.util.*

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

class ProximityContentManager(private val context: Context) {

    private var proximityObserverHandler: ProximityObserver.Handler? = null



    fun start() {
        val proximityObserver = ProximityObserverBuilder(context, (context.applicationContext as MyApplication).cloudCredentials)
            .withTelemetryReportingDisabled()
            .withEstimoteSecureMonitoringDisabled()
            .onError { throwable ->
                Log.e("app", "proximity observer error: $throwable")
            }
            .withBalancedPowerMode()
            .build()

        val zone = ProximityZoneBuilder()
            .forTag("pokus-gsg")
            .inCustomRange(10.0)
            .onContextChange { contexts ->
                val nearbyContent = ArrayList<ProximityContent>(contexts.size)
                for (context in contexts) {
                    val title: String = context.attachments["pokus-gsg/title"] ?: "unknown"
                    val subtitle = Utils.getShortIdentifier(context.deviceId)
                    var type: String = context.attachments["type"] ?: "unknown"
                    if (type == "door"){
                        type = "Entered through the doors!"
                    }
                    nearbyContent.add(ProximityContent(title, subtitle, type))
                    print(ProximityContent(title, subtitle, type))
                }
                (context as MainActivity).setNearbyContent(nearbyContent)
            }
            .build()

        proximityObserverHandler = proximityObserver.startObserving(zone)
    }

    fun stop() {
        proximityObserverHandler?.stop()
    }
}
