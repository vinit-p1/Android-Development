package com.example.miniproject1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import androidx.core.graphics.drawable.toBitmap
import com.example.miniproject1.R
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.mapboxsdk.plugins.annotation.Symbol
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener

class MapStoresActivity : AppCompatActivity() {

    private lateinit var permissionsManager: PermissionsManager
    var idStore: Long = -11
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        idStore = intent.getLongExtra("STORE_ID", -11)
        val long = intent.getDoubleExtra("STORE_LONG", 0.0)
        val lat = intent.getDoubleExtra("STORE_LAT", 0.0)
        val dbvm = MapStoreViewModel(application)
        var permissionsListener: PermissionsListener = object : PermissionsListener {
            override fun onExplanationNeeded(permissionsToExplain: List<String>) {
            }

            override fun onPermissionResult(granted: Boolean) {
            }
        }

        if (!PermissionsManager.areLocationPermissionsGranted(this)) {
            permissionsManager = PermissionsManager(permissionsListener)
            permissionsManager.requestLocationPermissions(this)
        }
        setContent {
            MapScreen(dbvm = dbvm, idStore = idStore, Point.fromLngLat(long, lat))
        }


    }
}

@Composable
fun MapScreen(dbvm: MapStoreViewModel, idStore: Long, point : Point) {

    val store = dbvm.getStoreById(idStore).collectAsState(initial = null)
    var point: Point? by remember {
        mutableStateOf(point)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        MapBoxMap(
            onPointChange = { point = it
                            dbvm.updateItem(store.value!!.copy(long = it.longitude(), lat = it.latitude()))},
            point = point,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun MapBoxMap(
    modifier: Modifier = Modifier,
    onPointChange: (Point) -> Unit,
    point: Point?,
) {
    val context = LocalContext.current
    val marker = remember(context) {
        context.getDrawable(R.drawable.baseline_place_24)!!.toBitmap()
    }
    var pointAnnotationManager: PointAnnotationManager? by remember {
        mutableStateOf(null)
    }
    AndroidView(
        factory = {
            MapView(it).also { mapView ->
                mapView.getMapboxMap().loadStyleUri(Style.TRAFFIC_DAY)
                val annotationApi = mapView.annotations
                pointAnnotationManager = annotationApi.createPointAnnotationManager()

                mapView.getMapboxMap().addOnMapClickListener { p ->
                    onPointChange(p)
                    true
                }
            }
        },
        update = { mapView ->
            if (point != null) {
                pointAnnotationManager?.let {
                    it.deleteAll()
                    val pointAnnotationOptions = PointAnnotationOptions()
                        .withPoint(point)
                        .withIconImage(marker)

                    it.create(pointAnnotationOptions)
                    mapView.getMapboxMap()
                        .flyTo(CameraOptions.Builder().zoom(16.0).center(point).build())
                }
            }
            NoOpUpdate
        },
        modifier = modifier
    )
}