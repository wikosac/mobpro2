package org.d3if4097.mobpro2.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import org.d3if4097.mobpro2.model.Harian

object WidgetUtils {
    const val KEY_DATE = "date"
    const val KEY_DATA = "data"
    fun saveData(sharedPref: SharedPreferences, data: Harian) {
        with(sharedPref.edit()) {
            putLong(KEY_DATE, data.key)
            putInt(KEY_DATA, data.jumlahPositif.value)
            apply()
        }
    }
    fun updateUI(context: Context) {
        val appContext = context.applicationContext
        val widgetManager = AppWidgetManager.getInstance(appContext)
        val ids = widgetManager.getAppWidgetIds(
            ComponentName(appContext, CovidWidgetProvider::class.java)
        )
        CovidWidgetProvider.updateAllWidget(appContext, widgetManager, ids)
    }
}