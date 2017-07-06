package fr.etna.thoteoback.controller;

import fr.etna.thoteoback.controller.users.UsersController;
import fr.etna.thoteoback.sqlclient.Client;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

import java.util.ArrayList;

/**
 * Created by juju_ on 27/06/2017.
 */
public class WebController
{

    private Client client = null;
    Router restAPI = null;
    Router mainRouter = null;
    ArrayList<Controller> listController = new ArrayList<>();

    public WebController(Vertx _vertx, Client _client, Router _mainRouter)
    {
        client = _client;
        restAPI = Router.router(_vertx);
        mainRouter = _mainRouter;
        initController();
        mountWebController();
    }

    public void initController()
    {
        listController.add(new UsersController());
        for (Controller c : listController)
            c.launchController(restAPI);
    }

    public void mountWebController()
    {
        mainRouter.mountSubRouter("/ajax", restAPI);
        System.out.println("mounted");
    }
}