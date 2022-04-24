package uji.al385773.barbariangold.controller

import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler
import uji.al385773.barbariangold.model.Direction
import uji.al385773.barbariangold.model.Model
import uji.al385773.barbariangold.view.IMainView

class Controller(private val model: Model, private val view: IMainView): IGameController {

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>) {

        val gestureDetector = GestureDetector()

        for (event in touchEvents) {
            if (event.type == TouchHandler.TouchType.TOUCH_DOWN)
                gestureDetector.onTouchDown(view.normalizeX(event.x).toFloat(), view.normalizeY(event.y).toFloat())
            if (event.type == TouchHandler.TouchType.TOUCH_UP)
                when (gestureDetector.onTouchUp(view.normalizeX(event.x).toFloat(), view.normalizeY(event.y).toFloat())){
                    GestureDetector.Gestures.SWIPE -> gestureDetector.direction
                }
        }
        model.update(deltaTime)
    }
}