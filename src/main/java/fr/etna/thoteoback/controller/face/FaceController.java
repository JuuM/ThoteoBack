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
public class FaceController {

    public FaceController(String fileName) {this._fileName = fileName;}

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
            URIBuilder uriBuilder = new URIBuilder("https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect");

            uriBuilder.setParameter("returnFaceId", "true");
            uriBuilder.setParameter("returnFaceLandmarks", "false");
            uriBuilder.setParameter("returnFaceAttributes", "age,gender,headPose,smile,facialHair,glasses,emotion,hair,makeup,occlusion,accessories,blur,exposure,noise");

            URI uri = uriBuilder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers. Replace the example key below with your valid subscription key.
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", "9d798dbc2a454bde90b748a78fb04d1d");

            // Request body. Replace the example URL below with the URL of the image you want to analyze.
            System.out.println(image.length());
            FileEntity reqEntity = new FileEntity(image);
            request.setEntity(reqEntity);

            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                JsonArray jsonObj = new JsonArray(EntityUtils.toString(entity));
                String age = "";
                String gender = "";
                for (int i = 0 ; i < jsonObj.size() ; i++) {
                    if (i != 0) {
                        age += ",";
                        gender += ",";
                    }
                    age += jsonObj.getJsonObject(i).getJsonObject("faceAttributes").getJsonObject("age").toString();
                    gender += jsonObj.getJsonObject(i).getJsonObject("faceAttributes").getJsonObject("gender").toString();
                }
                ArrayList<String> list = null;
                list.add(age);
                list.add(gender);
                return Observable.just(list);
            }
        }
        catch (Exception e)
        {
            errors.add(e.getMessage());
        }
        json.put("error", errors);
        return Observable.just(null);
    }
}