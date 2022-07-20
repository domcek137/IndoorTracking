package com.estimote.proximity

import android.app.Application
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

class MyApplication : Application() {

    val cloudCredentials =  EstimoteCloudCredentials("pokus-gsg", "7388470f5e759c61a3bb08604a32988a")
}
