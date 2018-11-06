package ru.stepanov.se.api;

import io.qameta.allure.Step;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.stepanov.se.model.http.HttpRequestEntity;
import ru.stepanov.se.model.http.HttpType;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.stepanov.se.report.AllureLogger.saveSimpleTextLog;

/**
 * @author Aleksei Stepanov
 */

@DisplayName("Test API for jsonplaceholder.typicode.com")
public class JsonPlaceholderTest {

    private static HttpClient CLIENT;

    private static final String URL = "https://jsonplaceholder.typicode.com";

    private HttpRequestEntity httpEntity = new HttpRequestEntity();

    private static Stream<Arguments> dataRequest() {
        return Stream.of(
                Arguments.of(URL + "/posts", HttpType.GET, HttpStatus.SC_OK),
                Arguments.of(URL + "/posts/1", HttpType.GET, HttpStatus.SC_OK),
                Arguments.of(URL + "/posts/1/comments", HttpType.GET, HttpStatus.SC_OK),
                Arguments.of(URL + "/comments?postId=1", HttpType.GET, HttpStatus.SC_OK),
                Arguments.of(URL + "/posts?userId=1", HttpType.GET, HttpStatus.SC_OK),
                Arguments.of(URL + "/posts", HttpType.POST, HttpStatus.SC_CREATED),
                Arguments.of(URL + "/posts/1", HttpType.DELETE, HttpStatus.SC_OK)
        );
    }

    @BeforeAll
    public static void beforeAll() {
        CLIENT = HttpClientBuilder.create().build();
    }

    @ParameterizedTest(name = "{0}, type: {1}, status code: {2}")
    @MethodSource("dataRequest")
    void checkRequest(String route, HttpType type, int statusCode) throws Throwable {
        HttpResponse response = executeRequest(httpEntity.getRequest(type, route));
        addResponseMessageToAllure(response.getEntity());
        checkResponseStatus(statusCode, response.getStatusLine().getStatusCode());
    }

    @Step("Add response message to description")
    private void addResponseMessageToAllure(HttpEntity respEntity) throws Throwable {
        if (respEntity != null) {
            saveSimpleTextLog("Response message", String.valueOf(EntityUtils.toString(respEntity)));
        }
    }

    @Step("Get response from server")
    private HttpResponse executeRequest(HttpRequestBase post) throws Exception {
        saveSimpleTextLog("Request URI", buildRequestMsg(post));
        return CLIENT.execute(post);
    }

    @Step("Check response status code")
    private void checkResponseStatus(int expectedStatus, int actualStatus) {
        assertEquals(expectedStatus, actualStatus, "Response code:");
    }

    private String buildRequestMsg(HttpRequestBase post) {
        return String.valueOf(post.getURI());
    }

}
