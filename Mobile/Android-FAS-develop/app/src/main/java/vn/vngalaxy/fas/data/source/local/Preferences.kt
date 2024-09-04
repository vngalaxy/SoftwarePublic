package vn.vngalaxy.fas.data.source.local

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.reflect.TypeToken
import io.appwrite.extensions.gson
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Preferences(private val context: Context) {
    private val pref = PreferenceManager.getDefaultSharedPreferences(context)

    inner class StringProp(private val key: String, defaultValue: String) : ReadWriteProperty<Any, String> {
        private var value: String = pref.getString(key, null) ?: defaultValue

        override fun getValue(thisRef: Any, property: KProperty<*>): String = value

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
            pref.edit().putString(key, value).apply()
            this.value = value
        }
    }

    inner class BooleanProp(private val key: String, defaultValue: Boolean) : ReadWriteProperty<Any, Boolean> {
        private var value: Boolean = pref.getBoolean(key, defaultValue)

        override fun getValue(thisRef: Any, property: KProperty<*>): Boolean = value

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
            pref.edit().putBoolean(key, value).apply()
            this.value = value
        }
    }

    inner class StringListProp(private val key: String) : ReadWriteProperty<Any, List<String>> {
        override fun getValue(thisRef: Any, property: KProperty<*>): List<String> {
            val json = pref.getString(key, null)
            return if (json != null) {
                gson.fromJson(json, object : TypeToken<List<String>>() {}.type)
            } else {
                emptyList()
            }
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: List<String>) {
            val json = gson.toJson(value)
            pref.edit().putString(key, json).apply()
        }
    }
}