package com.example.broadcastreceiverapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.example.broadcastreceiverapp.ui.theme.BroadcastReceiverAppTheme

class MainActivity : ComponentActivity() {

    private val receiver = NewProductReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BroadcastReceiverAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(", Waiting for broadcast")
                }
            }
        }
        NotificationManagerCompat.from(application)
            .createNotificationChannel(NotificationChannel(
                "newProduct",
                "Product Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ))
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStart() {
        super.onStart()
        if (ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                123
            )
        }

        registerReceiver(
            receiver,
            IntentFilter("com.example.NewProduct"),
            RECEIVER_EXPORTED
        )
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
