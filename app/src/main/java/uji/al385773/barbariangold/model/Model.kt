package uji.al385773.barbariangold.model

import java.lang.Math.abs

class Model(private var soundPlayer: Princess.PrincessSoundPlayer) {

    var level = 0
    var maze: Maze = Levels.all[level]
        private set
    var princess: Princess = Princess(maze, soundPlayer)
    var arrayMonsters: ArrayList<Monster> = fillArrayMonsters()
    var mazeChanged: Boolean = false


    //Function to fill the array of monsters
    private fun fillArrayMonsters(): ArrayList<Monster> {
        val arrayAux = ArrayList<Monster>()
        for (i in maze.enemyOrigins.indices) {
            arrayAux.add(Monster(maze, i))
        }
        return arrayAux
    }

    fun update(deltaTime: Float) {
        princess.update(deltaTime)
        for (monster in arrayMonsters) {
            monster.update(deltaTime)
            if ((abs(princess.coorX - monster.coorX) < 0.4f) && (abs(princess.coorY - monster.coorY) < 0.4f)) {
                if (princess.hasPotion) {
                    System.out.println("monster killed")
                    monster.reset()
                } else {
                    if (princess.lives <= 1) {
                        System.out.println("game over")
                        maze.reset()
                        for (monster in arrayMonsters) {
                            monster.reset()
                        }
                        princess.reset()
                    } else if (!princess.isDead) {
                        //a lo mejor se necesita un segundo donde no puedas volver a perder vida
                        princess.death()
                        for (monster in arrayMonsters) {
                            monster.reset()
                        }

                        System.out.println("death, " + princess.lives + " lives left")
                    }

                }
            }
        }



        if (princess.coins == maze.gold) {
            System.out.println("victoria")
            princess.levelPassedSound()
            if (Levels.all.size - 1 >= level + 1) {
                mazeChanged = true
                level = level + 1
                maze = Levels.all[level]
                princess = Princess(maze, soundPlayer)
                arrayMonsters = fillArrayMonsters()

            }

            System.out.println("no hay más niveles")
            maze.reset()
            princess.reset()
            for (monster in arrayMonsters) {
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