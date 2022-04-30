package uji.al385773.barbariangold.model

import android.util.Log
import java.lang.StringBuilder

/**
 * An enumeration for the types of [cells][Cell] in a [Maze].
 */
enum class CellType {
    POTION, GOLD, EMPTY, HOME, DOOR, ORIGIN, WALL
}

/**
 * A data class for representing the cells of the [Maze].
 */
data class Cell(val type: CellType, var used: Boolean, val walls: Int) {
    /**
     * Check whether there is a wall in the given [direction].
     *
     * Returns true if there is a wall in the direction, false otherwise.
     * The cells in the borders do *not* have walls outsize the [Maze].
     */
    fun hasWall(direction: Direction) = walls.hasWall(direction)
}

private fun Int.setWall(direction: Direction): Int =
    this or (1 shl direction.ordinal)

private fun Int.hasWall(direction: Direction): Boolean =
    this and (1 shl direction.ordinal) != 0

/**
 * A class for representing mazes.
 *
 * @author Juan Miguel Vilar Torres (jvilar@uji.es)
 */
class Maze(diagram: Array<String>) {
    private val cells: Array<Array<Cell>>

    /**
     * The [Position] of the origin.
     */
    val origin: Position

    /**
     * The positions of the enemies.
     */
    val enemyOrigins: List<Position>
        get() = enemies

    private val enemies = ArrayList<Position>()

    /**
     * The total gold
     */

    var gold = 0
        private set

    /**
     * The number of rows of the maze.
     */
    val nRows: Int = diagram.size

    /**
     * The number of columns of the maze.
     */
    val nCols: Int = diagram[0].length

    /**
     *
     * @constructor
     *
     * The parameter to the constructor is an array of [String] with
     * an string for each of the rows. The characters specified in the
     * [companion] are used for the different elements. An
     * example maze follows:
     * <pre>
     *      Maze(
     *          arrayOf(
     *          "#############################",
     *          "#...........................#",
     *          "#.##.#.###############.#.##.#",
     *          "#.##P#.####### #######.#P##.#",
     *          "#....#.......# #.......#.##.#",
     *          "####.#######.###.#######....#",
     *          "#  #.#.................#.####",
     *          "####.#.###.###D###.###.#.####",
     *          "#......# #.#HH HH#.# #......#",
     *          "#.#.##.###.#######.###.##.#.#",
     *          "#.#.##.................##.#.#",
     *          "#.#.##.#######.#######.##.#.#",
     *          "#.#........#.....#..........#",
     *          "#P########.#.###.#.########P#",
     *          "#.########.#.###.#.########.#",
     *          "#.............O.............#",
     *          "#############################",
     *          )
     *      )
     * </pre>
     * @param diagram a schematic representation of the maze.
     */
    init {
        cells = Array(nRows) { Array(nCols) { Cell(CellType.EMPTY, false, 0) } }
        var origin = Position(0, 0)

        for (row in 0 until nRows) {
            val previous = diagram[if (row > 0) row - 1 else row]
            val current = diagram[row]
            val next = diagram[if (row < nRows - 1) row + 1 else row]
            for (col in 0 until nCols) {
                var walls = 0
                if (row > 0 && previous[col] == WALL) walls = walls.setWall(Direction.UP)
                if (row < nRows - 1 && next[col] == WALL) walls = walls.setWall(Direction.DOWN)
                if (col > 0 && current[col - 1] == WALL) walls = walls.setWall(Direction.LEFT)
                if (col < nCols - 1 && current[col + 1] == WALL) walls =
                    walls.setWall(Direction.RIGHT)

                cells[row][col] = Cell(
                    when (current[col]) {
                        ORIGIN -> CellType.ORIGIN.also { origin = Position(row, col) }
                        POTION -> CellType.POTION
                        HOME -> CellType.HOME.also { enemies.add(Position(row, col)) }
                        DOOR -> CellType.DOOR
                        GOLD -> CellType.GOLD.also { gold++ }
                        WALL -> CellType.WALL
                        else -> CellType.EMPTY
                    },
                    false,
                    walls
                )
            }
        }
        this.origin = origin
        Log.i("MAZE", this.toString())
    }

    /**
     * The cell at a given [position].
     */
    operator fun get(position: Position): Cell = cells[position.row][position.col]

    /**
     * The cell at the given [row] and [col].
     */
    operator fun get(row: Int, col: Int): Cell = cells[row][col]

    /**
     * The representation of the [Maze] as a [String].
     */
    override fun toString(): String {
        val position = Position(0, 0)
        return StringBuilder().run {
            for (row in 0 until nRows) {
                for (col in 0 until nCols) {
                    position.col = col
                    append(
                        when (cells[row][col].type) {
                            CellType.POTION -> POTION
                            CellType.GOLD -> GOLD
                            CellType.EMPTY -> EMPTY
                            CellType.HOME -> HOME
                            CellType.DOOR -> DOOR
                            CellType.ORIGIN -> ORIGIN
                            CellType.WALL -> WALL
                        }
                    )
                }
                append('\n')
            }
            toString()
        }
    }

    /**
     * Check for the existence of a wall in the given [Direction] in
     * a position determined by a row and a column.
     * @param row the row.
     * @param col the column.
     * @param direction the direction.
     * @return `true` if there is a wall.
     */
    fun hasWall(row: Int, col: Int, direction: Direction) = get(row, col).hasWall(direction)

    /**
     * Check for the existence of a wall in a given [Position] and [Direction].
     * @param position the position
     * @param direction the direction
     * @return `true` if there is a wall.
     */
    fun hasWall(position: Position, direction: Direction) =
        hasWall(position.row, position.col, direction)

    /**
     * Mark all cells as not used.
     */
    fun reset() {
        for (row in cells)
            for (cell in row)
                cell.used = false
    }

    companion object {
        /**
         * The [char] used for representing the origin.
         */
        const val ORIGIN = 'O'

        /**
         * The [char] used for representing the potions.
         */
        const val POTION = 'P'

        /**
         * The [char] used for representing the walls.
         */
        const val WALL = '#'

        /**
         * The [char] used for representing the home places for enemies.
         */
        const val HOME = 'H'

        /**
         * The [char] used for representing the gold coins.
         */
        const val GOLD = '.'

        /**
         * The [char] used for representing empty cells.
         */
        const val EMPTY = ' '

        /**
         * The [char] used for representing the door.
         */
        const val DOOR = 'D'

    }
}