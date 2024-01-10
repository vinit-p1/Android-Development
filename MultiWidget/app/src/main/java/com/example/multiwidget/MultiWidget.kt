package com.example.multiwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */

class MultiWidget : AppWidgetProvider() {

    companion object {
        private var mediaPlayer: MediaPlayer? = null
        private var musicIndex = 0
        private val allMusic = arrayOf(R.raw.sleepymusic, R.raw.cinematicfairytale)
        private var isPlaying = false

        private val allImages = arrayOf(R.drawable.kitten, R.drawable.morekittens, R.drawable.note )
        private var imageIndex = 0
    }

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

    private fun makePendingIntent(context: Context, action: String): PendingIntent {
        val intent = Intent(context, MultiWidget::class.java)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }


    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        // Creating remote view
        val remoteViews = RemoteViews(context.packageName, R.layout.multi_widget)

        val ytIntent = Intent(Intent.ACTION_VIEW)
        ytIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ytIntent.data = Uri.parse("https://youtube.com")

        // Use FLAG_IMMUTABLE for PendingIntent associated with an Activity
        val ytPendingIntent = PendingIntent.getActivity(
            context,
            0,
            ytIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        remoteViews.setOnClickPendingIntent(R.id.ytButton, ytPendingIntent)

        // ImageView
        // Setting the ImageView initial image
        remoteViews.setImageViewResource(R.id.imageView, allImages[imageIndex])
        remoteViews.setOnClickPendingIntent(R.id.imageButton2, makePendingIntent(context,"NEXT_IMAGE"))


        remoteViews.setTextViewText(
            R.id.playStatus,
            (if (isPlaying) "Playing: " else "") + "Track $musicIndex"
        )
        remoteViews.setOnClickPendingIntent(R.id.playButton, makePendingIntent(context, "PLAY"))
        remoteViews.setOnClickPendingIntent(R.id.stopButton, makePendingIntent(context, "STOP"))
        remoteViews.setOnClickPendingIntent(R.id.nextButton, makePendingIntent(context, "NEXT"))

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)

    }

    private fun triggerUpdate(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds =
            appWidgetManager.getAppWidgetIds(
                ComponentName(
                context,
                MultiWidget::class.java)
            )
        onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        when (intent.action) {
            "NEXT_IMAGE" -> {
                imageIndex = 1 - imageIndex
            }

            "PLAY" -> {
                play(context);
            }

            "STOP" -> {
                stop();
            }

            "NEXT" -> {
                next(context);
            }
        }

        triggerUpdate(context)
    }

    private fun next(context: Context) {
        var wasPlaying = isPlaying
        stop();
        musicIndex = (musicIndex + 1) % allMusic.size
        if (wasPlaying)
            play(context);
    }

    private fun stop() {

        if (isPlaying) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            isPlaying = false
        }    }

    private fun play(context: Context) {

        mediaPlayer?.reset()
        mediaPlayer = MediaPlayer.create(context, allMusic[musicIndex])
        mediaPlayer?.setOnCompletionListener {
            if (musicIndex == allMusic.size - 1)
                isPlaying = false
            next(context)
            triggerUpdate(context)
        }
        isPlaying = true
        mediaPlayer?.start()
    }

}