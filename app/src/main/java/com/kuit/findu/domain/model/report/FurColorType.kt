package com.kuit.findu.domain.model.report

enum class FurColorType(
    val color: String,
    val code: Long?,
) {
    BLACK("검은색", 0xFF111111),
    YELLOW("노란색", 0xFFFCD502),
    SPOTTED("점박이", null),
    WHITE("하얀색", 0xFFFFFFFF),
    BROWN("갈색", 0xFFA0522D),
    GRAY("회색", 0xFFA0A0A0),
    RED("적색", 0xFFEF4346),
    OTHER("기타", null),
    ;
    companion object {
        fun fromString(value: String): FurColorType = FurColorType.entries.first { it.color == value }
    }
}