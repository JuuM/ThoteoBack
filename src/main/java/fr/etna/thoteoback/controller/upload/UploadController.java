package fr.etna.thoteoback.controller.upload;

import fr.etna.thoteoback.controller.Controller;
import fr.etna.thoteoback.controller.authentication.model.AuthenticationForm;
import io.reactivex.Observable;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;

public class UploadController implements Controller {

        public UploadController()
        {
            super();
        }

        @Override
        public void launchController(Router rest)
        {
            //rest.post("/connect").handler(this::connect);
            //rest.get("/connect2/:ID").handler(this::test);
            System.out.println("mounted LAUNCHCONTROLLER" + this.getClass().getName());
        }
        //POST EXEMPLE
        /*public Observable connect(RoutingContext routingContext)
        {
            ArrayList<String> errors = new ArrayList<>();
            JsonObject json = new JsonObject();
            try {
                final AuthenticationForm form = Json.decodeValue(routingContext.getBodyAsString(),
                        AuthenticationForm.class);
                JsonObject config = new JsonObject().put("keyStore", new JsonObject()
                        .put("path", "keystore.jceks")
                        .put("type", "jceks")
                        .put("password", "secret"));
                JWTAuth provider = JWTAuth.create(io.vertx.core.Vertx.vertx(), config);
                System.out.println(form.toString());
                //return Observable.just("ok");
                if (form.getEmail() == null || form.getPassword() == null)
                    errors.add("undefined value");
            /*if (!form.getEmail().equalsIgnoreCase("admin"))//TODO : Get
                errors.add("Nom de compte invalide");
            if (!form.getPassword().equalsIgnoreCase("1234"))
                errors.add("Mot de passe invalide");
            }catch (Exception e){
                errors.add("Not valid format");
            }
            json.put("error", errors);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(json.toString());
            return Observable.just(json);
        }*/
}
