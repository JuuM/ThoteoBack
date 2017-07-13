package fr.etna.thoteoback.controller.souvenir;

import fr.etna.thoteoback.controller.Controller;
import io.vertx.rxjava.ext.web.Router;

public class SouvenirController implements Controller {

    public SouvenirController() { super(); }
    @Override
    public void launchController(Router rest)
    {
        /*rest.get("/souvenir/all").handler(this::getEmotionByUrl);
        rest.get("/souvenir/get/:prop").handler(this::getEmotionByImage);
        System.out.println("mounted LAUNCHCONTROLLER" + this.getClass().getName());*/
    }
}
