 /* package Controller;

import Model.Account;
import Model.LoginDTO;
import Service.AuthService;
import com.google.gson.Gson;
import io.javalin.http.Handler;
import jakarta.servlet.http.HttpSession;


public class AuthController {

    //Instantiate an auth service.

    // Empty HTTP session object that will be filled upon successful login.
    public static HttpSession ses;

    public Handler loginHandler = (ctx) -> {
        AuthService as = new AuthService();
        String body = ctx.body();
        Gson gson = new Gson();

        // Take the incoming data, create the loginDto object
        LoginDTO lDTO = gson.fromJson(body, LoginDTO.class);

        // Attempt to login with the service.
        Account loggedInAccount = as.login(lDTO);

        // The service will return an employee if successful, and null if unsuccessful.
        if(loggedInAccount != null) {
            // fill our session.
            ses = ctx.req().getSession();

            // Use setAttribute to set certain values to certain keys
            ses.setAttribute("user_id", loggedInAccount);

            // ses.invalidate(); kills the session.
        }
    };


} */
