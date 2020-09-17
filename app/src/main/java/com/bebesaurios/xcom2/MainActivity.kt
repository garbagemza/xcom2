package com.bebesaurios.xcom2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val file = AssetHelper.openResourceAsString(this, "MSI.json")
        Log.i("xcom2", file)
        val database = Database(file)
        val locale = Locale.getDefault()
        Log.i("xcom2", locale.language)
    }
}