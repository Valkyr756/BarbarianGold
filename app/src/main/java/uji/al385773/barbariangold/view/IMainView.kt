package uji.al385773.barbariangold.view

interface IMainView {
    fun drawMaze()
    fun drawPrincess()
    fun drawMonsters()
    fun normalizeX(eventX: Int): Float
    fun normalizeY(eventY: Int): Float
    fun standardSizeCalculate()
    fun drawCoinsCollected()
    fun drawLives()
    fun drawGameOver()
    fun changeGameOverState(stateGameOver: Boolean)
}