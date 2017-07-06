package fr.etna.thoteoback.application;

import fr.etna.thoteoback.controller.WebController;
import fr.etna.thoteoback.controller.authentication.AuthController;
import fr.etna.thoteoback.controller.authentication.model.AuthenticationForm;
import fr.etna.thoteoback.sqlclient.Client;
import io.reactivex.Observable;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class Application extends AbstractVerticle {

        public Client client = null;
        public WebController wc = null;

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Application());

    }
        @Override
        public void start() throws Exception {

            System.out.println("Start of Hello World");

            final Router router = Router.router(vertx);
            wc = new WebController(vertx, client, router);
            router.route("/*").handler(StaticHandler.create());
            vertx.createHttpServer().requestHandler(router::accept).listen(8080);
            client = new Client(vertx);
        }
    }