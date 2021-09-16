package ru.irinavb.sensors.util

import android.content.Context
import android.content.Context.MODE_APPEND
import android.util.Log
import java.io.*

class Util {
    companion object {
        fun writeToInternalStorage(context: Context, file: String, text: String) {
            BufferedWriter(
                OutputStreamWriter(context.openFileOutput(file, MODE_APPEND))
            ).use { bufferedWriter ->
                bufferedWriter.write(
                    text
                )
            }
        }

        @Throws(IOException::class)
        fun writeToExternalStorage(
            context: Context,
            fileName: String?, text: String?
        ) {
            val externalFilesDir = File(context.getExternalFilesDir(null), fileName)
            Log.d("FilePath", externalFilesDir.absolutePath)
            BufferedWriter(FileWriter(externalFilesDir)).use { writer -> writer.write(text) }
        }
    }
}