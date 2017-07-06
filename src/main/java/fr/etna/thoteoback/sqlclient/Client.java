package fr.etna.thoteoback.sqlclient;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

/**
 * Created by juju_ on 27/06/2017.
 */
public class Client {

    private MongoClient mongo = null;
    private JsonObject config = new JsonObject()
        .put("db_name", "handifunding");

    public Client(Vertx vertx)
    {
        try {
            if (mongo == null)
                mongo = MongoClient.createShared(vertx, config);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public MongoClient getClient()
    {
        return mongo;
    }

}
