package uji.al385773.barbariangold.model

import kotlin.math.roundToInt
import kotlin.random.Random

class Monster(var maze: Maze, var nMonster: Int) {
    private val monsterSpeed: Float = 1.25f
    private var position: Position = maze.enemyOrigins[nMonster]
    var coorX = position.col + 0.5f
    var coorY = position.row + 0.5f
    var direction = Direction.UP

    init {
        direction = fixDirection(maze)
    }

    fun update(deltaTime: Float) {

        coorX += monsterSpeed * deltaTime * direction.col
        coorY += monsterSpeed * deltaTime * direction.row

        val newPos = Position((coorY - 0.5f).roundToInt(), (coorX - 0.5f).roundToInt())
        if (newPos != position) {
            if (maze[newPos].type == CellType.WALL) {
                toCenter()
                direction = fixDirection(maze)

            } else {
                position = newPos
                if (!maze[position].hasWall(direction.turnRight()) || !maze[position].hasWall(
                        direction.turnLeft()
                    )
                ) {
                    val newDirection = fixDirection(maze)
                    if (direction != newDirection) {
                        toCenter()
                        direction = newDirection
                    }
                }
            }
        }


    }

    private fun fixDirection(maze: Maze): Direction {

        val possible = ArrayList<Direction>()
        for (possibleDirection in Direction.values()) {
            if (!maze[position].hasWall(possibleDirection) && possibleDirection != direction.opposite())
                possible.add(possibleDirection)
        }
        if (possible.isEmpty()) return direction.opposite()
        return possible.random()


    }

    private fun toCenter() {
        coorX = position.col + 0.5f
        coorY = position.row + 0.5f
    }

    fun reset() {
        position = maze.enemyOrigins[nMonster]
        coorX = position.col + 0.5f
        coorY = position.row + 0.5f

    }
}