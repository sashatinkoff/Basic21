package com.isidroid.b21.utils.realm

import android.app.Activity
import android.os.Environment
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.realm.Realm
import io.realm.RealmModel
import java.io.File
import java.lang.reflect.Type


object YRealm {
    private const val GSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
    internal val gson: Gson = GsonBuilder().setDateFormat(GSON_DATE_FORMAT).create()

    fun get(isRefreshed: Boolean = false) =
        Realm.getDefaultInstance().apply { if (isRefreshed) refresh() }

    fun refresh() = get().apply { refresh() }
    fun close() = get().close()

    private fun toJson(item: Any) = gson.toJson(item)
    private fun <T> fromJson(json: String, type: Type): T = gson.fromJson<T>(json, type)
    private fun <T> fromJson(json: String, cl: Class<T>): T = gson.fromJson<T>(json, cl)

    fun update(list: List<RealmModel>) {
        if (list.isEmpty()) return
        val cls = list.first().javaClass
        val json = gson.toJson(list)
        get().executeTransaction { it.createOrUpdateAllFromJson(cls, json) }
    }

    fun backup(
        activity: Activity? = null,
        directory: File = Environment.getExternalStorageDirectory()
    ) = try {
        val destination = File(directory, get().configuration.realmFileName)
        if (destination.exists()) destination.delete()
        get().writeCopyTo(destination)

        activity?.let { a ->
            a.runOnUiThread { Toast.makeText(a, "Realm copied", Toast.LENGTH_SHORT).show() }
        }
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    fun restore(
        directory: File = Environment.getExternalStorageDirectory(),
        dbname: String = get().configuration.realmFileName
    ) = try {
        val restoredFile = File(directory, dbname)
        val targetFile = File(get().configuration.realmDirectory, dbname)
        restoredFile.copyTo(targetFile, true)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

}