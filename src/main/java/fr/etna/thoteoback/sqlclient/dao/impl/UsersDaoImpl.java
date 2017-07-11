package fr.etna.thoteoback.sqlclient.dao.impl;

import fr.etna.thoteoback.model.Users;
import fr.etna.thoteoback.sqlclient.GenericImplDao;
import fr.etna.thoteoback.sqlclient.dao.UsersDao;
import rx.Observable;

public class UsersDaoImpl extends GenericImplDao<Users> implements UsersDao
{
    //Implémentation des fonctions ici.
    public UsersDaoImpl(String name)
    {
        initCollection(name, Users.class);
    }

    @Override
    public Observable<String> returnTest(String t) {
        return Observable.just(t);
    }
}
