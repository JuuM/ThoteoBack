package fr.etna.thoteoback.controller.emotion;

import fr.etna.thoteoback.controller.Controller;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import org.apache.http.entity.FileEntity;
import rx.Observable;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

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
public class EmotionController {

    public EmotionController(String fileName)
    {
        this._fileName = fileName;
    }

    private String _fileName;

    public Observable getEmotionByImage(){
        JsonObject scores = new JsonObject();
        HttpClient httpClient = new DefaultHttpClient();
        File image = new File(this._fileName);
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
            System.out.println(image.length());
            FileEntity reqEntity = new FileEntity(image);
            request.setEntity(reqEntity);

            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                String keyString = "";
                JsonArray jsonObj = new JsonArray(EntityUtils.toString(entity));
                for (int i = 0 ; i < jsonObj.size() ; i++) {
                    if (i != 0) {
                        keyString += ",";
                    }
                    scores = jsonObj.getJsonObject(i).getJsonObject("scores");
                    float value = 0;
                    for (Map.Entry<String, Object> score : scores) {
                        if (value < Float.parseFloat(score.getValue().toString())) {
                            value = Float.parseFloat(score.getValue().toString());
                            keyString = score.getKey().toString();
                        }
                    }
                }
                return Observable.just(keyString);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return Observable.just(null);
    }
}
