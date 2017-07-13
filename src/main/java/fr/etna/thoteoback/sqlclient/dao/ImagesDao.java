package fr.etna.thoteoback.sqlclient.dao;

import fr.etna.thoteoback.model.Images;
import fr.etna.thoteoback.sqlclient.GenericDao;
import rx.Observable;

/**
 * Created by Geoffrey Colas on 12/07/2017.
 */
public interface ImagesDao extends GenericDao<Images> {
    Observable<String> returnTest(String t);
}
