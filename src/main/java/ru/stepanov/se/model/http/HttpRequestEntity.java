package ru.stepanov.se.model.http;

import io.qameta.allure.Step;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import ru.stepanov.se.domain.JSONMapper;
import ru.stepanov.se.domain.PlaceHolder;

import java.util.logging.Logger;

/**
 * @author Aleksei Stepanov
 */

public class HttpRequestEntity {

    private static final Logger LOG = Logger.getLogger(HttpRequestEntity.class.getName());

    private HttpPost httpPost;

    private HttpGet httpGet;

    private HttpDelete httpDelete;

    public HttpRequestBase getRequest(final HttpType type, final String route) throws Throwable {
        switch (type) {
            case POST:
                return httpPost = buildPostRequest(route);

            case GET:
                return httpGet = new HttpGet(route);

            case DELETE:
                return httpDelete = new HttpDelete(route);
        }
        LOG.info("ERROR: the type of request isn't found");
        return null;
    }

    @Step("Build post request for add data")
    private HttpPost buildPostRequest(String route) throws Throwable {
        PlaceHolder placeHolder = new PlaceHolder();
        placeHolder.setUserId(202);
        placeHolder.setTitle("Title");
        placeHolder.setBody("This is full body");

        String json = JSONMapper.getToJSON(placeHolder);

        StringEntity postingString = new StringEntity(json);
        HttpPost post = new HttpPost(route);
        post.setEntity(postingString);

        return post;
    }

}
