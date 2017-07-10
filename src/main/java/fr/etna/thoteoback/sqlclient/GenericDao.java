package fr.etna.thoteoback.sqlclient;

import io.vertx.core.json.JsonObject;

import java.util.List;
import rx.Observable;

public interface GenericDao<T>
{
    //Done
    Observable<List<T>> findAll();
    //Done
    Observable<List<T>> findAll(JsonObject query);
    //Done
    Observable<T> findOne(JsonObject query);
    //Done
    Observable<String> save(T object);
    //Done
    Observable<Void> update(String id, T object);
    //Done
    Observable<Void> delete(String id);
    //Done
    Observable<T> findOne(String id);
    //Done
    Observable<String> insert(T object);
    //Done
    Observable<String> insert(JsonObject json);
    
}