package com.examples.intentservicedemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.DataOutputStream

class MainActivity : AppCompatActivity() {

    private val broadcastReceiver = object: BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            val bundle = intent?.extras

            bundle?.let {
                Toast.makeText(this@MainActivity, it.getString(DownloadService.RESULT_MESSAGE), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindUI()
    }

    private fun bindUI() {

        btnStartService.setOnClickListener {
            val intent = Intent(this, DownloadService::class.java)
            intent.putExtra(DownloadService.DURATION, 5000)

            startService(intent)
            Toast.makeText(this@MainActivity, "Service is doing its work", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(broadcastReceiver, IntentFilter(DownloadService.SERVICE_BROADCAST_ID))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
    }
}
