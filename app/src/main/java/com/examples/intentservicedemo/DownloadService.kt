package com.examples.intentservicedemo

import android.app.Activity
import android.app.IntentService
import android.content.Intent

class DownloadService : IntentService("DownloadService") {

    private var resultStatus = Activity.RESULT_CANCELED

    companion object {

        val RESULT = "result"
        val SERVICE_BROADCAST_ID = "com.examples.intentservicedemo.receiver"
        val RESULT_MESSAGE = "result_message"
        val DURATION = "wait_duration"
    }

    override fun onHandleIntent(intent: Intent?) {
        var waitDuration: Long = -1

        intent?.let {
            waitDuration = intent.getIntExtra(DURATION, -1).toLong()
        }

        if (waitDuration == -1L) publishResult(resultStatus, "No wait duration!")

        else {

            try {

                Thread.sleep(waitDuration)
                resultStatus = Activity.RESULT_OK
                publishResult(resultStatus, "Success")

            } catch (e: Exception) {
                publishResult(resultStatus, "Exception")
            }
        }
    }

    private fun publishResult(resultStatus: Int, resultMessage: String) {

        val intent = Intent(SERVICE_BROADCAST_ID)
        intent.putExtra(RESULT, resultStatus)
        intent.putExtra(RESULT_MESSAGE, resultMessage)

        sendBroadcast(intent)
    }
}