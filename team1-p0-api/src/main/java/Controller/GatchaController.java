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


// javalin packages
import io.javalin.Javalin;
import io.javalin.http.Context;



public class GatchaController {
    // Object Service instances
    AccountService accountService;
    TransactionService transactionService;
    ToyService toyService;

    // Default constructor.
    public GatchaController() {
        accountService = new AccountService();
        transactionService = new TransactionService();
        toyService = new ToyService();

    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // routes
        app.post("/login",          this::loginHandler);
        app.post("/register",       this::registrationHandler);
        app.post("/account",        this::accountHandler);
        app.post("/account/add-balance", this::addBalanceHandler);

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

            ctx.sessionAttribute("user", loginAccount);

            account.setAccount_id( loginAccount.getAccount_id() );
            account.setUsername  ( loginAccount.getUsername()   );
            account.setPassword  ( loginAccount.getPassword()   );
            ctx.json             ( mapper.writeValueAsString(account));
            ctx.status           ( 200 );
        } else {
            ctx.status( 400 );
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

    public void accountHandler(Context ctx) throws JsonProcessingException {

    }

    public void addBalanceHandler(Context ctx) throws JsonProcessingException {


    }
}
