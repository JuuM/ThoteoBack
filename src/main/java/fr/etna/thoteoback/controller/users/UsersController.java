package fr.etna.thoteoback.controller.users;

import fr.etna.thoteoback.controller.Controller;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by juju_ on 27/06/2017.
 */
public class UsersController implements Controller{



    public UsersController()
    {
        super();
    }

    @Override
    public void launchController(Router rest)
    {
        rest.get("/test/:ID").handler(this::getTest);
        System.out.println("mounted LAUNCHCONTROLLER");
    }
    public void getTest(RoutingContext routingContext){
        String ID = routingContext.request().getParam("ID");
        HttpServerResponse response = routingContext.response();
        if (ID != null)
            response.end("ok" + ID);
    }
}
