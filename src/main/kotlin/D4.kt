package fr.kohrs

enum class Content {
    EMPTY,
    ROLL,
}

class Map(val grid: List<List<Content>>) {

    val width: Int = grid[0].size
    val height: Int = grid.size

    fun getNeignboorsCount(x: Int, y: Int): Int {
        var count = 0
        for (dy in -1..1) {
            for (dx in -1..1) {
                if (dx == 0 && dy == 0) continue
                val nx = x + dx
                val ny = y + dy
                if (nx in 0 until width && ny in 0 until height) {
                    if (grid[ny][nx] == Content.ROLL) {
                        count++
                    }
                }
            }
        }
        return count
    }

    // Print
    fun printMap(crosses: Set<Pair<Int, Int>> = emptySet()) {
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (crosses.contains(Pair(x, y))) {
                    print("x")
                    continue
                }
                when (grid[y][x]) {
                    Content.EMPTY -> print(".")
                    Content.ROLL -> print("@")
                }
            }
            println()
        }
    }

    fun removeRolls(positions: Set<Pair<Int, Int>>) = Map(grid.mapIndexed { y, row ->
            row.mapIndexed { x, content ->
                if (positions.contains(Pair(x, y))) {
                    Content.EMPTY
                } else {
                    content
                }
            }
        })
}

fun main() {
    val inputs = Utils.getInput(2025, 4)
    val grid = inputs.map { line ->
        line.map { char ->
            when (char) {
                '.' -> Content.EMPTY
                '@' -> Content.ROLL
                else -> throw IllegalArgumentException("Invalid character in map: $char")
            }
        }
    }
    val map = Map(grid)
    pb1(map)
    pb2(map)
}

fun pb2(map: Map) {
    var map = map
    var totalRemoved = 0
    while (true) {
        val toRemove = mutableSetOf<Pair<Int, Int>>()
        for (y in 0 until map.height) {
            for (x in 0 until map.width) {
                if (map.grid[y][x] == Content.ROLL) {
                    val neignboors = map.getNeignboorsCount(x, y)
                    if (neignboors < 4) {
                        toRemove.add(Pair(x, y))
                    }
                }
            }
        }
        if (toRemove.isEmpty()) {
            break
        }
        totalRemoved += toRemove.size
        // println("Remove $totalRemoved rolls of paper:")
        // map.printMap(toRemove)
        map = map.removeRolls(toRemove)
    }
    println("Solution 2: Final total removed: $totalRemoved")
}

fun pb1(map: Map) {
    // map.printMap()
    val forkLifts = mutableSetOf<Pair<Int, Int>>()
    for (y in 0 until map.height) {
        for (x in 0 until map.width) {
            if (map.grid[y][x] == Content.ROLL) {
                val neignboors = map.getNeignboorsCount(x, y)
                if (neignboors < 4) {
                    forkLifts.add(Pair(x, y))
                }
            }
        }
    }
    // map.printMap(forkLifts)
    println("Solution 1: Forklift access points: ${forkLifts.size}")
}