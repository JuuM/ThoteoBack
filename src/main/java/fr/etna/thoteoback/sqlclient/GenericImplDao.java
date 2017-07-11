package fr.etna.thoteoback.sqlclient;

import fr.etna.thoteoback.application.Application;
import fr.etna.thoteoback.controller.authentication.model.AuthenticationForm;
import fr.etna.thoteoback.model.Users;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import rx.Observable;
import io.vertx.rxjava.ext.mongo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;


public abstract class GenericImplDao<T> implements GenericDao<T>
{
    /** Initialized from reflection **/
    protected final MongoClient mongo = Application.client.getClient();
    protected String collection = null;
    protected Class<T> clazz;

    public GenericImplDao()
    {
    }
    public void initCollection(String name, Class<T> _clazz)
    {
        collection = name;
        clazz = _clazz;
    }

    @Override
    public Observable<List<T>> findAll()
    {
        return mongo.findObservable(collection, new JsonObject()).flatMap(this::deserializeObservable);
    }
    @Override
    public Observable<List<T>> findAll(JsonObject query)
    {
        List<T> template = new ArrayList<>();
        return mongo.findObservable(collection, query).flatMap(this::deserializeObservable);
    }
    @Override
    public Observable<T> findOne(JsonObject query)
    {
        return mongo.findOneObservable(collection, query, null).flatMap(this::deserializeObservable);
    }
    @Override
    public Observable<String> insert(T object)
    {
        return Observable.just(object).flatMap(this::serializeObservable).flatMap(jsonObject -> mongo.insertObservable(collection, jsonObject));
    }
    @Override
    public Observable<String> insert(JsonObject json)
    {
        return mongo.insertObservable(collection, json);
    }
    @Override
    public Observable<T> findOne(String id)
    {
        if (id != null)
        {
            JsonObject query = new JsonObject().put("_id", new JsonObject().put("$oid", id));
            return mongo.findOneObservable(collection, query, null).flatMap(this::deserializeObservable);
        }
        else
        {
            return Observable.just(null);
        }
    }
    @Override
    public Observable<Void> update(String id, T object)
    {
        System.out.println("Update !!");
        JsonObject query = new JsonObject().put("_id", new JsonObject().put("$oid", id));
        return this.serializeObservable(object).flatMap(jsonObject ->
        {
            JsonObject update = new JsonObject().put("$set", jsonObject);
            return mongo.updateObservable(collection, query, update);
        });
    }
    @Override
    public Observable<String> save(T object)
    {
        System.out.println("Save");
        return Observable.just(object).flatMap(this::serializeObservable).flatMap(jsonObject -> mongo.saveObservable(collection, jsonObject));
    }
    @Override
    public Observable<Void> delete(String id)
    {
        if (id != null)
        {
            JsonObject query = new JsonObject().put("_id", new JsonObject().put("$oid", id));
            return mongo.removeOneObservable(collection, query);
        }
        else
        {
            return Observable.just(null);
        }
    }

    protected Observable<List<T>> deserializeObservable(List<JsonObject> jsonObjects)
    {
        System.out.println(jsonObjects);
        List<T> result = new ArrayList<>();
        try
        {
            for (JsonObject json : jsonObjects)
            {
                T object = Json.decodeValue(json.toString(), clazz);
                result.add(object);
            }
            return Observable.just(result);
        }
        catch (Exception e)
        {
            return Observable.error(e);
        }
    }
    protected Observable<JsonObject> serializeObservable(T object) {
        System.out.println(object);
        try {
            if (object == null)
                return Observable.just(null);
            JsonObject json = new JsonObject(Json.encode(object));
            return Observable.just(json);
        } catch (Exception e) {
            return Observable.error(e);
        }
    }
    protected Observable<T> deserializeObservable(JsonObject jsonObject) {
        System.out.println(jsonObject);
        try {
            if (jsonObject == null)
                return Observable.just(null);
            T object = Json.decodeValue(jsonObject.toString(), clazz);
            return Observable.just(object);
        } catch (Exception e) {
            return Observable.error(e);
        }
    }
}
