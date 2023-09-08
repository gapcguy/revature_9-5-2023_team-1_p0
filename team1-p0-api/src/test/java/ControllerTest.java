import Controller.GatchaController;
import Model.Account;
import Service.AccountService;
import Utils.Application;
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
    public void loginSuccessful() throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/users"))
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


}
