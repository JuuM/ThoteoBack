package fr.etna.thoteoback.model;

public class Users {

    private int _guid;
    private String email;
    private String password;
    private String Key;

    public int get_guid() {
        return _guid;
    }

    public void set_guid(int _guid) {
        this._guid = _guid;
    }

    public String getEmail() {
        return email;
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
