package fr.etna.thoteoback.sqlclient.dao.impl;

import fr.etna.thoteoback.model.Images;
import fr.etna.thoteoback.sqlclient.GenericImplDao;
import fr.etna.thoteoback.sqlclient.dao.ImagesDao;
import rx.Observable;

/**
 * Created by Geoffrey Colas on 12/07/2017.
 */
public class ImagesDaoImpl extends GenericImplDao<Images> implements ImagesDao {
    public ImagesDaoImpl(String name)
    {
        initCollection(name, Images.class);
    }

    @Override
    public Observable<String> returnTest(String t) {
        return Observable.just(t);
    }
}
