package ru.irinavb.sensors.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

class FilesUtil {
    public static void writeToInternalStorage(Context context,
                                              String fileName, String text) throws IOException {
        try(BufferedWriter bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_APPEND)))){
            bufferedWriter.write(text);
            //bufferedWriter.newLine();
        }
    }

    public static void writeToExternalStorage(Context context,
                                              String fileName, String text) throws IOException {
        /*File externalFilesDir = new File(context.getExternalFilesDir(null), fileName);
        Log.d("--------external files", externalFilesDir.getAbsolutePath());
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(externalFilesDir))){
            writer.write(text);
        }*/

       /* String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/sensors");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        File file = new File (myDir, fileName);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            writer.write(text);
        }*/
    }
}
