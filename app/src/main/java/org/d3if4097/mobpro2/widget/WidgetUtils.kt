package org.d3if4097.mobpro2.widget

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
}