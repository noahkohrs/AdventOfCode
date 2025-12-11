package fr.kohrs

object Utils {

    fun getInput(year: Int, day: Int, strategy: Strategy = Strategy.LINES_SPACED): List<String> {
        val fileName = "/${year}_${day}.txt"
        val input = object {}.javaClass.getResource(fileName)?.readText()
            ?: throw IllegalArgumentException("Input file $fileName not found in resources")
        return when (strategy) {
            Strategy.LINES_SPACED -> input.lines()
            Strategy.ONE_LINE_COMMA_SEPARATED -> input.split(",").map { it.trim() }.filter { it.isNotBlank() }
        }
    }
}

enum class Strategy {
    LINES_SPACED,
    ONE_LINE_COMMA_SEPARATED,
}