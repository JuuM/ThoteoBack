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
public class VisionController implements Controller{

    public VisionController()
    {
        super();
    }

    @Override
    public void launchController(Router rest)
    {
        rest.post("/vision/url").handler(this::getEmotionByUrl);
        rest.post("/vision/image").handler(this::getEmotionByImage);
        System.out.println("mounted LAUNCHCONTROLLER" + this.getClass().getName());
    }

    public Observable getEmotionByUrl(RoutingContext routingContext) {
        ArrayList<String> errors = new ArrayList<>();
        JsonObject json = new JsonObject();
        HttpClient httpclient = new DefaultHttpClient();
        try {
            URIBuilder builder = new URIBuilder("https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/analyze");

            // Request parameters. All of them are optional.
            builder.setParameter("visualFeatures", "Categories,Description,Color");
            builder.setParameter("language", "en");

            // Prepare the URI for the REST API call.
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers.
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", "d9aa766cfaa841069deefc6fad6677a8");

            // Request body.
            StringEntity reqEntity = new StringEntity("{\"url\":\"https://upload.wikimedia.org/wikipedia/commons/1/12/Broadway_and_Times_Square_by_night.jpg\"}");
            request.setEntity(reqEntity);

            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // Format and display the JSON response.
                String jsonString = EntityUtils.toString(entity);
                JsonObject responseJson = new JsonObject(jsonString);
                json.put("response", responseJson.toString());
            }
        } catch (Exception e) {
            // Display error message.
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
            URIBuilder uriBuilder = new URIBuilder("https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/analyze");

            URI uri = uriBuilder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers. Replace the example key below with your valid subscription key.
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", "d9aa766cfaa841069deefc6fad6677a8");

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
