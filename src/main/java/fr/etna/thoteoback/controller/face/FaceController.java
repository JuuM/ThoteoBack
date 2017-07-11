package fr.etna.thoteoback.controller.face;

import fr.etna.thoteoback.controller.Controller;
import io.vertx.core.json.JsonArray;
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
public class FaceController implements Controller {

    public FaceController()
    {
        super();
    }

    @Override
    public void launchController(Router rest)
    {
        rest.post("/face/url").handler(this::getEmotionByUrl);
        rest.post("/face/image").handler(this::getEmotionByImage);
        System.out.println("mounted LAUNCHCONTROLLER" + this.getClass().getName());
    }

    private Observable getEmotionByUrl(RoutingContext routingContext){
        ArrayList<String> errors = new ArrayList<>();
        JsonObject json = new JsonObject();
        HttpClient httpclient = new DefaultHttpClient();

        try
        {
            URIBuilder builder = new URIBuilder("https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect");

            // Request parameters. All of them are optional.
            builder.setParameter("returnFaceId", "true");
            builder.setParameter("returnFaceLandmarks", "false");
            builder.setParameter("returnFaceAttributes", "age,gender,headPose,smile,facialHair,glasses,emotion,hair,makeup,occlusion,accessories,blur,exposure,noise");

            // Prepare the URI for the REST API call.
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers.
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", "9d798dbc2a454bde90b748a78fb04d1d");

            // Request body.
            StringEntity reqEntity = new StringEntity("{\"url\":\"https://upload.wikimedia.org/wikipedia/commons/c/c3/RH_Louise_Lillian_Gish.jpg\"}");
            request.setEntity(reqEntity);

            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                String jsonString = EntityUtils.toString(entity).trim();
                if (jsonString.charAt(0) == '[') {
                    JsonArray jsonArray = new JsonArray(jsonString);
                    json.put("response", jsonArray.toString());
                }
                else if (jsonString.charAt(0) == '{') {
                    JsonObject jsonObject = new JsonObject(jsonString);
                    json.put("response", jsonObject.toString());
                } else {
                    json.put("response", jsonString);
                }
            }
        }
        catch (Exception e)
        {
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
            URIBuilder uriBuilder = new URIBuilder("https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect");

            URI uri = uriBuilder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers. Replace the example key below with your valid subscription key.
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", "9d798dbc2a454bde90b748a78fb04d1d");

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