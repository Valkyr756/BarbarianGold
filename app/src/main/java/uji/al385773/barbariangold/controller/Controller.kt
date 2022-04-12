package uji.al385773.barbariangold.controller

import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler
import uji.al385773.barbariangold.model.Model
import uji.al385773.barbariangold.view.IMainView

class Controller(model: Model, view: IMainView): IGameController {
    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {

    }
}