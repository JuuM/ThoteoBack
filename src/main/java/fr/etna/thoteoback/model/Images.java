package fr.etna.thoteoback.model;

/**
 * Created by Geoffrey Colas on 12/07/2017.
 */
public class Images {
    private String _id;
    private String _fileName;
    private String _uploadedFileName;
    private String _contentType;
    private long _size;
    private String _mood;
    private String _info;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_fileName() {
        return _fileName;
    }

    public void set_fileName(String _fileName) {
        this._fileName = _fileName;
    }

    public String get_uploadedFileName() {
        return _uploadedFileName;
    }

    public void set_uploadedFileName(String _uploadedFileName) {
        this._uploadedFileName = _uploadedFileName;
    }

    public String get_contentType() {
        return _contentType;
    }

    public void set_contentType(String _contentType) {
        this._contentType = _contentType;
    }

    public long get_size() {
        return _size;
    }

    public void set_size(long _size) {
        this._size = _size;
    }

    public String get_mood() {
        return _mood;
    }

    public void set_mood(String _mood) {
        this._mood = _mood;
    }

    public String get_info() {
        return _info;
    }

    public void set_info(String _info) {
        this._info = _info;
    }
}
