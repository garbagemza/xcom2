package com.bebesaurios.xcom2.util

/**
 * Can be used to force a when statement to be exhaustive, to make sure that the class will not compile
 * unless all cases in the when statement are handled.
 *
 * example: when(x){}.exhaustive will not compile unless all possible values of x are handled
 *
 */
val <T> T.exhaustive: T
    get() = this