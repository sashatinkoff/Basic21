package com.isidroid.b21.sample.clean.presentation.main

import android.Manifest
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import androidx.activity.viewModels
import com.isidroid.b21.R
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.ext.alert
import com.isidroid.b21.ext.dpToPx
import com.isidroid.b21.ext.observe
import com.isidroid.b21.ext.permission
import com.isidroid.b21.sample.clean.presentation.location.LocationService
import com.isidroid.b21.sample.data.DbLocation
import com.isidroid.b21.utils.BindActivity
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Circle
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.geometry.PolylineBuilder
import com.yandex.mapkit.map.CameraPosition
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : BindActivity(layoutRes = R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }
    private val locationReceiver = LocationReceiver(callback = { viewModel.onLocation(it) })

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)
        createMap()

        button.setOnClickListener {
            permission(permission = Manifest.permission.ACCESS_FINE_LOCATION,
                onGranted = { LocationService.start(this) })
        }

        buttonClear.setOnClickListener { viewModel.requestClear() }
        buttonPath.setOnClickListener { viewModel.showPath() }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
        registerReceiver(locationReceiver, LocationReceiver.intentFilter)
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        unregisterReceiver(locationReceiver)
    }

    override fun onCreateViewModel() {
        observe(viewModel.state) { onState(it) }
    }

    private fun createMap() {
        MapKitFactory.initialize(this)
        mapView.map.move(CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f))
    }

    // ViewModel
    private fun onState(state: MainViewModel.State?) {
        when (state) {
            is MainViewModel.State.ConfirmClear -> callConfirmClear(state.size)
            is MainViewModel.State.Data -> onLocation(state.list, state.location)
            is MainViewModel.State.DataPath -> showPath(state.list)
        }
    }

    private fun showPath(list: List<DbLocation>) {
        mapView.map.mapObjects.clear()
        mapView.map.move(
            CameraPosition(with(list.first()) {
                Point(latitude, longitude)
            }, 14.0f, 0.0f, 0.0f)
        )



        list.forEach { location ->
            val circle = Circle(Point(location.latitude, location.longitude), dpToPx(18).toFloat())
            mapView.map.mapObjects.addCircle(circle, Color.RED, dpToPx(12).toFloat(), Color.RED)
        }

        showPolyLine(*list.toTypedArray())
    }

    private fun onLocation(list: List<Location>, location: Location) {
        val point = Point(location.latitude, location.longitude)
        mapView.map.move(
            CameraPosition(point, 14.0f, 0.0f, 0.0f)
        )

        val circle = Circle(point, dpToPx(18).toFloat())
        mapView.map.mapObjects.addCircle(circle, Color.RED, dpToPx(12).toFloat(), Color.RED)

        if (list.size > 1) {
            showPolyLine(
                with(list[list.size - 2]) {
                    DbLocation(latitude = latitude, longitude = longitude)
                },
                with(location) { DbLocation(latitude = latitude, longitude = longitude) }
            )
        }
    }

    private fun showPolyLine(vararg locations: DbLocation) {
        val polyline = with(PolylineBuilder()) {
            val polylinePoints = locations.map { Point(it.latitude, it.longitude) }
            mapView.map.mapObjects.addPolyline(Polyline(polylinePoints))
        }


        polyline.strokeWidth = 1f
        polyline.strokeColor = Color.BLACK
        polyline.zIndex = 200.0f
    }

    private fun callConfirmClear(size: Int) = alert(
        message = "Do you really want to remove $size records?",
        positive = "Yes",
        onPositive = { viewModel.clearData() }
    )
}