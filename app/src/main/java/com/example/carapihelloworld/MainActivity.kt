package com.example.carapihelloworld
// For accessing vendor properties names (instead of using magic numbers)
// AOSP:
//      Add to Android.bp vehicle generated lib "vendor.nlab.vehicle-V1.0-java"
//      - gen file: out/soong/.intermediates/vendor/nkh-lab/interfaces/vehicle/1.0/vendor.nlab.vehicle-V1.0-java_gen_java/gen/srcs/vendor/nlab/vehicle/V1_0/VehicleProperty.java
// gradle:
//      Add AOSP vehicle generated lib to project, e.g:
//      - from: out/soong/.intermediates/vendor/nkh-lab/interfaces/automotive/vehicle/1.0/vendor.nlab.vehicle-V1.0-java/android_common/javac/vendor.nlab.vehicle-V1.0-java.jar
//      - to: app/libs
import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import java.util.*

class MainActivity : AppCompatActivity() {
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request dangerous permissions only
        val dangPermToRequest = checkDangerousPermissions()
        if (dangPermToRequest.isEmpty()) {
            main()
        } else {
            requestDangerousPermissions(dangPermToRequest)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            //all permissions have been granted
            if (!grantResults.contains(PackageManager.PERMISSION_DENIED)
            ) {
                main()
            }
        }
    }

    private fun main() {
        initGUI()
    }

    private fun checkDangerousPermissions(): List<String> {
        val permissions: MutableList<String> = ArrayList()
        if (checkSelfPermission(Car.PERMISSION_SPEED) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Car.PERMISSION_SPEED)
        }
        if (checkSelfPermission(Car.PERMISSION_ENERGY) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Car.PERMISSION_ENERGY)
        }
        return permissions
    }

    private fun requestDangerousPermissions(permissions: List<String>) {
        requestPermissions(permissions.toTypedArray(), REQUEST_CODE_ASK_PERMISSIONS)
    }

    private fun initGUI() {
        setContentView(R.layout.activity_main)
        tabLayout = findViewById<TabLayout>(R.id.menu_tab_layout)
        viewPager = findViewById<ViewPager>(R.id.viewPager)
        tabLayout!!.setupWithViewPager(viewPager)
        val adapter = ViewPagerAdapter(
            getSupportFragmentManager(),
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        adapter.addFragment(FaultsFragment(), getString(R.string.Faults))
        adapter.addFragment(InstrumentationFragment(), "Instrumentation")
        adapter.addFragment(TelemetryFragment(), getString(R.string.telemetry))
        adapter.addFragment(InspectionReportFragment(), getString(R.string.edvir))
        viewPager!!.setAdapter(adapter)
    }

    companion object {
        private const val TAG = "CarApiHelloWorld"
        private const val REQUEST_CODE_ASK_PERMISSIONS = 1
    }
}