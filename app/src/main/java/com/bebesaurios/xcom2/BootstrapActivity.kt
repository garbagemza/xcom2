package com.bebesaurios.xcom2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bebesaurios.xcom2.bootstrap.BootstrapViewModel
import com.bebesaurios.xcom2.bootstrap.InputAction
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BootstrapActivity : AppCompatActivity() {
    val bootstrapViewModel by viewModel<BootstrapViewModel> { parametersOf() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bootstrap_activity)
        subscribe()
        bootstrapViewModel.handle(InputAction.CheckData)
    }

    private fun subscribe() {

    }
}