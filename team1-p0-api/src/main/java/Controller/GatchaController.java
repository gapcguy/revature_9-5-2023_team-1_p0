package Controller;

// Model Packages
import Model.Account;
import Model.Transaction;

// Service Packages
import Model.Toy;
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
import jakarta.servlet.http.HttpSession;

//import java.util.ArrayList; -- Remove if tests reveal it's not used.
import java.util.List;


public class GatchaController {
    // Object Service instances
    AccountService accountService;
    TransactionService transactionService;
    ToyService toyService;

    public static HttpSession ses;



    // Default constructor.
    public GatchaController() {
        accountService = new AccountService();
        transactionService = new TransactionService();
        toyService = new ToyService();
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // routes
        app.post("/account/login",                          this::loginHandler);
        app.post("/account/register",                       this::registrationHandler);
        app.get("/toybox",               this::viewToyboxHandler);
        app.get("/toybox/myToys",               this::viewUserToyboxHandler);
        app.patch("/toyboy/pull",  this::pullHandler);
        app.delete("/account", this::deleteUserHandler);
        app.get("/account/allUsers", this::getUsersHandler);
        app.patch("/account/deposit", this::depositHandler);

        return app;
    }

    // Handlers go here
    public void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper    mapper          = new ObjectMapper();
        Account         account         = mapper.readValue(ctx.body(), Account.class);
        Account         loginAccount    = accountService.getUserAccount(account);

        if ( (loginAccount != null)) {
            ses = ctx.req().getSession();
            ses.setAttribute("account_id", loginAccount.getAccount_id());
            ses.setAttribute("username", loginAccount.getUsername());
            ses.setAttribute("password", loginAccount.getPassword());

            ctx.json             ( mapper.writeValueAsString(loginAccount));
            ctx.status           ( 200 );
        } else {
            ctx.status( 401 );
        }
    }

    public void registrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.createAccount(account);

        if (( addedAccount == null)) {
            ctx.status(400);
        } else {
            ses = ctx.req().getSession();
            ses.setAttribute("account_id", addedAccount.getAccount_id());
            ses.setAttribute("username",addedAccount.getUsername());
            ses.setAttribute("password",addedAccount.getPassword());
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
        }
    }

    public void pullHandler(Context ctx) throws JsonProcessingException {
        if(ses == null) { ctx.status(403); }
        else {
            ObjectMapper mapper = new ObjectMapper();
            Account account = new Account((int) ses.getAttribute("account_id"),(String)ses.getAttribute("username"),(String)ses.getAttribute("password"));

            try {
                // Check if the account has sufficient balance and perform the pull
                Transaction transaction = transactionService.pull(account);

                // If the pull operation is successful, return a response.
                ctx.status(200); // HTTP(OK)
                ctx.json(transaction); // Return the transaction object as a JSON response.
            } catch (Exception e) {
                ctx.status(400);
                ctx.result(e.getMessage());
            }
        }
    }

    public void viewUserToyboxHandler(Context ctx) throws JsonProcessingException {
        if(ses == null) { ctx.status(403); }
        else {
            ObjectMapper mapper = new ObjectMapper();
            List<Toy> toys = transactionService.getToysForAccountID((int) ses.getAttribute("account_id"));
            if(!toys.isEmpty()) {
                ctx.status(200);
                ctx.json(mapper.writeValueAsString(toys));
            } else {
                ctx.status(400);
            }
        }
    }

    public void viewToyboxHandler(Context ctx) throws JsonProcessingException {
        if(ses == null) { ctx.status(403); }
        else {
            ObjectMapper mapper = new ObjectMapper();
            List<Toy> toys = toyService.getAvailableToys();
            if(!toys.isEmpty()) {
                ctx.status(200);
                ctx.json(mapper.writeValueAsString(toys));
            } else {
                ctx.status(400);
            }
        }
    }

    public void depositHandler(Context ctx) throws JsonProcessingException {
        if(ses == null) { ctx.status(403); }
        else {
            ObjectMapper mapper = new ObjectMapper();
            Integer amount = mapper.readValue(ctx.body(), Integer.class);
            Account account = new Account((int) ses.getAttribute("account_id"),(String)ses.getAttribute("username"),(String)ses.getAttribute("password"));

            try {
                accountService.deposit(account,amount);
                ctx.status(200); // HTTP(OK)
                ctx.result("New Balance:" + Integer.toString(amount + account.getCoinBalance()));
            } catch (Exception e) {
                ctx.status(400);
                ctx.result(e.getMessage());
            }
        }
    }

    public void deleteUserHandler(Context ctx) throws JsonProcessingException {
        if(ses == null) { ctx.status(403); }
        else {
            ObjectMapper mapper = new ObjectMapper();

            String memberName = (String) ses.getAttribute("username");
            Account deletedAccount = accountService.deleteAccount(memberName);
            ctx.status(200);
            if(deletedAccount != null) { ctx.json(mapper.writeValueAsString(deletedAccount)); }
            else { ctx.status(400); }
        }
    }

    public void getUsersHandler(Context ctx) throws JsonProcessingException {
        if(ses == null) { ctx.status(403); }
        else {
            ObjectMapper mapper = new ObjectMapper();
            List<Account> al = accountService.getAllAccounts();
            if(!al.isEmpty()) {
                ctx.status(200);
                ctx.json(mapper.writeValueAsString(al));
            }
        }

    }
}
