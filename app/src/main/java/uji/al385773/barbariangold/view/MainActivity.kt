package uji.al385773.barbariangold.view

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics
import uji.al385773.barbariangold.controller.Controller
import uji.al385773.barbariangold.model.CellType
import uji.al385773.barbariangold.model.Model
import kotlin.math.min

class MainActivity : GameActivity(), IMainView {

    companion object {
        private const val BACKGROUND_COLOR = Color.GRAY
    }

    private var xOffset = 0f
    private var yOffset = 0f
    private var standardSize = 0f

    private var width = 0
    private var height = 0

    private lateinit var graphics: Graphics
    private val model = Model()
    private val maze
        get() = model.maze
    private val mazeRows
        get() = maze.nRows
    private val mazeCols
        get() = maze.nCols
    private val controller = Controller(model, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        landscapeFullScreenOnCreate()

    }

    //override fun getGameView(): GameView = findViewById(R.id.gameView)

    override fun onBitmapMeasuresAvailable(width: Int, height: Int) {
        this.width = width
        this.height = height

        standardSize = min((this.width/mazeCols), (this.height/mazeRows)).toFloat()
        xOffset = (this.width - mazeCols * standardSize)/2
        yOffset = (this.height - mazeRows * standardSize)/2

        graphics = Graphics(width, height)
    }

    override fun onDrawingRequested(): Bitmap {
        graphics.clear(BACKGROUND_COLOR)
        drawMaze()
        drawPrincess()
        drawMonsters()

        return graphics.frameBuffer
    }

    override fun buildGameController() = controller

    private fun rowToY(row: Int): Float = yOffset + row * standardSize

    private fun colToX(col: Int): Float = xOffset + col * standardSize

    private fun mazeXToScreenX(coordX: Float):Float = xOffset + coordX * standardSize

    private fun mazeYToScreenY(coordY: Float):Float = yOffset + coordY * standardSize

    override fun normalizeX(eventX: Int): Float {
        return eventX/width.toFloat()
    }

    override fun normalizeY(eventY: Int): Float {
        return eventY/height.toFloat()
    }

    override fun drawMaze() {
        for (row in 0 until mazeRows){
            for (col in 0 until mazeCols){
                val cell = maze[row, col]
                when(cell.type){
                    CellType.POTION -> graphics.drawCircle(colToX(col) + standardSize/2, rowToY(row) + standardSize/2, standardSize/3, Color.GREEN)
                    CellType.GOLD -> if(!maze[row,col].used)
                                        graphics.drawCircle(colToX(col) + standardSize/2, rowToY(row) + standardSize/2, standardSize/10, Color.YELLOW)
                    CellType.DOOR -> graphics.drawRect(colToX(col), rowToY(row), standardSize, standardSize/4, Color.WHITE)
                    CellType.WALL -> graphics.drawRect(colToX(col), rowToY(row), standardSize, standardSize, Color.BLUE)

                }
            }
        }
    }

    override fun drawPrincess() {
        graphics.drawCircle(mazeXToScreenX(model.princess.coorX), mazeYToScreenY(model.princess.coorY), standardSize/2.5f, Color.YELLOW)
    }

    override fun drawMonsters() {
        val arrayColors = arrayOf(Color.CYAN, Color.RED, Color.MAGENTA, Color.WHITE)
        var i : Int = 0
        for (monster in model.arrayMonsters){
            graphics.drawRect(mazeXToScreenX(monster.coorX) - standardSize/2, mazeYToScreenY(monster.coorY) - standardSize/2, standardSize/1.25f, standardSize/1.25f, arrayColors[i])
            i++
        }
    }
}