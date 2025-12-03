package fr.kohrs

fun main() {
    val inputs = Utils.getInput(2025, 2, Strategy.ONE_LINE_COMMA_SEPARATED)
    println("Loaded $inputs")
    var sum1 = 0L
    var sum2 = 0L
    for (input in inputs) {
        val (first, last) = input.split("-").map { it.trim().toLong() }
        val sol1Matches = solution1Matches(first, last)
        val sol2Matches = solution2Matches(first, last)
        //println("S1: $input has ${sol1Matches.size} invalid IDs: $sol1Matches")
        println("S2: $input has ${sol2Matches.size} invalid IDs: $sol2Matches")
        sum1 += sol1Matches.sum()
        sum2 += sol2Matches.sum()
    }
    println("Solution 1 : $sum1")
    println("Solution 2 : $sum2")
}

fun solution1Matches(first: Long, second: Long): List<Long> {
    val matches = mutableListOf<Long>()
    for (i in first..second) {
        val str = i.toString()
        if (str.length % 2 != 0) continue
        val part1 = str.substring(0, str.length / 2)
        val part2 = str.substring(str.length / 2)
        if (part1 == part2) {
            matches.add(i)
        }
    }
    return matches
}

fun solution2Matches(first: Long, second: Long): List<Long> {
    val matches = mutableListOf<Long>()
    for (i in first..second) {
        val curr = i.toString()

        for (size in 1..(curr.length / 2)) {
            if (curr.length % size != 0) continue
            val subsrs = curr.windowed(size, size)
            if (subsrs.all { it == subsrs[0] }) {
                matches.add(i)
                break
            }
        }
    }
    return matches
}