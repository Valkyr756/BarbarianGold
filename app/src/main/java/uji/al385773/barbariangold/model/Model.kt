package uji.al385773.barbariangold.model

import uji.al385773.barbariangold.controller.GestureDetector
import kotlin.math.roundToInt

class Model {

    var maze: Maze = Levels.all[0]
        private set
    var princess: Princess = Princess(maze.origin, maze)


    fun update(deltaTime: Float) {
        princess.updatePrincess(deltaTime)

    }

    fun changeDirection(direction: Direction) {
        princess.changeDirection(direction)
    }
    /*fun changeDirection(direction: Direction) {
        if(!princess.moving){
            princess.direction = direction
            princess.moving = true
        }
        else{
            if(direction != princess.direction){
                if(direction == princess.direction.opposite()){princess.direction = direction}
                else{
                    if(!maze[princess.princessPos].hasWall(direction)){
                        princess.toCenter()
                        princess.direction = direction
                    }
                }
            }
        }
        princess.nextDirection = direction

    }*/
}