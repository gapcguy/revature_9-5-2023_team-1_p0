package Controller;

// Imports
import Model.Toy;
import Model.Account;
import Model.Transaction;

import Service.ToyService;
import Service.AccountService;
import Service.TransactionService;

import java.lang.Class;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import io.javalin.Javalin;
import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.revature.Main;
import Utils.Resources;


/*
public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        // Define a route to handle the form submission
        app.post("/submit", ctx -> {
            // Extract the input data from the form
            String inputText = ctx.formParam("inputText");

            // Do something with the input data (e.g., print it)
            System.out.println("Received input: " + inputText);

            // You can also respond with a message or redirect the user
            ctx.result("Received input: " + inputText);
        });
    }
}*/
public class GatchaController {
    // Object Service instances
    ToyService         toyService;
    AccountService     accountService;
    TransactionService transactionService;

    public static HttpSession ses;

    // Default constructor.
    public GatchaController() {
        toyService         = new ToyService();
        accountService     = new AccountService();
        transactionService = new TransactionService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // routes
        app.get   ("/", this::indexHandler);
        app.get   ( "/toybox", 		  this::viewToyboxHandler       ); // View available toys
        app.delete( "/account", 	      this::deleteUserHandler       ); // Delete account
        app.post  ( "/account/delete",   this::deleteUserHandler       ); // Delete account
        app.patch ( "/toybox/pull", 	  this::pullHandler	            ); // Pull a random toy
        app.post  ( "/toybox/pull", 	  this::pullHandler	            ); // Pull a random toy
        app.get   ( "/toybox/pull",      this::pullHandler             ); // View the toy just pulled.
        app.post  ( "/account/login", 	  this::loginHandler	        ); // Login start a session
        app.get   ( "/account/login",    this::viewLoginHandler        ); // View Login page
        app.get   ( "/toybox/myToys", 	  this::viewUserToyboxHandler   ); // View toys for logged in account
        app.patch ( "/account/deposit",  this::depositHandler	        ); // Deposit additional currency into your account
        app.post  ( "/account/deposit",  this::depositHandler	        ); // Deposit additional currency into your account
        app.get   ( "/account/register", this::viewRegistrationHandler ); // View registration page
        app.post  ( "/account/register", this::registrationHandler     ); // Register a new account
        app.get   ( "/account/allUsers", this::getUsersHandler	        ); // Retrieve a list of all users.
        app.get   ( "/regredirect",      this::viewRegistrationSuccess   ); // post-registration
        app.get   ( "/loginredirect",    this::viewLoginSuccess   ); // post-registration

        return app;
    }
    public boolean isValid(String json) {
        try {
            new ObjectMapper().readValue(json, Object.class);
        } catch (JsonProcessingException e) {
            return false;
        }
        return true;
    }
    // Handlers
    public void indexHandler(Context ctx) {
        ctx.result(Resources.getFile("index.html"));
        ctx.contentType("text/html");
    }

    public void viewLoginHandler(Context ctx) {
        ctx.result(Resources.getFile("login.html"));
        ctx.contentType("text/html");
    }

    public void viewRegistrationSuccess(Context ctx) {
        ctx.result(Resources.getFile("regredirect.html"));
        ctx.contentType("text/html");
    }

    public void viewLoginSuccess(Context ctx) {
        ctx.result(Resources.getFile("loginredirect.html"));
        ctx.contentType("text/html");
    }

    public void viewRegistrationHandler(Context ctx) {
        ctx.result(Resources.getFile("register.html"));
        ctx.contentType("text/html");
    }

    public void loginHandler( Context ctx ) throws JsonProcessingException {
        ObjectMapper mapper  = new ObjectMapper();
        Account account = null;
        System.out.println(ctx.formParamMap().toString());
        if(isValid(ctx.body())) {
        } else {
             account = new Account(ctx.formParamMap().get("username").get(0), ctx.formParamMap().get("password").get(0));
            System.out.println(account.toString());
        }
        ctx.redirect("/loginredirect");


        try {
            Account loginAccount = accountService.getUserAccount(account);
            ses = ctx.req().getSession();
            ses.setAttribute("account_id", loginAccount.getAccount_id());
            ses.setAttribute("username", loginAccount.getUsername());
            ses.setAttribute("password", loginAccount.getPassword());

            /*
            ctx.result("Welcome" + loginAccount.getUsername() + "\n Your new balance is: " + loginAccount.getCoinBalance());
            ctx.status(200); */
            ctx.json(mapper.writeValueAsString(loginAccount));ctx.status(200);
            account = mapper.readValue( ctx.body(), Account.class );
        } catch (Exception e) {
            e.printStackTrace();
            ctx.result(e.getMessage());
            ctx.status(401);
        }
    }

