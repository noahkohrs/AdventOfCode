package fr.kohrs

fun main() {
    val inputs = Utils.getInput(2025, 3)

    val sol1 = inputs.sumOf { it ->
        val digits = it.toCharArray().map { c -> c.digitToInt() }
        var currMax = 0

        for (i in 0 until digits.size) {
            for (j in i + 1 until digits.size) {
                if (digits[i] * 10 + digits[j] > currMax) {
                    currMax = digits[i] * 10 + digits[j]
                }
            }
        }
        currMax
    }

    val sol2: Long = inputs.sumOf { it ->
        val digits = it.map { c -> c.digitToInt() }
        val toRemove = digits.size - 12
        var removeLeft = toRemove
        val stack = ArrayDeque<Int>()
        for (d in digits) {
            while (removeLeft > 0 && stack.isNotEmpty() && stack.last() < d) {
                stack.removeLast()
                removeLeft--
            }
            stack.addLast(d)
        }
        // If still need to remove, remove from end
        repeat(removeLeft) { stack.removeLast() }
        stack.joinToString("").toLong()
    }
    println("Result: $sol1")
    println("Result: $sol2")
}