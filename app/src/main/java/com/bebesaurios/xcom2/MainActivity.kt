package com.bebesaurios.xcom2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bebesaurios.xcom2.database.DatabaseFeeder
import org.koin.android.ext.android.inject
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))

        val locale = Locale.getDefault()
        Log.i("xcom2", locale.language)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_action -> initSearch()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initSearch() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }
}