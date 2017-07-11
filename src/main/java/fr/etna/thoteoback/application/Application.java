package fr.etna.thoteoback.application;

import fr.etna.thoteoback.controller.WebController;
import fr.etna.thoteoback.sqlclient.Client;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.json.JsonObject;

import io.vertx.rxjava.ext.mongo.MongoClient;
import io.vertx.rxjava.ext.web.Route;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import rx.Observable;

public class Application extends AbstractVerticle {

        private MongoClient mongo;
        public static Client client = null;
        public WebController wc = null;

        public static void main(String[] args) {
                Vertx vertx = Vertx.vertx();
                vertx.deployVerticle(Application.class.getName(), res -> {
                    if (res.failed())
                        res.cause().printStackTrace();
                });
        }
        @Override
        public void start() throws Exception {
            System.out.println(Application.class.getName());
            final Router mainRouter = Router.router(vertx);
            final Router apiRouter = Router.router(vertx);
            JsonObject config = new JsonObject().put("db_name", "thoteo");
            mongo = MongoClient.createShared(vertx, config);
            client = new Client(mongo);
            mainRouter.route("/ajax*").handler(BodyHandler.create());
            mainRouter.mountSubRouter("/ajax", apiRouter);
            WebController.initController(apiRouter);
            vertx.createHttpServer().requestHandler(mainRouter::accept).listen(80);
        }
    }