import Controller.GatchaController;
import Model.Account;
import Service.AccountService;
import Utils.ConnectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;

public class UserDeleteTest {
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
    public void deleteSuccessful() throws Exception {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/account/login"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"user4\", " +
                        "\"password\": \"dallas\" }"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();

        Assert.assertEquals(200, status);
        ObjectMapper om = new ObjectMapper();
        Assert.assertEquals(200, status);
        AccountService as = new AccountService();
        Account expectedAccount = as.getUserAccount(new Account("user4", "dallas"));
        Account actualResult = om.readValue(response.body().toString(), Account.class);
        Assert.assertEquals(expectedAccount, actualResult);

        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/account"))
                .DELETE()
                .build();

        response = httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        status = response.statusCode();

        Assert.assertEquals(200, status);
        //testing a fail
        postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/account/login"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"user4\", " +
                        "\"password\": \"dallas\" }"))
                .header("Content-Type", "application/json")
                .build();

        response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        status = response.statusCode();

        Assert.assertEquals(401, status);




    }
}
