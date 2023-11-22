package com.example.broadcastreceiverapp

import android.Manifest
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NewProductService : Service() {

    companion object {
        var index: Int = 0
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val productName = intent.getStringExtra("Name")
        val qty = intent.getIntExtra("Qty",0)
        val productListIntent = Intent("com.example.miniproject1.ACTION_SHOW_PRODUCT_LIST")
        productListIntent.setComponent(ComponentName("com.example.miniproject1", "com.example.miniproject1.ProductListActivity"))
        productListIntent.putExtra("productName", productName)
        productListIntent.putExtra("Qty", qty)
        productListIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)


        if(productListIntent != null) {

            startActivity(productListIntent)

            val pendingIntent =
                PendingIntent.getActivity(
                    application,
                    119911,
                    productListIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )

            val notification = NotificationCompat.Builder(application, "newProduct")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("A new product: $productName with Qty: $qty was added.")
                .addAction(R.mipmap.ic_launcher_round, "Open Product List", pendingIntent)
                .setAutoCancel(true)
                .build()

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(this, "Missing notification permission.", Toast.LENGTH_SHORT).show()
            }

            NotificationManagerCompat.from(application)
                .notify(index++, notification)
        }
        else
        {
            Toast.makeText(this, "Error: Could not find the activity!", Toast.LENGTH_SHORT).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }
}