package fr.etna.thoteoback.controller;

import fr.etna.thoteoback.controller.authentication.AuthController;
import fr.etna.thoteoback.controller.emotion.EmotionController;
import fr.etna.thoteoback.controller.face.FaceController;
import fr.etna.thoteoback.controller.upload.UploadController;
import fr.etna.thoteoback.controller.users.UsersController;
import fr.etna.thoteoback.controller.vision.VisionController;
import fr.etna.thoteoback.sqlclient.Client;
import io.vertx.rxjava.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import rx.Observable;

import java.util.ArrayList;

/**
 * Created by juju_ on 27/06/2017.
 */
public class WebController
{
    static Router router = null;
    static ArrayList<Controller> listController = new ArrayList<>();

    public static void initController(Router restAPI)
    {
        restAPI.get("/verify").handler(context -> {
            context.response().setStatusCode(200).end("ok");});
        listController.add(new AuthController());
        listController.add(new UsersController());
        listController.add(new UploadController());
        for (Controller c : listController)
            c.launchController(restAPI);
    }
}