package com.bebesaurios.xcom2.main.page.model
enum class Rows {
    TitleRow,
    ParagraphRow,
    ImagePushRow,
    ImageRow
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

class ImagePushRow(val text: String, internal val imageUrl: String, val page: String) : Model {
    override val type: Rows
        get() = Rows.ImagePushRow
}

class ImageRow(val imageUrl: String) : Model {
    override val type: Rows
        get() = Rows.ImageRow

}