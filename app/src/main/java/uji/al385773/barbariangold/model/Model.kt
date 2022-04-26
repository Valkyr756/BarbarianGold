package uji.al385773.barbariangold.model

class Model {

    var maze: Maze = Levels.all[0]
        private set
    var princess: Princess = Princess(maze)
    val arrayMonsters: ArrayList<Monster> = fillArrayMonsters()


    //Function to fill the array of monsters
    private fun fillArrayMonsters(): ArrayList<Monster> {
        val arrayAux = ArrayList<Monster>()
        for (i in maze.enemyOrigins.indices){
            arrayAux.add(Monster(maze, i))
        }
        return arrayAux
    }

    fun update(deltaTime: Float) {
        princess.update(deltaTime)
        for (monster in arrayMonsters){
            monster.update(deltaTime)
        }
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