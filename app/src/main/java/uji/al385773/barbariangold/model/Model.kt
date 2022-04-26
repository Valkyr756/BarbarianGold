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
            if(((princess.coorX - monster.coorX)<0.4f) && ((princess.coorY - monster.coorY)<0.4f))
            {
                if(princess.hasPotion)
                {
                    System.out.println("monster killed")
                    monster.reset()
                }
                else {
                    if(princess.lives <= 1) {
                        System.out.println("game over")
                        maze.reset()
                        for(monster in arrayMonsters){
                            monster.reset()
                        }
                        princess.reset()
                    }
                    else {
                        //a lo mejor se necesita un segundo donde no puedas volver a perder vida
                        princess.death()

                        System.out.println("death, "+princess.lives+ " lives left")
                    }

                }
            }
        }



        if(princess.coins == maze.gold){
            System.out.println("victoria")
            maze.reset()
            princess.reset()
            for(monster in arrayMonsters){
                monster.reset()
            }



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