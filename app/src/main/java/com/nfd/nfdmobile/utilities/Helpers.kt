package com.nfd.nfdmobile.utilities

import android.app.ActionBar
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Environment
import android.view.View
import android.view.Window
import android.view.WindowManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class Helpers {

    companion object {

        @Suppress("DEPRECATION")
        fun isNetworkAvailable(context: Context): Boolean {
            try {
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                return if (Build.VERSION.SDK_INT > 22) {
                    val an = cm.activeNetwork ?: return false
                    val capabilities = cm.getNetworkCapabilities(an) ?: return false
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                } else {
                    val a = cm.activeNetworkInfo ?: return false
                    a.isConnected && (a.type == ConnectivityManager.TYPE_WIFI || a.type == ConnectivityManager.TYPE_MOBILE)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }

        /* Checks if external storage is available for read and write */
        fun isExternalStorageWritable(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }

        /* Checks if external storage is available to at least read */
        fun isExternalStorageReadable(): Boolean {
            return Environment.getExternalStorageState() in
                    setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
        }

        @Suppress("DEPRECATION")
        fun hideStatusBar(window: Window, actionBar: ActionBar?) {
            if (Build.VERSION.SDK_INT < 16) {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
            } else {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
                // Remember that you should never show the action bar if the
                // status bar is hidden, so hide that too if necessary.
                actionBar?.hide()
            }
        }

        //    fun downloadFile(
        //        dwnload_file_path: String, fileName: String,
        //    ) {
        //        val pathToSave = "nfd_meditations_folder"
        //        var downloadedSize = 0
        //        var totalSize = 0
        //
        //        try {
        //            val url = URL(dwnload_file_path)
        //            val urlConnection = url.openConnection() as HttpURLConnection
        //            urlConnection.requestMethod = "GET"
        //            urlConnection.doOutput = true
        //
        //            // connect
        //            urlConnection.connect()
        //
        //            val myDir: File
        //            myDir = File(pathToSave)
        //            myDir.mkdirs()
        //
        //            // create a new file, to save the downloaded file
        //            val file = File(myDir, fileName)
        //            val fileOutput = FileOutputStream(file)
        //
        //            // Stream used for reading the data from the internet
        //            val inputStream = urlConnection.getInputStream()
        //
        //            // this is the total size of the file which we are downloading
        //            // val totalSize = urlConnection.getContentLength()
        //
        //            // runOnUiThread(new Runnable() {
        //            // public void run() {
        //            // pb.setMax(totalSize);
        //            // }
        //            // });
        //
        //            // create a buffer...
        //            val buffer = ByteArray(1024)
        //            var bufferLength = 0
        //
        //            while ((bufferLength = inputStream.read(buffer)) > 0) {
        //                fileOutput.write(buffer, 0, bufferLength)
        //                downloadedSize += bufferLength
        //                // update the progressbar //
        //                // runOnUiThread(new Runnable() {
        //                // public void run() {
        //                // pb.setProgress(downloadedSize);
        //                // float per = ((float)downloadedSize/totalSize) * 100;
        //                // cur_val.setText("Downloaded " + downloadedSize + "KB / " +
        //                // totalSize + "KB (" + (int)per + "%)" );
        //                // }
        //                // });
        //            }
        //            // close the output stream when complete //
        //            fileOutput.close()
        //            // runOnUiThread(new Runnable() {
        //            // public void run() {
        //            // // pb.dismiss(); // if you want close it..
        //            // }
        //            // });
        //
        //        } catch (e: MalformedURLException) {
        //            // showError("Error : MalformedURLException " + e);
        //            e.printStackTrace()
        //        } catch (e: IOException) {
        //            // showError("Error : IOException " + e);
        //            e.printStackTrace()
        //        } catch (e: Exception) {
        //            // showError("Error : Please check your internet connection " + e);
        //        }
        //
        //    }
    }
}