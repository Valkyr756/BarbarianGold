package uji.al385773.barbariangold.view

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController
import uji.al385773.barbariangold.R

class MainActivity : GameActivity() {

    companion object {
        private const val BACKGROUND_COLOR = Color.LTGRAY
    }
    lateinit var graphics: Graphics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBitmapMeasuresAvailable(width: Int, height: Int) {

    }

    override fun onDrawingRequested(): Bitmap {
        graphics.clear(BACKGROUND_COLOR)
        graphics.drawRect(2f, 2f, 2f, 2f, 5)
        return graphics.frameBuffer
    }

    override fun buildGameController(): IGameController {
        TODO("Not yet implemented")
    }
}