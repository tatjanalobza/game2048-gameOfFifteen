package board

import board.Direction.*

lateinit var cells:List<Cell>

open class SquareBoardImpl(override val width: Int) : SquareBoard {
    init{
        cells = (1 .. width).flatMap { i ->
            (1 .. width).map{ j ->
                Cell(i, j)
            }
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? =
        when {
            i > width || j > width || i == 0 || j == 0 -> null
            else -> getCell(i, j)
        }

    override fun getCell(i: Int, j: Int): Cell {
        return cells.first { cell ->
            cell.i == i && cell.j == j
        }
    }

    override fun getAllCells(): Collection<Cell> {
        return IntRange(1, width)
            .flatMap { i: Int -> IntRange(1, width)
                .map{ j:Int -> getCell(i, j) } }
            .toList()
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> =
        when {
            iRange.last > width -> IntRange(iRange.first, width).map { i: Int -> getCell(i, j) }.toList()
            else -> iRange.map { i: Int -> getCell(i, j) }.toList()
        }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> =
        when {
            jRange.last > width -> IntRange(jRange.first, width).map { j: Int -> getCell(i, j) }.toList()
            else -> jRange.map { j: Int -> getCell(i, j) }.toList()
        }

    override fun Cell.getNeighbour(direction: Direction): Cell? =
        when (direction) {
            UP -> getCellOrNull(i - 1, j)
            LEFT -> getCellOrNull(i, j - 1)
            DOWN -> getCellOrNull(i + 1, j)
            RIGHT -> getCellOrNull(i, j + 1)
        }
}

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

class GameBoardImpl<T>(width: Int) : GameBoard<T>, SquareBoardImpl(width) {
    private val cellValues: MutableMap<Cell, T?> = cells.map{
        it to null
    }.toMap().toMutableMap()

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return cellValues.filterValues(predicate).keys
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return cellValues.filterValues(predicate).count() > 0
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return cellValues.filterValues(predicate).count() == cellValues.count()
    }

    override fun get(cell: Cell): T? {
        return cellValues[cell]
    }

    override fun set(cell: Cell, value: T?) {
        cellValues[cell] = value
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return cellValues.filterValues(predicate).keys.firstOrNull()
    }

}



