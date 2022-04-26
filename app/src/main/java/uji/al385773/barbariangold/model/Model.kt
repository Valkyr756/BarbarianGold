package uji.al385773.barbariangold.model

import uji.al385773.barbariangold.controller.GestureDetector

class Model {

    var maze: Maze = Levels.all[0]
        private set
    private val princessSpeed: Float = 1f
    var princessPos: Position = maze.origin
    var coorX = princessPos.col + 0.5f
    var coorY = princessPos.row + 0.5f
    private var direction = GestureDetector().direction

    fun update(deltaTime: Float, nextDirection: Direction) {
        coorX += princessSpeed * deltaTime * direction.col
        coorY += princessSpeed * deltaTime * direction.row

        /*if (direction != nextDirection && !maze[princessPos].hasWall(nextDirection) && maze[princessPos.translate(nextDirection) != CellType.DOOR) {
            toCenter()
            direction = nextDirection
        }*/
    }

    fun toCenter() {
        coorX = princessPos.col + 0.5f
        coorY = princessPos.row + 0.5f
    }
}