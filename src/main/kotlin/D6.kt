package fr.kohrs

enum class SymbolType {
    ADD,
    MULTIPLY;
}

fun String.toSymbol() = when (this) {
    "+" -> SymbolType.ADD
    "*" -> SymbolType.MULTIPLY
    else -> throw IllegalArgumentException("Invalid symbol: $this")
}

fun <T> List<List<T>>.invertMatrix(): List<List<T>> {
    val inverted = mutableListOf<MutableList<T>>()
    for (col in this[0].indices) {
        val newRow = mutableListOf<T>()
        for (row in this.indices) {
            newRow.add(this[row][col])
        }
        inverted.add(newRow)
    }
    return inverted
}
fun main() {
    val inputs = Utils.getInput(2025, 6)
    val numbers: List<List<Long>> = inputs.subList(0, inputs.size - 1).map { it.split(" ").filter { it.isNotBlank() }.map { it.toLong() } }
    val symbols = inputs.last().split(" ").filter { it.isNotBlank() }.map { it.trim().toSymbol() }
    for (i in numbers) println(i)
    println(symbols)
    // invert numbers matrix
    val invertedNumbers = numbers.invertMatrix()
    println("Inverted:")
    for (i in invertedNumbers) println(i)
    val results = invertedNumbers.mapIndexed { index, nums ->
        compute(nums, symbols[index])
    }
    println("Results: ${results.sum()}")
}

fun compute(numbers: List<Long>, symbolType: SymbolType): Long {
    return when (symbolType) {
        SymbolType.ADD -> numbers.sum()
        SymbolType.MULTIPLY -> numbers.reduce { acc, l -> acc * l }
    }
}