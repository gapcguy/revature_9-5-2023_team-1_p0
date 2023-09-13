package Controller;

// Imports
import Model.Toy;
import Model.Account;
import Model.Transaction;

import Service.ToyService;
import Service.AccountService;
import Service.TransactionService;

import java.util.List;

import io.javalin.Javalin;
import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


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
        app.get   ( "/toybox", 		  this::viewToyboxHandler     ); //View available toys
        app.delete( "/account", 	      this::deleteUserHandler     ); //Delete account
        app.patch ( "/toyboy/pull", 	  this::pullHandler	          ); //Pull a random toy
        app.post  ( "/account/login", 	  this::loginHandler	      ); //Login start a session
        app.get   ( "/toybox/myToys", 	  this::viewUserToyboxHandler ); //view toys for logged in account
        app.patch ( "/account/deposit",  this::depositHandler	      ); //Deposit additional currency into your account
        app.post  ( "/account/register", this::registrationHandler   ); //Register a new account
        app.get   ( "/account/allUsers", this::getUsersHandler	      ); //Retrieve a list of all users.

        return app;
    }

    // Handlers
    public void loginHandler( Context ctx ) throws JsonProcessingException {
        ObjectMapper mapper  = new ObjectMapper();
        Account      account = mapper.readValue( ctx.body(), Account.class );

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
        } catch (Exception e) {
            e.printStackTrace();
            ctx.result(e.getMessage());
            ctx.status(401);
        }
    }

    public void registrationHandler(Context ctx) throws Exception {
        ObjectMapper mapper  = new ObjectMapper();
        Account      account = mapper.readValue( ctx.body(), Account.class );

        try {
            Account addedAccount = accountService.createAccount(account);
            ses = ctx.req().getSession();
            ses.setAttribute("account_id", addedAccount.getAccount_id());
            ses.setAttribute("username", addedAccount.getUsername());
            ses.setAttribute("password", addedAccount.getPassword());
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
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
            Integer      amount  = mapper.readValue( ctx.body(), Integer.class );
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
