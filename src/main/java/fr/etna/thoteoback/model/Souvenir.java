package fr.etna.thoteoback.model;

import io.vertx.core.json.JsonObject;

public class Souvenir {

    private String _id;
    private String url;
    private String quand;
    private String qui;
    private String quoi;
    private String ou;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQuand() {
        return quand;
    }

    public void setQuand(String quand) {
        this.quand = quand;
    }

    public String getQui() {
        return qui;
    }

    public void setQui(String qui) {
        this.qui = qui;
    }

    public String getQuoi() {
        return quoi;
    }

    public void setQuoi(String quoi) {
        this.quoi = quoi;
    }

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public JsonObject serialize()
    {
        JsonObject jsonObject = new JsonObject();
        if (_id != null)             { jsonObject.put("_id", _id); }
        if (url != null)           { jsonObject.put("url", url); }
        if (quand != null)    { jsonObject.put("quand", quand); }
        if (qui != null)        { jsonObject.put("qui", qui); }
        if (quoi != null)        { jsonObject.put("quoi", quoi); }
        if (ou != null)        { jsonObject.put("ou", ou); }
        return jsonObject;
    }
}
