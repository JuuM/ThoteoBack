package fr.etna.thoteoback.sqlclient.dao;

import fr.etna.thoteoback.model.Users;
import fr.etna.thoteoback.sqlclient.GenericDao;

import rx.Observable;

public interface UsersDao extends GenericDao<Users>
{
    //Ici on y ajoute toutes les méthodes uniques à UserDao.
    Observable<String> returnTest(String t);
}
