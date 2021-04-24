package games.gameOfFifteen

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {
        val random = (1..15).shuffled()
        if (!isEven(random)) random.swap()
        random
    }
    private fun <T> List<T>.swap(i: Int = 0, j: Int = 1) :List<T> {
        val result = this.toMutableList()
        val a = result[i]
        result[i] = result[j]
        result[j] = a
        return result
    }
}

