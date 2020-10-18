package com.bebesaurios.xcom2.service

open class CommonService {

    enum class ServiceCallError(s: String) {
        Unknown("Unknown error.")
    }

    internal fun wrapException(error: ServiceCallError) : Exception {
        return Exception(error.toString())
    }

}