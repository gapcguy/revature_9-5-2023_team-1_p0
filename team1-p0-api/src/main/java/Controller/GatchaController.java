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
        app.get   ( "/toybox", 		  this::viewToyboxHandler     );
        app.delete( "/account", 	      this::deleteUserHandler     );
        app.patch ( "/toyboy/pull", 	  this::pullHandler	          );
        app.post  ( "/account/login", 	  this::loginHandler	      );
        app.get   ( "/toybox/myToys", 	  this::viewUserToyboxHandler );
        app.patch ( "/account/deposit",  this::depositHandler	      );
        app.post  ( "/account/register", this::registrationHandler   );
        app.get   ( "/account/allUsers", this::getUsersHandler	      );

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
            ses.setAttribute("username",   addedAccount.getUsername());
            ses.setAttribute("password",   addedAccount.getPassword());
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.result(e.getMessage());
            ctx.status(400);
        }
    }

    public void viewUserToyboxHandler( Context ctx ) {
        if ( ses == null ) { ctx.status( 403 ); }
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
        if   (ses == null) { ctx.status( 403 ); }
        else {
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Toy>    toys   = toyService.getAvailableToys();
                ctx.status( 200 );
                ctx.json  ( mapper.writeValueAsString( toys ) );
            } catch (Exception e) { e.printStackTrace(); ctx.result(e.getMessage()); ctx.status(400); }
        }
    }

    public void depositHandler( Context ctx ) throws JsonProcessingException {
        if (ses == null) { ctx.status( 403 ); }
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
                ctx.result("New Balance:" + newBalance);
            } catch (Exception e) { e.printStackTrace(); ctx.result( e.getMessage() ); ctx.status( 400 ); }
        }
    }

    public void deleteUserHandler(Context ctx) {
        if (ses == null) { ctx.status(403); }
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
        if (ses == null) {
            ctx.status(403);
        } else {
            try {
                ObjectMapper  mapper = new ObjectMapper();
                List<Account> al     = accountService.getAllAccounts();
                ctx.status(200);
                ctx.json(mapper.writeValueAsString(al));
            } catch (Exception e) { e.printStackTrace(); ctx.result(e.getMessage()); ctx.status(400); }
        }
    }

    public void pullHandler(Context ctx) {
        if (ses == null) {
            ctx.status(403);
        } else {
            Account account = new Account((Integer)ses.getAttribute("account_id"), (String)ses.getAttribute("username"), (String)ses.getAttribute("password"));

            try {
                Transaction transaction = this.transactionService.pull(account);
                ctx.status(200);
                ctx.json(transaction);
            } catch (Exception var4) {
                var4.printStackTrace();
                ctx.result(var4.getMessage());
                ctx.status(400);
            }
        }

    }
}
