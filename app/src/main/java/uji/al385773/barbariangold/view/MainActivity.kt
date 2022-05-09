package uji.al385773.barbariangold.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics
import uji.al385773.barbariangold.R
import uji.al385773.barbariangold.controller.Controller
import uji.al385773.barbariangold.model.CellType
import uji.al385773.barbariangold.model.Model
import uji.al385773.barbariangold.model.Princess
import kotlin.math.min

class MainActivity : GameActivity(), IMainView, Princess.PrincessSoundPlayer {

    companion object {
        private const val BACKGROUND_COLOR = Color.GRAY
    }

    private var xOffset = 0f
    private var yOffset = 0f
    private var standardSize = 0f
    private var width = 0
    private var height = 0

    private lateinit var graphics: Graphics
    private lateinit var soundPool: SoundPool
    private var walkingId = 0
    private var potionDrinkId = 0
    private var coinTakenId = 0
    private var levelFinishedId = 0
    private var princessDiesId = 0

    var isGameOverScreen = false

    private val model = Model(this)
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
        prepareSoundPool(this)
    }

    private fun prepareSoundPool(context: Context) {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(3)
            .setAudioAttributes(attributes)
            .build()

        walkingId = soundPool.load(context, R.raw.walking_princess, 0)
        potionDrinkId = soundPool.load(context, R.raw.potion_taken, 0)
        coinTakenId = soundPool.load(context, R.raw.coin_sound, 0)
        levelFinishedId = soundPool.load(context, R.raw.game_over, 0)
        princessDiesId = soundPool.load(context, R.raw.princess_death, 0)
    }

    //override fun getGameView(): GameView = findViewById(R.id.gameView)


    override fun onBitmapMeasuresAvailable(width: Int, height: Int) {
        this.width = width
        this.height = height

        standardSizeCalculate()
    }

    override fun standardSizeCalculate() {
        standardSize = min((this.width / mazeCols), (this.height / mazeRows)).toFloat()
        xOffset = (this.width - mazeCols * standardSize) / 2
        yOffset = (this.height - mazeRows * standardSize) / 2

        graphics = Graphics(width, height)
    }


    override fun onDrawingRequested(): Bitmap {
        graphics.clear(BACKGROUND_COLOR)
        drawMaze()
        drawPrincess()
        drawMonsters()
        drawCoinsCollected()
        if(isGameOverScreen) {
            drawGameOver()
        }

        return graphics.frameBuffer
    }

    override fun buildGameController() = controller

    private fun rowToY(row: Int): Float = yOffset + row * standardSize

    private fun colToX(col: Int): Float = xOffset + col * standardSize

    private fun mazeXToScreenX(coordX: Float): Float = xOffset + coordX * standardSize

    private fun mazeYToScreenY(coordY: Float): Float = yOffset + coordY * standardSize

    override fun normalizeX(eventX: Int): Float {
        return eventX / width.toFloat()
    }

    override fun normalizeY(eventY: Int): Float {
        return eventY / height.toFloat()
    }

    override fun drawMaze() {
        for (row in 0 until mazeRows) {
            for (col in 0 until mazeCols) {
                val cell = maze[row, col]
                when (cell.type) {
                    CellType.POTION -> if (!maze[row, col].used)
                        graphics.drawCircle(
                            colToX(col) + standardSize / 2,
                            rowToY(row) + standardSize / 2,
                            standardSize / 3,
                            Color.GREEN
                        )
                    CellType.GOLD -> if (!maze[row, col].used)
                        graphics.drawCircle(
                            colToX(col) + standardSize / 2,
                            rowToY(row) + standardSize / 2,
                            standardSize / 10,
                            Color.YELLOW
                        )
                    CellType.DOOR -> graphics.drawRect(
                        colToX(col),
                        rowToY(row),
                        standardSize,
                        standardSize / 4,
                        Color.WHITE
                    )
                    CellType.WALL -> graphics.drawRect(
                        colToX(col),
                        rowToY(row),
                        standardSize,
                        standardSize,
                        Color.BLUE
                    )

                }
            }
        }
    }

    override fun drawPrincess() {
        if (!model.princess.hasPotion) graphics.drawCircle(
            mazeXToScreenX(model.princess.coorX),
            mazeYToScreenY(model.princess.coorY),
            standardSize / 2.5f,
            Color.YELLOW
        )
        else graphics.drawCircle(
            mazeXToScreenX(model.princess.coorX),
            mazeYToScreenY(model.princess.coorY),
            standardSize / 2.5f,
            Color.BLUE
        )
    }
    override fun drawCoinsCollected(){
        var coins = controller.getCoins()
        graphics.setTextSize(60)
        graphics.setTextColor(Color.WHITE)
        graphics.setTextAlign(Paint.Align.LEFT)
        graphics.drawText((width/32).toFloat(),(height/8).toFloat(), coins.toString())

    }

    override fun drawGameOver(){
        graphics.drawRect((width/2).toFloat() - (width/3), (height/2).toFloat() - height/4 ,(width/1.5).toFloat(), (height/2).toFloat(),Color.BLACK)
        graphics.setTextSize(60)
        graphics.setTextAlign(Paint.Align.CENTER)
        graphics.setTextColor(Color.WHITE)
        graphics.drawText((width/2).toFloat(), (height/2).toFloat(), "GAME OVER")
        graphics.setTextSize(40)
        graphics.setTextColor(Color.CYAN)
        graphics.drawText((width/2).toFloat(), (height/2).toFloat() + height/8, "Press to start")

    }

    override fun drawMonsters() {
        val arrayColors = arrayOf(Color.CYAN, Color.RED, Color.MAGENTA, Color.WHITE)
        var i: Int = 0
        for (monster in model.arrayMonsters) {
            graphics.drawRect(
                mazeXToScreenX(monster.coorX) - standardSize / 2.5f,
                mazeYToScreenY(monster.coorY) - standardSize / 2.5f,
                standardSize / 1.25f,
                standardSize / 1.25f,
                arrayColors[i]
            )
            i++
        }
    }


    override fun playCoin() {
        soundPool.play(coinTakenId, 0.6f, 0.8f, 0, 0, 1f)
    }

    override fun playPotion() {
        soundPool.play(potionDrinkId, 0.6f, 0.8f, 0, 0, 1f)
    }
    var idWalkingLoop = 0

    override fun playWalk() {
        idWalkingLoop = soundPool.play(walkingId, 0.6f, 0.8f, 0, -1, 1f)

    }

    override fun stopWalk() {
        soundPool.stop(idWalkingLoop)
    }

    override fun deathPlay() {
        soundPool.play(princessDiesId, 0.6f, 0.8f, 0, 0, 1f)
    }

    override fun gameOverPlay() {
        soundPool.play(levelFinishedId, 0.6f, 0.8f, 0, 0, 1f)
    }

    override fun changeGameOverState(stateGameOver: Boolean) {
        isGameOverScreen = stateGameOver
    }
}