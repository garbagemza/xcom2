package com.bebesaurios.xcom2.util

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.addFragment(@IdRes res: Int, fragment: Fragment, fragmentTag: String) {
    supportFragmentManager
        .beginTransaction()
        .add(res, fragment, fragmentTag)
        .disallowAddToBackStack()
        .commit()
}