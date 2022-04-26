package uji.al385773.barbariangold.model

import kotlin.math.roundToInt

class Monster(mazeOG: Maze) {
    var maze: Maze = mazeOG
    private val monsterSpeed: Float = 0.9f //provisional idk
    var monsterPos: Position = TODO()
    var coorX = monsterPos.col + 0.5f
    var coorY = monsterPos.row + 0.5f
    var direction = Direction.UP
    var nextDirection = Direction.UP

    fun updateMonster(deltaTime: Float){
        //if(moving) { SIEMPRE SE MUEVEN
            coorX += monsterSpeed * deltaTime * direction.col
            coorY += monsterSpeed * deltaTime * direction.row
            val newPos = Position((coorY-0.5f).roundToInt(), (coorX-0.5f).roundToInt())
            if(newPos != monsterPos){
                if(maze[newPos].type == CellType.WALL){
                    //IMAGINO QUE SERÁ CAMBIAR DIRECCIÓN A OTRA QUE NO TENGA PARED
                    toCenter()
                }
                else{
                    monsterPos = newPos
                }
            }
       // }


        if (direction != nextDirection && !maze[monsterPos].hasWall(nextDirection)) {
            toCenter()
            direction = nextDirection
            //moving = true
        }
    }
    fun toCenter() {
        coorX = monsterPos.col + 0.5f
        coorY = monsterPos.row + 0.5f
    }
}