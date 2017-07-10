package fr.etna.thoteoback.controller;


import io.vertx.rxjava.ext.web.Router;

/**
 * Created by juju_ on 27/06/2017.
 */
public interface Controller {


    default void launchController(Router _restAPI)
    {
    }
}
