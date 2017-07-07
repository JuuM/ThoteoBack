package fr.etna.thoteoback.controller.upload;

import fr.etna.thoteoback.controller.Controller;
import io.reactivex.Observable;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.ArrayList;

public class UploadController implements Controller {

        public UploadController()
        {
            super();
        }

        @Override
        public void launchController(Router rest)
        {
            rest.post("/upload").handler(BodyHandler.create().setUploadsDirectory("./file-uploads"));
            rest.post("/upload").handler(this::upload);
            System.out.println("mounted LAUNCHCONTROLLER" + this.getClass().getName());
        }

        //POST EXEMPLE
        private Observable upload(RoutingContext routingContext)
        {
            ArrayList<String> errors = new ArrayList<>();
            JsonObject json = new JsonObject();
            try {
                for (FileUpload f : routingContext.fileUploads()) {
                    json.put("fileName", f.fileName());
                    json.put("name", f.name());
                    json.put("size", f.size());
                    json.put("charSet", f.charSet());
                    json.put("uploadedFileName", f.uploadedFileName());
                    json.put("contentType", f.contentType());
                }
            } catch (Exception e) {
                errors.add(e.toString());
            }
            json.put("error", errors);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(json.toString());
            return Observable.just(json);
        }
}
