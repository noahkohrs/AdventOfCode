package fr.kohrs

object Utils {

    fun getInput(year: Int, day: Int): List<String> {
        val fileName = "/${year}_${day}.txt"
        val input = object {}.javaClass.getResource(fileName)?.readText()
            ?: throw IllegalArgumentException("Input file $fileName not found in resources")
        return input.lines()
    }
}