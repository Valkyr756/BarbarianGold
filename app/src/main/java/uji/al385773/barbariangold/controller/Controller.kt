package uji.al385773.barbariangold.controller

import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler
import uji.al385773.barbariangold.model.Direction
import uji.al385773.barbariangold.model.Model
import uji.al385773.barbariangold.view.IMainView

class Controller(private val model: Model, private val view: IMainView): IGameController {
    private val gestureDetector = GestureDetector()

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>) {

        val dTime =if(deltaTime>0.5)  0.02f else deltaTime

        if(model.isGameOver){
            view.changeGameOverState(true)
        }
        for (event in touchEvents) {
            if (event.type == TouchHandler.TouchType.TOUCH_DOWN)
                gestureDetector.onTouchDown(view.normalizeX(event.x), view.normalizeY(event.y))

            if (event.type == TouchHandler.TouchType.TOUCH_UP) {
                var touchUp = gestureDetector.onTouchUp(view.normalizeX(event.x), view.normalizeY(event.y))
                if (touchUp == GestureDetector.Gestures.SWIPE)
                    model.changeDirection(gestureDetector.direction)
                else if (touchUp == GestureDetector.Gestures.CLICK && model.isGameOver){
                    view.changeGameOverState(false)
                    model.restartGameOver()

                }
            }
        }

        model.update(dTime)

        if(model.mazeChanged) {
            view.standardSizeCalculate()
            model.mazeChanged = false
        }

    }

    fun getCoins(): Int {
        return model.princess.coins
    }

    fun getLives(): Int {
        return model.princess.lives
    }
}