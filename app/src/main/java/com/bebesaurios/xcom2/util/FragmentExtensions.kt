package com.bebesaurios.xcom2.util

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

fun Fragment.replaceFragment(@IdRes res: Int, fragment: Fragment, fragmentTag: String, backStackStateName: String?) {
    parentFragmentManager
        .beginTransaction()
        .replace(res, fragment, fragmentTag)
        .addToBackStack(backStackStateName)
        .commit()
}