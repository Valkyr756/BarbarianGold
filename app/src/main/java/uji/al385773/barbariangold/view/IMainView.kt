package uji.al385773.barbariangold.view

interface IMainView {
    fun drawMaze()
    fun rowToY(row: Int): Float
    fun colToX(col: Int): Float
    fun normalizeX(eventX: Int): Int
    fun normalizeY(eventY: Int): Int
}