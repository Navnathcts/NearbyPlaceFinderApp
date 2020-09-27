package com.johnsoncontrol.nearbyplacesfinderapp.view.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.johnsoncontrol.nearbyplacesfinderapp.R
import com.johnsoncontrol.nearbyplacesfinderapp.utils.SharedPrefUtility


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var MY_PERMISION_CODE = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_restaurant,
                R.id.nav_hospitals,
                R.id.nav_pubs,
                R.id.nav_schools,
                R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        checkForPermission()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        Log.i("On request permiss", "executed")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISION_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveLocationPermissionGranted(true)
            } else {
                saveLocationPermissionGranted(false)
            }
        }
    }

    private fun saveLocationPermissionGranted(permissionGranted: Boolean) {
        SharedPrefUtility.saveBooleanValue(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION, permissionGranted
        )
    }

    private fun showAlert() {
        val dialog: android.app.AlertDialog.Builder =
            android.app.AlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.enable_location))
            .setMessage(getString(R.string.enable_location_message))
            .setPositiveButton(
                getString(R.string.title_allow)
            ) { _, paramInt ->
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    MY_PERMISION_CODE
                )
            }
            .setNegativeButton(
                getString(R.string.title_cancel),
                { paramDialogInterface, paramInt -> })
        dialog.show()
    }

    fun checkForPermission() {
        when {
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED -> {
                when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                        when {
                            ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) && ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) -> {
                                showAlert()
                            }
                            else -> {
                                when {
                                    SharedPrefUtility.getBooleanValue(
                                        this,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) -> {
                                        ActivityCompat.requestPermissions(
                                            this,
                                            arrayOf(
                                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                                Manifest.permission.ACCESS_FINE_LOCATION
                                            ),
                                            MY_PERMISION_CODE
                                        )
                                    }
                                    else -> {
                                        showAlert()
                                    }
                                }
                            }
                        }
                    }
                    else -> saveLocationPermissionGranted(true)
                }
            }
        }
    }
}
