package uji.al385773.barbariangold.model

import kotlin.math.roundToInt

class Princess(var princessPosition: Position, mazeOG: Maze) {
    var maze: Maze = mazeOG
    private val princessSpeed: Float = 1f
    var princessPos = maze.origin
    var coorX = princessPos.col + 0.5f
    var coorY = princessPos.row + 0.5f
    var direction = Direction.UP
    var nextDirection = Direction.UP
    var moving = false

    fun updatePrincess(deltaTime: Float){
        if(moving) {
            coorX += princessSpeed * deltaTime * direction.col
            coorY += princessSpeed * deltaTime * direction.row
            val newPos = Position((coorY-0.5f).roundToInt(), (coorX-0.5f).roundToInt())
            if(newPos != princessPos){
                if(maze[newPos].type == CellType.DOOR || maze[newPos].type == CellType.WALL){
                    moving = false
                    toCenter()
                }
                else{
                    princessPos = newPos
                }
            }
        }
        if (direction != nextDirection && !maze[princessPos].hasWall(nextDirection) && maze[princessPos.translate(nextDirection)].type != CellType.DOOR) {
            toCenter()
            direction = nextDirection
            moving = true
        }
    }
    fun toCenter() {
        coorX = princessPos.col + 0.5f
        coorY = princessPos.row + 0.5f
    }


}