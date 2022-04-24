package uji.al385773.barbariangold.view

interface IMainView {
    fun drawMaze()
    fun drawPrincess()
    fun rowToY(row: Int): Float
    fun colToX(col: Int): Float
    fun normalizeX(eventX: Int): Float
    fun normalizeY(eventY: Int): Float
}