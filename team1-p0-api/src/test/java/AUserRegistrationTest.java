import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;

import Service.AccountService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import Controller.GatchaController;
import Model.Account;
import Utils.ConnectionUtil;
import io.javalin.Javalin;

public class AUserRegistrationTest {
    GatchaController gatchaController;
    HttpClient httpClient;
    ObjectMapper objectMapper;
    Javalin app;

    @Before
    public void startup() throws InterruptedException, SQLException {
        ConnectionUtil.resetTestDatabase();
        gatchaController = new GatchaController();
        app = gatchaController.startAPI();
        httpClient = httpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        app.start(8080);
        Thread.sleep(1000);
    }

    @After
    public void tearDown() {
        app.stop();
    }

    // Sends an http request to POST localhost:8080/register.
    //
    // Expected response:
    //  Status Code: 200
    //  Response Body: JSON representation of a user object.
    @Test
    public void testUserRegistration() throws Exception {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/account/register"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"user13\", " +
                        "\"password\": \"password\" }"))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assert.assertEquals(302, status);
       /* AccountService as = new AccountService();
        Account expectedAccount = as.getUserAccount(new Account("user13", "password"));
        Account actualAccount = objectMapper.readValue(response.body().toString(), Account.class);
        Assert.assertEquals(expectedAccount, actualAccount);*/
    }

    // Sends and HTTP request to POST localhost:8080/register when a username already exists
    //
    // Expected Response:
    //  Status Code: 400
    @Test
    public void testThatUserAlreadyExists() throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/account/register"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"user12\", " +
                        "\"password\": \"password\" }"))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse response1 = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        HttpResponse response2 = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status2 = response2.statusCode();
        Assert.assertEquals(400, status2);
        Assert.assertEquals("account couldn't be created", response2.body().toString());
    }

    // Sends an HTTP Request to POST localhost:8080/register when no username is provided
    //
    // Expected Response:
    //  Status Code: 400
    @Test
    public void testNoUserNameDuringRegistration() throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/account/register"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"\", " +
                        "\"password\": \"password\" }"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assert.assertEquals(400, status);
        Assert.assertEquals("Username or Password too short", response.body().toString());
    }

    // Sends an HTTP Request to POST localhost:8080/register when the password is less than 4 characters
    //
    // Expected Response:
    //  Status Code: 400
    @Test
    public void testUserRegistrationWithUserPasswordUnderFourCharacters() throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/account/register"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"username\", " +
                        "\"password\": \"asd\" }"))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assert.assertEquals(400, status);
        Assert.assertEquals("Username or Password too short", response.body().toString());
    }
}
