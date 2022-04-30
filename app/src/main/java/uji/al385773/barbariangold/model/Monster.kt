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
                direction = direction.opposite()

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

        //Modo 1
        /*val directionsList = arrayListOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)

        if (maze[position].hasWall(Direction.RIGHT) || direction == Direction.RIGHT.opposite()) //
            directionsList.removeAt(3)
        if (maze[position].hasWall(Direction.LEFT) || direction == Direction.LEFT.opposite())
            directionsList.removeAt(2)
        if (maze[position].hasWall(Direction.DOWN) || direction == Direction.DOWN.opposite())
            directionsList.removeAt(1)
        if (maze[position].hasWall(Direction.UP) || direction == Direction.UP.opposite())
            directionsList.removeAt(0)

        val randomIndex = Random.nextInt(directionsList.size)
        return directionsList[randomIndex]*/
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