package com.example.weatherlogger.view.activity

import android.Manifest
import android.R
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weatherlogger.databinding.ActivityMainBinding
import com.example.weatherlogger.viewmodel.WeatherDataViewModel
import com.example.weatherlogger.viewmodel.WeatherViewModelFactory
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

/**
 * MainActivity is used to design and operate weather screen operation
 */
class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    LocationListener {

    private var binding: ActivityMainBinding? = null
    private var googleApiClient: GoogleApiClient? = null
    private var locationRequest: LocationRequest? = null
    private var location: Location? = null
    private val REQUEST_CODE = 1001
    private var weatherDataViewModel: WeatherDataViewModel? = null

    private val TAG = MainActivity::class.java.canonicalName

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        weatherDataViewModel = ViewModelProvider(
            this,
            WeatherViewModelFactory(application)
        ).get(WeatherDataViewModel::class.java)

        binding!!.viewModel = weatherDataViewModel
        binding!!.lifecycleOwner = this

    }

    override fun onResume() {
        super.onResume()
        connectGoogleApiClient()
    }

    private fun connectGoogleApiClient() {
        googleApiClient =
            GoogleApiClient.Builder(this) // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this) //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build()

        locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(10 * 1000)        // 10 seconds, in milliseconds
            .setFastestInterval(10 * 1000) // 10 second, in milliseconds
        googleApiClient?.connect()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onConnected(bundle: Bundle?) {
        if (!checkPermission()) {
            return
        }

        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient,
                locationRequest,
                this
            )
        } else {
            if (location?.latitude != null && location?.longitude != null) {
                updateLatLog(location?.latitude!!, location?.longitude!!)
            }
        }
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(result: ConnectionResult) {

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onLocationChanged(p0: Location?) {
        if (location?.latitude != null && location?.longitude != null) {
            updateLatLog(location?.latitude!!, location?.longitude!!)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun updateLatLog(lat: Double, lon: Double) {
        weatherDataViewModel?.lat = lat
        weatherDataViewModel?.lon = lon

        Log.d(TAG, "Lat : $lat, Lon : $lon")

        weatherDataViewModel?.getWeatherData()
    }

    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                showExplanation(
                    "Permission Needed",
                    "Location is required to fetch weather information",
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), REQUEST_CODE
                )
            } else {
                googleApiClient?.disconnect()
                requestPermission(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), REQUEST_CODE
                )
            }
            return false
        }
        return true
    }

    private fun showExplanation(
        title: String,
        message: String,
        permission: Array<String>,
        permissionRequestCode: Int
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                R.string.ok
            ) { dialog, id ->
                requestPermission(
                    permission,
                    permissionRequestCode
                )
            }
        builder.create().show()
    }

    private fun requestPermission(permissionName: Array<String>, permissionRequestCode: Int) {
        ActivityCompat.requestPermissions(this, permissionName, permissionRequestCode)
    }

}