    public void registrationHandler(Context ctx) throws Exception {
        ObjectMapper mapper  = new ObjectMapper();
        Account account = null;
        if(isValid(ctx.body())) {
            account = mapper.readValue( ctx.body(), Account.class );
        } else {
            account = new Account(ctx.formParamMap().get("username").get(0), ctx.formParamMap().get("password").get(0));
            System.out.println(account.toString());
        }
        try {
            Account addedAccount = accountService.createAccount(account);
            ses = ctx.req().getSession();
            ses.setAttribute("account_id", addedAccount.getAccount_id());
            ses.setAttribute("username", addedAccount.getUsername());
            ses.setAttribute("password", addedAccount.getPassword());
            ctx.json(mapper.writeValueAsString(accountService.getUserAccount(addedAccount)));
            ctx.status(200);
            ctx.redirect("/regredirect");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.result(e.getMessage());
            ctx.status(400);
        }
    }

    public void pullHandler( Context ctx ) {
        if (ses == null) {
            ctx.result("Must Login Before Pulling");
            ctx.status(403);
        } else {

            Account account = new Account(
                    (int) ses.getAttribute("account_id"),
                    (String) ses.getAttribute("username"),
                    (String) ses.getAttribute("password"));

            try {
                Transaction transaction = transactionService.pull(account);                                                // Check if the account has sufficient balance and perform the pull

                ctx.status(200);                                                                                        // HTTP(OK) if we can pull, then
                ctx.json(transaction);                                                                                // return the transaction object as a JSON response.
            } catch (Exception e) {
                e.printStackTrace();
                ctx.result(e.getMessage());
                ctx.status(400);
            }
        }
    }

    public void viewUserToyboxHandler( Context ctx ) {
        if ( ses == null ) {
            ctx.result("Must Login Before Viewing Your Toys");
            ctx.status( 403 );
        }
        else {
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Toy>    toys   =     transactionService.getToysForAccountID((int) ses.getAttribute( "account_id" ) );
                ctx.status( 200 );
                ctx.result( mapper.writeValueAsString( toys ) );
            } catch ( Exception e ) { e.printStackTrace(); ctx.result(e.getMessage()); ctx.status(400); }
        }
    }

    public void viewToyboxHandler( Context ctx ) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Toy>    toys   = toyService.getAvailableToys();
                ctx.status( 200 );
                ctx.json  ( mapper.writeValueAsString( toys ) );
            } catch (Exception e) { e.printStackTrace(); ctx.result(e.getMessage()); ctx.status(400); }
    }

    public void depositHandler( Context ctx ) throws JsonProcessingException {
        if (ses == null) {
            ctx.result("Must Login Before Changing Balance");
            ctx.status( 403 );
        }
        else {
            ObjectMapper mapper  = new ObjectMapper();
            Integer amount = 0;
            System.out.println(ctx.formParamMap().toString());
            if(isValid(ctx.body())) {
                amount = mapper.readValue( ctx.body(), Integer.class );
            } else {
                amount = Integer.parseInt(ctx.formParamMap().get("amount").get(0));
            }
            Account      account = new Account(
                    (int)    ses.getAttribute("account_id"),
                    (String) ses.getAttribute("username"  ),
                    (String) ses.getAttribute("password"  )
            );

            try {
                int newBalance = accountService.deposit(account, amount);
                ctx.status(200); // HTTP(OK)
                account = accountService.getUserAccount(account);
                ctx.result("New Balance:" + Integer.toString(account.getCoinBalance()));
            } catch (Exception e) { e.printStackTrace(); ctx.result( e.getMessage() ); ctx.status( 400 ); }
        }
    }

    public void deleteUserHandler(Context ctx) {
        if (ses == null) {
            ctx.result("Must Login Before Deleting User");
            ctx.status(403);
        }
        else {
            try {
                ObjectMapper mapper         = new ObjectMapper();
                String       memberName     = (String) ses.getAttribute("username");
                Account      deletedAccount = accountService.deleteAccount(memberName);
                ctx.status( 200 );
                ctx.json( mapper.writeValueAsString( deletedAccount ) );
            } catch (Exception e) { e.printStackTrace(); ctx.result( e.getMessage() ); ctx.status( 400 ); }
        }
    }

    public void getUsersHandler(Context ctx) {
            try {
                ObjectMapper  mapper = new ObjectMapper();
                List<Account> al     = accountService.getAllAccounts();
                ctx.status(200);
                ctx.json(mapper.writeValueAsString(al));
            } catch (Exception e) { e.printStackTrace(); ctx.result(e.getMessage()); ctx.status(400); }

    }
}
