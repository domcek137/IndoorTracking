package com.estimote.proximity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.widget.GridView
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory
import com.estimote.proximity.estimote.ProximityContent
import com.estimote.proximity.estimote.ProximityContentAdapter
import com.estimote.proximity.estimote.ProximityContentManager

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

class MainActivity : AppCompatActivity() {

    private var proximityContentManager: ProximityContentManager? = null
    private var proximityContentAdapter: ProximityContentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        proximityContentAdapter = ProximityContentAdapter(this)
        val gridView = findViewById<GridView>(R.id.gridView)
        gridView.adapter = proximityContentAdapter

        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this,
                        {
                            Log.d("app", "requirements fulfilled")
                            startProximityContentManager()
                        },
                        { requirements ->
                            Log.e("app", "requirements missing: " + requirements)
                        }
                        , { throwable ->
                    Log.e("app", "requirements error: " + throwable)
                })
    }

    private fun startProximityContentManager() {
        proximityContentManager = ProximityContentManager(this)
        proximityContentManager?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        proximityContentManager?.stop()
    }

    fun setNearbyContent(nearbyContent: List<ProximityContent>) {
        proximityContentAdapter?.setNearbyContent(nearbyContent)
        proximityContentAdapter?.notifyDataSetChanged()
    }
}
