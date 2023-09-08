package Controller;

// Model Packages
import Model.Account;

// Service Packages
import Service.AccountService;
import Service.TransactionService;
import Service.ToyService;

// JSON-Related packages
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


// SQL packages


// javalin packages
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;


public class GatchaController {
    // Object Service instances
    AccountService accountService;
    TransactionService transactionService;
    ToyService toyService;


    Account Login;

    // Default constructor.
    public GatchaController() {
        accountService = new AccountService();
        transactionService = new TransactionService();
        toyService = new ToyService();
        Login = null;
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // routes
        app.post("/login",                          this::loginHandler);
        app.post("/register",                       this::registrationHandler);
        app.get("/toybox",               this::viewToyboxHandler);
        app.get("/toybox/{user_id}",               this::viewUserToyboxHandler);
        app.patch("/users/{user_id}/buy",  this::pullHandler);
        app.delete("/users/{user_id}", this::deleteUserHandler);
        app.get("/users", this::getUsersHandler);
        app.patch("/users/{user_id}/deposit", this::depositHandler);

        return app;
    }

    // Handlers go here
    public void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper    mapper          = new ObjectMapper();
        Account         account         = mapper.readValue(ctx.body(), Account.class);
        Account         loginAccount    = accountService.getUserAccount(account);

        if ( (loginAccount != null)
                && loginAccount.getUsername().equals(account.getUsername())
                && loginAccount.getPassword().equals(account.getPassword())) {

            account.setAccount_id( loginAccount.getAccount_id() );
            account.setUsername  ( loginAccount.getUsername()   );
            account.setPassword  ( loginAccount.getPassword()   );
            ctx.json             ( mapper.writeValueAsString(account));
            ctx.status           ( 200 );
        } else {
            ctx.status( 401 );
        }
    }

    public void registrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.createAccount(account);

        if (( addedAccount == null)
            || ( account.getUsername().isEmpty() )
            || ( account.getPassword().length() < 4)) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
        }
    }

    public void pullHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);


    }

    public void viewUserToyboxHandler(Context ctx) throws JsonProcessingException {

    }

    public void viewToyboxHandler(Context ctx) throws JsonProcessingException {

    }

    public void depositHandler(Context ctx) throws JsonProcessingException {

    }

    public void deleteUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String memberName = ctx.pathParam("username");
        Account deletedAccount = accountService.deleteAccount(memberName);

        ctx.status(200);
        if(deletedAccount != null) { ctx.json(mapper.writeValueAsString(deletedAccount)); }
        else { ctx.status(400); }
    }

    public void getUsersHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Account> al = accountService.getAllAccounts();
        if(!al.isEmpty()) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(al));
        }

    }
}
