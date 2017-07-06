package fr.etna.thoteoback.controller;

import io.vertx.ext.web.Router;

/**
 * Created by juju_ on 27/06/2017.
 */
public interface Controller {

    public Router restAPI = null;

    default void launchController(Router _restAPI)
    {
    }
}
