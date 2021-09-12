package ru.irinavb.sensors

import android.content.Context
import android.content.Context.MODE_APPEND
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.StandardCharsets

class Util {
    companion object {
        fun writeToInternalStorage(context: Context, file: String, text: String) {
            var fos: FileOutputStream? = null
            try {
                fos = context.openFileOutput(file, MODE_APPEND)
                fos?.write(text.toByteArray(StandardCharsets.UTF_8))
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (fos != null) {
                    try {
                        fos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}