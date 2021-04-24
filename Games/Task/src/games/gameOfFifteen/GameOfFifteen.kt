package games.gameOfFifteen

import board.Cell
import board.Direction
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game = GameOfFifteen(initializer)


class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        val values = initializer.initialPermutation

        var a = 0
        repeat(board.width) {i->
            repeat(board.width) { j ->
                if (a < values.size)
                    board[board.getCell(i+1, j+1)] = values[a]
                a++
            }
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }

    override fun canMove() = true

    override fun hasWon(): Boolean {
        var won = true
        var a = 1
        for (i in 1..board.width) {
            (1..board.width).forEach { j ->
                if (get(i, j) != a && a != board.width * board.width) {
                    won = false
                }
                a++
            }
        }
        return won && get(board.width, board.width) == null
    }

    override fun processMove(direction: Direction) {
        board.run {
            val blank = find { it == null }
            blank?.let {cell->
                cell.getNeighbour(direction.reversed())?.let {
                    board[cell] = board[it]
                    board[it] = null
                }
            }
        }
    }
}


