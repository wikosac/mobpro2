package org.d3if4097.mobpro2.widget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.d3if4097.mobpro2.Covid19Api
import org.d3if4097.mobpro2.MainActivity
import org.d3if4097.mobpro2.R
import java.text.SimpleDateFormat
import java.util.*

class CovidWidgetProvider : AppWidgetProvider() {

    companion object {
        fun updateAllWidget(context: Context, manager: AppWidgetManager,
                            ids: IntArray) {
            for (id in ids) {
                updateAppWidget(context, manager, id)
            }
        }

        @SuppressLint("UnspecifiedImmutableFlag")
        private fun updateAppWidget(context: Context,
                                    manager: AppWidgetManager, id: Int) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent, 0)
            val views = RemoteViews(context.packageName, R.layout.widget_main)
            views.setOnClickPendingIntent(R.id.dataPanel, pendingIntent)

            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val date = prefs.getLong(WidgetUtils.KEY_DATE, -1L)
            val data = prefs.getInt(WidgetUtils.KEY_DATA, -1)
            updateUI(context, views, date, data)

            val intentRefresh = Intent(context, CovidWidgetProvider::class.java)
            intentRefresh.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            intentRefresh.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(id))
            val pendingRefresh = PendingIntent.getBroadcast(context, id,
                intentRefresh, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(R.id.refreshButton, pendingRefresh)

            manager.updateAppWidget(id, views)
        }

        private fun updateUI(context: Context, views: RemoteViews,
                             date: Long, data: Int) {
            if (date == -1L || data == -1) {
                views.setTextViewText(R.id.dateTextView,
                    context.getString(R.string.belum_ada_data))
                views.setTextViewText(R.id.dataTextView,
                    context.getString(R.string.sad_emoji))
                views.setTextViewText(R.id.descTextView,
                    context.getString(R.string.klik_refresh))
            }
            else {
                val formatter = SimpleDateFormat("dd MMMM yyyy",
                    Locale("ID", "id")
                )
                views.setTextViewText(R.id.dateTextView,
                    formatter.format(date))
                views.setTextViewText(R.id.dataTextView, data.toString())
                views.setTextViewText(R.id.descTextView,
                    context.getString(R.string.kasus_positif))
            }
        }
    }

    override fun onUpdate(context: Context?, manager: AppWidgetManager?, ids: IntArray?) {
        if (context == null || manager == null || ids == null) return

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val result = Covid19Api.service.getData()
                WidgetUtils.saveData(prefs, result.update.harian.last())
            }
            catch (e: Exception) {
                Log.e("CovidWidgetProvider", "Error: ${e.message}")
            }
            finally {
                updateAllWidget(context, manager, ids)
            }
        }
    }
}