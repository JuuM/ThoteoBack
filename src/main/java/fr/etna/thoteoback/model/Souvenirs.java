package fr.etna.thoteoback.model;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;

public class Souvenirs {

    private String _id;
    private String name;
    private ArrayList<Souvenir> listSouvenirs = new ArrayList<>();


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Souvenir> getListSouvenirs() {
        return listSouvenirs;
    }

    public void setListSouvenirs(ArrayList<Souvenir> listSouvenirs) {
        this.listSouvenirs = listSouvenirs;
    }

    public JsonObject serialize()
    {
        JsonObject jsonObject = new JsonObject();
        if (_id != null)             { jsonObject.put("_id", _id); }
        if (name != null)        { jsonObject.put("name", name); }
        JsonArray souvenirs = new JsonArray();
        for (Souvenir souv : getListSouvenirs())
            souvenirs.add(souv.serialize());
        if (souvenirs.size() != 0) { jsonObject.put("listSouvenirs", listSouvenirs); }
        System.out.println(jsonObject.toString());
        return jsonObject;
    }

}
