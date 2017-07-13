package fr.etna.thoteoback.controller.users;

import fr.etna.thoteoback.controller.Controller;
import fr.etna.thoteoback.controller.authentication.model.AuthenticationForm;
import fr.etna.thoteoback.model.Users;
import fr.etna.thoteoback.sqlclient.dao.UsersDao;
import fr.etna.thoteoback.sqlclient.dao.impl.UsersDaoImpl;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTOptions;
import io.vertx.rxjava.core.http.HttpClientResponse;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import rx.Observable;

import java.util.ArrayList;

/**
 * Created by juju_ on 27/06/2017.
 */
public class UsersController implements Controller{


    public UsersDao userDao = new UsersDaoImpl("account");

    public UsersController()
    {
        super();
    }

    @Override
    public void launchController(Router rest)
    {
        //rest.get("/test/:ID").handler(this::getTest);
        //rest.post("/");
        rest.post("/register").handler(this::createUser);
        System.out.println("mounted LAUNCHCONTROLLER" + this.getClass().getName());
    }
    public Observable<Users> createUser (RoutingContext routingContext){
        JsonObject json = new JsonObject(routingContext.getBodyAsString());
        Users user = Json.decodeValue(routingContext.getBodyAsString(), Users.class);
        JsonObject errors = new JsonObject();
        userDao.findOne(new JsonObject().put("email", user.getEmail())).subscribe(_u ->
        {
            if (_u == null)
            {
                userDao.insert(json).subscribe(u ->
                {
                    user.set_id(u);
                    routingContext.response()
                            .setStatusCode(200)
                            .putHeader("content-type", "application/json; charset=utf-8")
                            .end(user.serialize().toString());
                });
            }else {
                errors.put("error", "Email déjà existante");
                routingContext.response()
                        .setStatusCode(200)
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(errors.toString());
            }
        });
        return Observable.just(user);
    }
    public void mailFound (RoutingContext routingContext){
        String email = routingContext.getBodyAsString();
        userDao.findOne(new JsonObject().put("email", email)).subscribe(found ->
        {
            routingContext.response()
                    .setStatusCode(200)
                    .end(found != null ? "YES" : "NO");
        });
    }

}
