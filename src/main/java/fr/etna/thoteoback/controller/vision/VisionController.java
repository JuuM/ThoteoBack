package fr.etna.thoteoback.controller.vision;

import fr.etna.thoteoback.controller.Controller;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import rx.Observable;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Geoffrey Colas on 11/07/2017.
 */
public class VisionController {

    public VisionController(String fileName)
    {
        this._fileName = fileName;
    }

    private String _fileName;

    public Observable getEmotionByImage(){
        ArrayList<String> errors = new ArrayList<>();
        JsonObject json = new JsonObject();
        HttpClient httpClient = new DefaultHttpClient();
        File image = new File(this._fileName);
        try
        {
            // NOTE: You must use the same region in your REST call as you used to obtain your subscription keys.
            //   For example, if you obtained your subscription keys from westcentralus, replace "westus" in the
            //   URL below with "westcentralus".
            URIBuilder uriBuilder = new URIBuilder("https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/analyze");

            uriBuilder.setParameter("visualFeatures", "Categories,Description,Color");
            uriBuilder.setParameter("language", "en");

            URI uri = uriBuilder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers. Replace the example key below with your valid subscription key.
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", "d9aa766cfaa841069deefc6fad6677a8");

            // Request body. Replace the example URL below with the URL of the image you want to analyze.
            System.out.println(image.length());
            FileEntity reqEntity = new FileEntity(image);
            request.setEntity(reqEntity);

            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                json.put("response", EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            errors.add(e.getMessage());
        }
        json.put("error", errors);
        return Observable.just(json);
    }
}
