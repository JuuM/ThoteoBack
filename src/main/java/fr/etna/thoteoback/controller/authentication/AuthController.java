package fr.etna.thoteoback.controller.authentication;


import fr.etna.thoteoback.controller.Controller;
import fr.etna.thoteoback.controller.authentication.model.AuthenticationForm;
import fr.etna.thoteoback.model.Users;
import fr.etna.thoteoback.sqlclient.dao.UsersDao;
import fr.etna.thoteoback.sqlclient.dao.impl.UsersDaoImpl;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.http.HttpServerResponse;
import rx.Observable;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.ext.auth.jwt.JWTOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.core.Vertx;

import java.util.ArrayList;
import java.util.HashMap;

public class AuthController  implements Controller {

    public AuthController()
    {
        super();
    }
    public UsersDao userDao = new UsersDaoImpl("account");
    private static HashMap<String, AuthenticationForm> jwtHash = new HashMap<>();
    Users user = null;


    @Override
    public void launchController(Router rest)
    {
        rest.get("/t/:ID").handler(this::getTest);
        rest.post("/connect").handler(this::connect);
        rest.post("/validKey").handler(this::validKey);
        System.out.println("mounted LAUNCHCONTROLLER" + this.getClass().getName());
    }
    public void getTest(RoutingContext routingContext){
        try {
            System.out.println("in");
            String ID = routingContext.request().getParam("ID");
            JsonObject returnJson = new JsonObject();
            if (ID != null)
                returnJson.put("result", "ok" + ID);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(returnJson.toString());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        //return Observable.just(returnJson);
    }
    private Observable<JsonObject> validKey(RoutingContext routingContext)
    {
        final AuthenticationForm form = Json.decodeValue(routingContext.getBodyAsString(),
                AuthenticationForm.class);
        JsonObject returnJson = new JsonObject();
        if (jwtHash.get(form.getClef()) == null)
            returnJson.put("result", "key undefined");
        else if (form.getEmail().equals(jwtHash.get(form.getClef()).getEmail()))
            returnJson.put("result", "ok");
        else
            returnJson.put("result", "key not matching");
        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(returnJson.toString());
        return Observable.just(returnJson);
    }
    public void connect(RoutingContext routingContext)
    {

        ArrayList<String> errors = new ArrayList<>();
        JsonObject json = new JsonObject();
        try {
            System.out.println(routingContext.getBodyAsString());
            System.out.println(routingContext);
            final AuthenticationForm form = Json.decodeValue(routingContext.getBodyAsString(),
                    AuthenticationForm.class);
            System.out.println(form.toString());

            //return Observable.just("ok");
            if (form.getEmail() == null || form.getPassword() == null)
                errors.add("undefined value");
            JsonObject json2 = new JsonObject().put("email", form.getEmail());
            // Load company
            userDao.returnTest("Test").subscribe(test -> System.out.println(test));
            userDao.findOne(json2).subscribe(user ->
            {
                if (user != null)
                {
                    if (user.getPassword().equals(form.getPassword())) {
                        System.out.println("no null");
                        JsonObject config = new JsonObject().put("keyStore", new JsonObject()
                                .put("path", "keystore.jceks")
                                .put("type", "jceks")
                                .put("password", "secret"));
                        JWTAuth provider = JWTAuth.create(io.vertx.core.Vertx.vertx(), config);
                        System.out.println("====  LOGIN OK =====");
                        String token = provider.generateToken(new JsonObject().put("sub", user.getEmail()), new JWTOptions());
                        System.out.println("Token : " + token);
                        jwtHash.put(token, form);
                        json.put("result", user.toString());
                        json.put("token", token);
                    }
                    else
                        errors.add("mdp invalide");
                } else
                    errors.add("Undefined account");
                if (errors.size() != 0)
                    json.put("error", errors);
                routingContext.response()
                        .setStatusCode(200)
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(json.toString());
            });

        }catch (Exception e){
            e.printStackTrace();
            errors.add("Not valid format");
            json.put("error", errors);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(json.toString());
        }
    }

}
