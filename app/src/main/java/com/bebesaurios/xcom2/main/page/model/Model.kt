package com.bebesaurios.xcom2.main.page.model
enum class Rows {
    TitleRow,
    ParagraphRow,
    ImagePushRow
}

interface Model {
    val type: Rows
}

class TitleRow(val title: String) : Model {
    override val type: Rows
        get() = Rows.TitleRow
}

class ParagraphRow(val text: String) : Model {
    override val type: Rows
        get() = Rows.ParagraphRow
}

class ImagePushRow(val text: String, internal val imageUrl: String, val page: String, val clickListener: (ImagePushRow) -> Unit) : Model {
    override val type: Rows
        get() = Rows.ImagePushRow
}