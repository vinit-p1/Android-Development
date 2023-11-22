package com.example.broadcastreceiverapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class NewProductReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == "com.example.NewProduct") {
            val productName = intent.getStringExtra("Name")
            val qty = intent.getIntExtra("Qty", 0)
            Log.d("NewProductReceiver",
                "Received Product Name: $productName")
            context.startService(
                Intent(context, NewProductService::class.java).also {
                    it.putExtra("Name", productName)
                    it.putExtra("Qty", qty)
                }
            )
        }
    }
}