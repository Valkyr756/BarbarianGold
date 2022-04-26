package uji.al385773.barbariangold.view

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.GameView
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController
import uji.al385773.barbariangold.R
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
        return graphics.frameBuffer
    }

    override fun buildGameController() = controller

    override fun rowToY(row: Int): Float = yOffset + row * standardSize

    override fun colToX(col: Int): Float = xOffset + col * standardSize

    fun wXToSX(coordX:Float):Float = xOffset + coordX * standardSize

    fun wYToSY(coordY:Float):Float = yOffset + coordY * standardSize

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
                    CellType.GOLD -> graphics.drawCircle(colToX(col) + standardSize/2, rowToY(row) + standardSize/2, standardSize/10, Color.YELLOW)
                    CellType.DOOR -> graphics.drawRect(colToX(col), rowToY(row), standardSize, standardSize/4, Color.WHITE)
                    CellType.WALL -> graphics.drawRect(colToX(col), rowToY(row), standardSize, standardSize, Color.BLUE)
                    /*CellType.ORIGIN -> graphics.drawCircle(colToX(col) + standardSize/2, rowToY(row) + standardSize/2, standardSize/2.5f, Color.YELLOW)
                    CellType.HOME -> graphics.drawRect(colToX(col) + standardSize/8, rowToY(row) + standardSize/8, standardSize/1.25f, standardSize/1.25f, Color.RED)
                */}
            }
        }
    }

    override fun drawPrincess() {
        graphics.drawCircle(wXToSX(model.princess.coorX), wYToSY(model.princess.coorY), standardSize/2.5f, Color.YELLOW)
    }
}