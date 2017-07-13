package fr.etna.thoteoback.controller.upload;

import fr.etna.thoteoback.controller.Controller;
import fr.etna.thoteoback.controller.emotion.EmotionController;
import fr.etna.thoteoback.controller.face.FaceController;
import fr.etna.thoteoback.controller.vision.VisionController;
import fr.etna.thoteoback.model.Images;
import fr.etna.thoteoback.sqlclient.dao.ImagesDao;
import fr.etna.thoteoback.sqlclient.dao.impl.ImagesDaoImpl;
import io.vertx.core.json.JsonArray;
import io.vertx.rxjava.ext.web.FileUpload;
import rx.Observable;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;

import java.util.ArrayList;

public class UploadController implements Controller {

        public UploadController()
        {
            super();
        }
        public ImagesDao imagesDao = new ImagesDaoImpl("uploads");
        Images images = null;
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
                    FaceController face = new FaceController(f.uploadedFileName());
                    EmotionController emotion = new EmotionController(f.uploadedFileName());
                    VisionController vision = new VisionController(f.uploadedFileName());
                    face.getEmotionByImage().toList().subscribe(response -> {
                    });
                    emotion.getEmotionByImage().subscribe(response -> {
                        json.put("mood", response.toString());
                    });
                    vision.getEmotionByImage().subscribe(response -> {
                        System.out.println(response.toString());
                    });;
                    json.put("fileName", f.fileName());
                    json.put("size", f.size());
                    json.put("uploadedFileName", f.uploadedFileName());
                    json.put("contentType", f.contentType());
                    imagesDao.insert(json).subscribe();
                }
            } catch (Exception e) {
                errors.add(e.toString());
            }
            json.put("error", errors);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(json.toString());
            imagesDao.findAll().subscribe(response -> System.out.println(response.size()));
            return Observable.just(json);
        }

}
