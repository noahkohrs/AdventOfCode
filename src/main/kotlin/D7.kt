package fr.kohrs

enum class CellType(val symbol: Char) {
    Manifold('S'),
    Empty('.'),
    Splitter('^'),
    Beam('|');

    companion object {
        fun fromSymbol(symbol: Char): CellType {
            return entries.firstOrNull { it.symbol == symbol }
                ?: throw IllegalArgumentException("Invalid cell type symbol: $symbol")
        }
    }
}

data class Board(val cells: List<List<CellType>>) {
    override fun toString(): String = cells.joinToString("\n") { row ->
        row.joinToString("") { cell -> cell.symbol.toString() }
    }

    fun progressLine(): Int {
        return cells.indexOfLast { row -> row.any { cell -> cell in listOf(CellType.Beam, CellType.Manifold) } }
    }

    fun computeLine(line: Int, dimensions: List<Long>): Triple<Board, Int, List<Long>> {
        val updatedCells = mutableListOf<List<CellType>>()
        var splits = 0
        val newDimensions = dimensions.toMutableList()
        for (i in cells.indices) {
            val newRow = cells[i].toMutableList()
            if (i == line) {
                val row = cells[i]
                val previousRow = cells[i - 1]
                for (j in row.indices) {
                    when (previousRow[j] to row[j]) {
                        CellType.Beam to CellType.Splitter -> {
                            splits++
                            newRow[j-1] = CellType.Beam
                            newDimensions[j-1]+=dimensions[j]
                            newRow[j+1] = CellType.Beam
                            newDimensions[j+1]+=dimensions[j]
                            newDimensions[j] -= dimensions[j]
                        }
                        CellType.Beam to CellType.Empty,
                            CellType.Manifold to CellType.Empty -> {
                                newRow[j] = CellType.Beam
                            }
                        else -> { /* noop */ }
                    }
                }
            }
            updatedCells.add(newRow)
        }
        return Triple(Board(updatedCells), splits, newDimensions)
    }
}

fun main() {
    val inputs = Utils.getInput(2025, 7)
    var board = Board(inputs.map { line ->
        line.map { char -> CellType.fromSymbol(char) }
    })
    // Print the board
    println(board)
    var totalSplits = 0
    var lastdimensions = board.cells[0].map { if (it == CellType.Manifold) 1L else 0L }
    while (true) {
        val lineToCompute = board.progressLine()
        if (lineToCompute == board.cells.size - 1) {
            break
        }
        val (newBoard, splits, dimensions) = board.computeLine(lineToCompute + 1, lastdimensions)
        println("After computing line ${lineToCompute + 1}, splits: $splits")
        println(newBoard)
        board = newBoard
        totalSplits += splits
        lastdimensions = dimensions
    }

    println("Total splits: $totalSplits")
    println("Final dimensions: ${lastdimensions.sum()}")
}