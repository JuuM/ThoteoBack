package fr.etna.thoteoback.model;

public class Users {


    private Object _id;
    private String email;
    private String password;
    private String Key;

    public Object get_id() {
        System.out.println(_id);
        return _id;
    }

    public void set_id(Object _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Users{" +
                "_id=" + _id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", Key='" + Key + '\'' +
                '}';
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
