package com.bebesaurios.xcom2.bootstrap

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bebesaurios.xcom2.main.MainActivity
import com.bebesaurios.xcom2.R
import com.bebesaurios.xcom2.util.exhaustive
import kotlinx.android.synthetic.main.bootstrap_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BootstrapActivity : AppCompatActivity() {
    private val bootstrapViewModel by viewModel<BootstrapViewModel> { parametersOf() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bootstrap_activity)
        subscribe()
        bootstrapViewModel.handle(InputAction.CheckData)
    }

    private fun subscribe() {
        bootstrapViewModel.configurationReply().observe(this, {
            it?.let {action ->
                when (action) {
                    is ConfigurationReply.Loading -> statusText.setText(R.string.checking_updates)
                    is ConfigurationReply.Error -> statusText.setText(R.string.unable_to_download_content_try_again_later)
                    is ConfigurationReply.Done -> {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }.exhaustive
            }
        })
    }
}