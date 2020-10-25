package com.bebesaurios.xcom2.main.page.model

enum class LoadingStatusRows {
    LoadingRow,
    ErrorRow
}

interface LoadingStatus {
    val type: Int
}

class Loading : LoadingStatus {
    override val type: Int
        get() = LoadingStatusRows.LoadingRow.ordinal

}

class Error : LoadingStatus {
    override val type: Int
        get() = LoadingStatusRows.ErrorRow.ordinal

}
