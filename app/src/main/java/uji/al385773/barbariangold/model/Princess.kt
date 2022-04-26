package uji.al385773.barbariangold.model

import kotlin.math.roundToInt

class Princess(mazeOG: Maze) {
    var maze: Maze = mazeOG
    private val princessSpeed: Float = 2f
    var position = maze.origin
    var coorX = position.col + 0.5f
    var coorY = position.row + 0.5f
    var direction = Direction.UP
    var nextDirection = Direction.UP
    var moving = false
    var coins : Int = 0

    fun update(deltaTime: Float){
        if(moving) {
            coorX += princessSpeed * deltaTime * direction.col
            coorY += princessSpeed * deltaTime * direction.row

            val newPos = Position((coorY - 0.5f).roundToInt(), (coorX - 0.5f).roundToInt())
            if(newPos != position){
                if(maze[newPos].type == CellType.GOLD && !maze[newPos].used ){
                    maze[newPos].used = true
                    coins++
                }
                else if(maze[newPos].type == CellType.POTION && !maze[newPos].used){
                    maze[newPos].used = true
                    //IMAGINO QUE AQUÍ SERÍA ACTIVAR EL ESTADO DE INVINCIBILITY O LO QUE SEA
                }

                if(maze[newPos].type == CellType.DOOR || maze[newPos].type == CellType.WALL){
                    moving = false
                    toCenter()
                }
                else{
                    position = newPos
                }
            }
        }
        if (direction != nextDirection && !maze[position].hasWall(nextDirection) && maze[position.translate(nextDirection)].type != CellType.DOOR) {
            toCenter()
            direction = nextDirection
            moving = true
        }
    }

    fun changeDirection(direction: Direction) {
        if(!moving){
            this.direction = direction
            moving = true
        }
        else{
            if(direction != this.direction){
                if(direction == this.direction.opposite())
                    this.direction = direction
                else{
                    if(!maze[position].hasWall(direction)){
                        toCenter()
                        this.direction = direction
                    }
                }
            }
        }
        nextDirection = direction
    }

    private fun toCenter() {
        coorX = position.col + 0.5f
        coorY = position.row + 0.5f
    }

    fun reset() {
        coins = 0
        position = maze.origin
        coorX = position.col + 0.5f
        coorY = position.row + 0.5f
        moving = false
    }
}