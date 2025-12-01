package fr.kohrs

import kotlin.math.abs

fun main() {
    val day1Input = Utils.getInput(2025, 1)
    println("Loaded ${day1Input.size} entries from resources")
    var currNum = 50
    var zeros = 0
    var stopsOnZeros = 0
    println("The dial starts pointing at $currNum")
    day1Input.forEach { it ->
        val direction = it.substring(0, 1)
        val distance = when (direction) {
            "L" -> - it.substring(1).toInt()
            "R" -> it.substring(1).toInt()
            else -> throw IllegalArgumentException("Invalid direction: $direction")
        }

        val newZeros = numberOfTimeItGoesThroughZeroDuringTheMovement(currNum, distance)
        currNum = (currNum + distance).mod(100)
        zeros += newZeros
        if (currNum == 0) stopsOnZeros++
        println("The dial is rotated $it to point at $currNum during this rotation" + if (newZeros > 0) ", it points at 0 $newZeros times." else ".")
    }
    println("Password Problem 1: $stopsOnZeros")
    println("Password Problem 2: $zeros")
}

fun numberOfTimeItGoesThroughZeroDuringTheMovement(currentNumber: Int, movement: Int): Int {
    if (movement == 0) return 0

    val steps = abs(movement)

    val firstSteps = if (movement > 0) {
        if (currentNumber == 0) 100 else 100 - currentNumber
    } else {
        if (currentNumber == 0) 100 else currentNumber
    }

    if (steps < firstSteps) return 0

    return 1 + (steps - firstSteps) / 100
}