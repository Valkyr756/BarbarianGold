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
    var lives = 3
    var hasPotion : Boolean = false
    var invencibilityTime : Float = 0f
    var dead : Boolean = false
    var deadTime : Float = 0f

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
                    hasPotion = true
                    //luego que los monstruos al colisionar o lo que sea comprueben si la princesa tiene la pocion
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

        //Comprueba si tienes la poción y si la tienes aumenta la var invencibilityTime y si ve que ha pasado
        //suficiente tiempo te quita ya el estado de invencibilidad (pone hasPotion a falso otra vez)
        if(hasPotion) {
            invencibilityTime++
            if(invencibilityTime>140f){
                hasPotion = false
                invencibilityTime = 0f
            }
        }

        if(dead){
            deadTime++
            if(deadTime>30f)
            {
                dead = false
                deadTime = 0f
            }
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
        hasPotion = false
        lives = 3
        dead = false
    }

    fun death() {
        position = maze.origin
        coorX = position.col + 0.5f
        coorY = position.row + 0.5f
        moving = false
        hasPotion = false
        lives = lives - 1
        dead = true
    }
}