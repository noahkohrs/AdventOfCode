package fr.kohrs

import kotlin.math.max
import kotlin.math.min

fun main() {
    val inputs: List<String> = Utils.getInput(2025, 5, Strategy.LINES_SPACED)
    val (rangesEntries, _, ingredientsEntries) = inputs.splitAt { it.isBlank() }
    println("Ranges: $rangesEntries")
    println("Ingredients: $ingredientsEntries")
    val ranges = rangesEntries.map {
        val (min, max) = it.split("-").map { range -> range.toLong() }
        min..max
    }
    val ingredients = ingredientsEntries.map { it.toLong() }
    val freshIngredients = ingredients.filter { it -> ranges.any { range -> it in range } }
    println("Fresh ingredients: ${freshIngredients.size}")
    println("Full range size: ${getFullRangesSanatized(ranges).sumOf { it.last - it.first + 1 }}")
}

/**
 * Avoids overlaps
 */
fun getFullRangesSanatized(ranges: List<LongRange>): List<LongRange> {
    val existingRanges = ranges.sortedBy { it.first }
    val sanatizedRanges = mutableListOf<LongRange>()
    for (range in existingRanges) {
        var toAdd = range
        val last = sanatizedRanges.lastOrNull()

        if (last != null && toAdd.first <= last.last) {
            sanatizedRanges.removeLast()
            toAdd = min(last.first, toAdd.first)..max(toAdd.last, last.last)
        }
        sanatizedRanges.add(toAdd)
    }
    return sanatizedRanges
}

fun <E> List<E>.splitAt(predicate: (E) -> Boolean): SplitResult<E> {
    val index = this.indexOfFirst(predicate)
    if (index == -1) throw IllegalArgumentException("Predicate does not match any element in the list")
    val firstPart = this.subList(0, index)
    val matchingElement = this[index]
    val secondPart = this.subList(index + 1, this.size)

    return SplitResult(firstPart, matchingElement, secondPart)
}

data class SplitResult<E>(val firstPart: List<E>, val matchingElement: E, val secondPart: List<E>)