package fr.etna.thoteoback.sqlclient;

import io.vertx.rxjava.ext.mongo.MongoClient;
import io.vertx.rxjava.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.Observable;

/**
 * Created by juju_ on 27/06/2017.
 */
public class Client {

    public static MongoClient mongo = null;

    public Client(MongoClient _mongo)
    {
        try {
            mongo = _mongo;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public MongoClient getClient()
    {
        return mongo;
    }

}
