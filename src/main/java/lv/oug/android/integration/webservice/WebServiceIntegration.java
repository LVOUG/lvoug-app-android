package lv.oug.android.integration.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lv.oug.android.domain.EventRepository;
import lv.oug.android.infrastructure.common.ClassLogger;
import lv.oug.android.integration.webservice.articles.ArticleWrapperJSON;
import lv.oug.android.integration.webservice.events.EventsWrapperJSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class WebServiceIntegration {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static final String WEBSERVICE_API_ARTICLES = "http://lvoug-webservice.herokuapp.com/api/articles";
    public static final String WEBSERVICE_API_EVENTS = "http://lvoug-webservice.herokuapp.com/api/events";

    ClassLogger logger = new ClassLogger(WebServiceIntegration.class);

    @Inject
    EventRepository eventRepository;

    public EventsWrapperJSON loadEventsWrapper() {
        InputStream source = retrieveStream(WEBSERVICE_API_EVENTS);

        Gson gson = createGson();
        Reader reader = new InputStreamReader(source);

        return gson.fromJson(reader, EventsWrapperJSON.class);
    }

    public ArticleWrapperJSON loadArticleWrapper() {
        InputStream source = retrieveStream(WEBSERVICE_API_ARTICLES);

        Gson gson = createGson();
        Reader reader = new InputStreamReader(source);

        return gson.fromJson(reader, ArticleWrapperJSON.class);
    }

    private InputStream retrieveStream(String url) {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(url);

        try {

            HttpResponse getResponse = client.execute(getRequest);
            final int statusCode = getResponse.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                logger.w("Error " + statusCode + " for URL " + url);
                return null;
            }

            HttpEntity getResponseEntity = getResponse.getEntity();
            return getResponseEntity.getContent();

        } catch (IOException e) {
            getRequest.abort();
            logger.w("Error for URL " + url, e);
        }

        return null;

    }

    private Gson createGson() {
        return new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .create();
    }
}
