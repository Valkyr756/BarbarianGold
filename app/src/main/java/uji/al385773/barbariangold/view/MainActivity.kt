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
import uji.al385773.barbariangold.model.Model

class MainActivity : GameActivity(), IMainView {

    companion object {
        private const val BACKGROUND_COLOR = Color.LTGRAY
    }

    private var width = 0
    private var height = 0

    lateinit var graphics: Graphics
    val model = Model()
    val controller = Controller(model, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        portraitFullScreenOnCreate()

    }

    //override fun getGameView(): GameView = findViewById(R.id.gameView)

    override fun onBitmapMeasuresAvailable(width: Int, height: Int) {
        this.width = width
        this.height = height

        graphics = Graphics(width, height)
    }

    override fun onDrawingRequested(): Bitmap {
        graphics.clear(BACKGROUND_COLOR)
        graphics.drawRect(150f, 150f, 120f, 200f, Color.RED)
        return graphics.frameBuffer
    }

    override fun buildGameController() = Controller(model, this)
}