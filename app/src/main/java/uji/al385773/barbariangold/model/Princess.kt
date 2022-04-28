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
    var isDead: Boolean = false
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
                    invencibilityTime = 5f
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
            invencibilityTime-=deltaTime
            if(invencibilityTime<=0f){
                hasPotion = false
            }
        }

        if(isDead){
            deadTime-=deltaTime
            if(deadTime<=0)
            {
                isDead = false
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
        isDead = false
    }

    fun death() {
        position = maze.origin
        coorX = position.col + 0.5f
        coorY = position.row + 0.5f
        moving = false
        hasPotion = false
        lives = lives - 1
        isDead = true
        deadTime = 1f

    }
}