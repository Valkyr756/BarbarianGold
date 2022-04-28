package uji.al385773.barbariangold.controller

import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler
import uji.al385773.barbariangold.model.Direction
import uji.al385773.barbariangold.model.Model
import uji.al385773.barbariangold.view.IMainView

class Controller(private val model: Model, private val view: IMainView): IGameController {
    private val gestureDetector = GestureDetector()

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>) {
        val dTime =/* if(deltaTime>0.5)  0.02f else */deltaTime
        for (event in touchEvents) {
            if (event.type == TouchHandler.TouchType.TOUCH_DOWN)
                gestureDetector.onTouchDown(view.normalizeX(event.x), view.normalizeY(event.y))
            if (event.type == TouchHandler.TouchType.TOUCH_UP)
                if (gestureDetector.onTouchUp(view.normalizeX(event.x), view.normalizeY(event.y)) == GestureDetector.Gestures.SWIPE)
                    model.changeDirection(gestureDetector.direction)
        }
        model.update(dTime)
        if(model.mazeChanged) {
            view.standardSizeCalculate()
            model.mazeChanged = false
        }

    }
}