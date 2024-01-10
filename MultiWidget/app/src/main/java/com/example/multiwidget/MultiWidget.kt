package com.example.multiwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */

// Define the actions for the "Up" and "Down" buttons
const val ACTION_UP = "com.example.multiwidget.ACTION_UP"
const val ACTION_DOWN = "com.example.multiwidget.ACTION_DOWN"
class MultiWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (intent?.action == ACTION_UP) {
            // Handle "Up" button click
            updateImage(context, R.drawable.kitten)
        } else if (intent?.action == ACTION_DOWN) {
            // Handle "Down" button click
            updateImage(context, R.drawable.morekittens)
        } else {
            Log.d("Widget", "Unknown action: ${intent?.action}")
        }
    }

    private fun updateImage(context: Context?, imageResource: Int) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            ComponentName(context!!, MultiWidget::class.java)
        )

        for (appWidgetId in appWidgetIds) {
            val remoteViews = RemoteViews(context?.packageName, R.layout.multi_widget)
            remoteViews.setImageViewResource(R.id.imageView, imageResource)
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }
    }
}
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Creating remote view
    val remoteViews = RemoteViews(context.packageName, R.layout.multi_widget)

    // Setting the ImageView initial image
    remoteViews.setImageViewResource(R.id.imageView, R.drawable.note)

    val ytIntent = Intent(Intent.ACTION_VIEW)
    ytIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    ytIntent.data = Uri.parse("https://youtube.com")

    // Use FLAG_IMMUTABLE for PendingIntent associated with an Activity
    val ytPendingIntent = PendingIntent.getActivity(
        context,
        0,
        ytIntent,
        PendingIntent.FLAG_IMMUTABLE)

    remoteViews.setOnClickPendingIntent(R.id.ytButton, ytPendingIntent)

    // Create intents for the "Up" and "Down" buttons
    val upIntent = Intent(context, MultiWidget::class.java)
    upIntent.action = ACTION_UP
    val upPendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        upIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    remoteViews.setOnClickPendingIntent(R.id.imageButton2, upPendingIntent)

    val downIntent = Intent(context, MultiWidget::class.java)
    downIntent.action = ACTION_DOWN
    val downPendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        downIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    remoteViews.setOnClickPendingIntent(R.id.imageButton1, downPendingIntent)

    appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
}