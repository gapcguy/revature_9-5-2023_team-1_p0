import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;

import org.checkerframework.checker.units.qual.A;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import Controller.GatchaController;
import Model.Account;
import Utils.ConnectionUtil;
import io.javalin.Javalin;

public class UserLoginTest {
    GatchaController gatchaController;
    HttpClient httpClient;
    ObjectMapper objectMapper;
    Javalin app;

    @Before
    public void setUp() throws InterruptedException, SQLException {
        ConnectionUtil.resetTestDatabase();
        gatchaController = new GatchaController();
        app = gatchaController.startAPI();
        httpClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        app.start(8080);
        Thread.sleep(1000);
    }

    @After
    public void tearDown() {
        app.stop();
    }


    // Sends an HTTP request to localhost:8080/login with a valid username and password
    //
    // Expected Response:
    //  Status Code: 200
    //  Response Body: JSON representation of a user object.
    @Test
    public void loginSuccessful() throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/login"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"testuser1\", " +
                        "\"password\": \"password\" }"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();

        Assert.assertEquals(200, status);
        ObjectMapper om = new ObjectMapper();
        Account expectedResult = new Account(1, "testuser1", "password");
        Account actualResult = om.readValue(response.body().toString(), Account.class);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void loginInvalidUsername() throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/login"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"testuser4\", " +
                        "\"password\": \"password\" }"))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();

        Assert.assertEquals(401, status);
        Assert.assertEquals("", response.body().toString());
    }

    @Test
    public void loginInvalidPassword() throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/login"))
                .POST(HttpRequest.BodyPublishers.ofString( "{" +
                        "\"username\": \"testuser1\", " +
                        "\"password\": \"pass123\" }"))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();

        Assert.assertEquals(401, status);
        Assert.assertEquals("", response.body().toString());

    }
}