package xyz.miieev.gameengine

data class Color(
    val red: Float,
    val green: Float,
    val blue: Float,
    val alpha: Float
) {
    init {
        require(red in 0f..1f) { "Red must be between 0.0 and 1.0" }
        require(green in 0f..1f) { "Green must be between 0.0 and 1.0" }
        require(blue in 0f..1f) { "Blue must be between 0.0 and 1.0" }
        require(alpha in 0f..1f) { "Alpha must be between 0.0 and 1.0" }
    }

    constructor(red: Int, green: Int, blue: Int, alpha: Int = 255) : this(
        red.coerceIn(0, 255) / 255f,
        green.coerceIn(0, 255) / 255f,
        blue.coerceIn(0, 255) / 255f,
        alpha.coerceIn(0, 255) / 255f
    )

    fun darken(amount: Float): Color {
        val factor = 1 - (amount / 100).coerceIn(0.0f, 1.0f)
        return Color(red * factor, green * factor, blue * factor, alpha)
    }

    fun lighten(amount: Float): Color {
        val factor = 1 + (amount / 100).coerceIn(0.0f, 1.0f)
        return Color(
            (red * factor).coerceIn(0.0f, 1.0f),
            (green * factor).coerceIn(0.0f, 1.0f),
            (blue * factor).coerceIn(0.0f, 1.0f),
            alpha
        )
    }

    fun toOpenGL(): FloatArray = floatArrayOf(red, green, blue, alpha)

    companion object {
        fun fromPercent(redPercent: Int, greenPercent: Int, bluePercent: Int, alphaPercent: Int = 100): Color {
            return Color(
                redPercent.coerceIn(0, 100) / 100f,
                greenPercent.coerceIn(0, 100) / 100f,
                bluePercent.coerceIn(0, 100) / 100f,
                alphaPercent.coerceIn(0, 100) / 100f
            )
        }
    }
}