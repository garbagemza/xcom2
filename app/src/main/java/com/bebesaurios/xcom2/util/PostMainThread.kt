package com.bebesaurios.xcom2.util

import android.os.Handler
import android.os.Looper
import androidx.annotation.WorkerThread

@WorkerThread
fun postMainThread(function: () -> Unit) {
    val mainHandler = Handler(Looper.getMainLooper())
    val myRunnable = Runnable {
        function.invoke()
    }
    mainHandler.post(myRunnable)
}
