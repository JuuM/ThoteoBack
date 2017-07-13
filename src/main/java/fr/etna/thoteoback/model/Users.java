package fr.etna.thoteoback.model;

import io.vertx.core.json.JsonObject;

import java.util.Date;

public class Users {


    private String _id;
    private String email;
    private String password;
    private String Key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    private String name;
    private String surname;

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    private String born;

    public String get_id() {
        System.out.println(_id);
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    // @MongoSerializer
    public JsonObject serialize()
    {
        JsonObject jsonObject = new JsonObject();
        if (_id != null)             { jsonObject.put("_id", _id); }
        if (email != null)           { jsonObject.put("email", email); }
        if (Key != null)    { jsonObject.put("Key", Key); }
        if (name != null)        { jsonObject.put("name", name); }
        if (surname != null)        { jsonObject.put("surname", surname); }
        if (born != null)        { jsonObject.put("born", born); }
        return jsonObject;
    }
    @Override
    public String toString() {
        return "{"+
                "\"id\":\"" + this.get_id() +
                "\", \"email\":\"" + this.getEmail() +
                "\", \"password\":\"" + password +
                "\", \"Key\":\"" + Key +
                "\", \"born\":\"" + born +
                "}";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
