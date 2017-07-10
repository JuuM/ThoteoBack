package fr.etna.thoteoback.application;

import fr.etna.thoteoback.controller.WebController;
import fr.etna.thoteoback.sqlclient.Client;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.json.JsonObject;

import io.vertx.rxjava.ext.mongo.MongoClient;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import rx.Observable;

public class Application extends AbstractVerticle {

        private MongoClient mongo;
        public static Client client = null;
        public WebController wc = null;

        public static void main(String[] args) {
                Vertx vertx = Vertx.vertx();
                vertx.deployVerticle(Application.class.getName());
        }
        @Override
        public void start() throws Exception {
            System.out.println(Application.class.getName());
            final Router router = Router.router(vertx);
            router.route("/*").handler(StaticHandler.create());
            JsonObject config = new JsonObject()
                    .put("db_name", "thoteo");
            mongo = MongoClient.createShared(vertx, config);
            client = new Client(mongo);
            wc = new WebController(vertx, client, router);
            vertx.createHttpServer().requestHandler(router::accept).listen(8080);
        }
    }