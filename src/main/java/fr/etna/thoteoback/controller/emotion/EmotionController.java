package fr.etna.thoteoback.controller.emotion;

import fr.etna.thoteoback.controller.Controller;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.apache.http.entity.FileEntity;
import rx.Observable;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Geoffrey Colas on 10/07/2017.
 */
public class EmotionController implements Controller {

    public EmotionController()
    {
        super();
    }

    @Override
    public void launchController(Router rest)
    {
        rest.post("/api/url").handler(this::getEmotionByUrl);
        rest.post("/api/image").handler(this::getEmotionByImage);
        System.out.println("mounted LAUNCHCONTROLLER" + this.getClass().getName());
    }

    private Observable getEmotionByUrl(RoutingContext routingContext){
        ArrayList<String> errors = new ArrayList<>();
        JsonObject json = new JsonObject();
        HttpClient httpClient = new DefaultHttpClient();
        try
        {
            // NOTE: You must use the same region in your REST call as you used to obtain your subscription keys.
            //   For example, if you obtained your subscription keys from westcentralus, replace "westus" in the
            //   URL below with "westcentralus".
            URIBuilder uriBuilder = new URIBuilder("https://westus.api.cognitive.microsoft.com/emotion/v1.0/recognize");

            URI uri = uriBuilder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers. Replace the example key below with your valid subscription key.
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", "d66cc23bd83844cb948699d138a6c69b");

            // Request body. Replace the example URL below with the URL of the image you want to analyze.
            StringEntity reqEntity = new StringEntity("{ \"url\": \"" + routingContext.request().getParam("url")+ "\" }");
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
        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(json.toString());
        return Observable.just(json);
    }

    private Observable getEmotionByImage(RoutingContext routingContext){
        ArrayList<String> errors = new ArrayList<>();
        JsonObject json = new JsonObject();
        HttpClient httpClient = new DefaultHttpClient();
        File test = new File("file-uploads/0cd6fc03-46da-4710-9491-6b30f721c20f");
        try
        {
            // NOTE: You must use the same region in your REST call as you used to obtain your subscription keys.
            //   For example, if you obtained your subscription keys from westcentralus, replace "westus" in the
            //   URL below with "westcentralus".
            URIBuilder uriBuilder = new URIBuilder("https://westus.api.cognitive.microsoft.com/emotion/v1.0/recognize");

            URI uri = uriBuilder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers. Replace the example key below with your valid subscription key.
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", "d66cc23bd83844cb948699d138a6c69b");

            // Request body. Replace the example URL below with the URL of the image you want to analyze.
            System.out.println(test.length());
            FileEntity reqEntity = new FileEntity(test);
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
        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(json.toString());
        return Observable.just(json);
    }
}
