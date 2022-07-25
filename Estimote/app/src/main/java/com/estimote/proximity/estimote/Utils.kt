package com.estimote.proximity.estimote

import android.graphics.Color

import com.estimote.proximity.R

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

object Utils {

    fun getShortIdentifier(deviceIdentifier: String): String {
        return deviceIdentifier.substring(0, 4) + "..." + deviceIdentifier.substring(28, 32)
    }

    internal fun getEstimoteColor(colorName: String): Int {
        return when (colorName) {
            "ice" -> Color.rgb(109, 170, 199)

            "blueberry" -> Color.rgb(36, 24, 93)

            "candy" -> Color.rgb(219, 122, 167)

            "mint" -> Color.rgb(155, 186, 160)

            "beetroot" -> Color.rgb(84, 0, 61)

            "lemon" -> Color.rgb(195, 192, 16)

            else -> R.color.defaultContentBackground
        }
    }
}
