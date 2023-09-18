import Controller.GatchaController;
import Model.Account;
import Model.Toy;
import Service.AccountService;
import Service.TransactionService;

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
import java.util.List;

public class ControllerTest {

    GatchaController gatchaController;
    HttpClient httpClient;
    ObjectMapper objectMapper;
    Javalin app;
    @Before  public void setUp() throws InterruptedException, SQLException {
        System.out.println("Current Working Directory: " + System.getProperty("user.dir"));

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

    @Test
    public void testGetUsers() throws Exception {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/account/allUsers"))
                .GET()
                .header("Content-Type", "application/json")
                .build();

        HttpResponse response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();

        Assert.assertEquals(200, status);
        ObjectMapper om = new ObjectMapper();
        Assert.assertEquals(200, status);
        AccountService as = new AccountService();
        List<Account> expectedList = as.getAllAccounts();
        List<Account> actualResult = om.readValue(response.body().toString(), om.getTypeFactory().constructCollectionType(List.class, Account.class));
        Assert.assertEquals(expectedList, actualResult);
    }

    @Test
    public void testGetToysForUserId() throws Exception {
        HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/account/login"))
            .POST(HttpRequest.BodyPublishers.ofString("{" +
                    "\"username\": \"user6\", " +
                    "\"password\": \"tampa\" }"))
            .header("Content-Type", "application/json")
            .build();

        httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        TransactionService ts = new TransactionService();
        AccountService as = new AccountService();
        Account c = as.getUserAccount(new Account("user6", "tampa"));
        ts.pull(c);
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/toybox/myToys"))
                .GET()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assert.assertEquals(200, status);
        ObjectMapper om = new ObjectMapper();
        List<Toy> expectedList = om.readValue(response.body().toString(), om.getTypeFactory().constructCollectionType(List.class, Toy.class));
        assert(!expectedList.isEmpty());
    }


}
