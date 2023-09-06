package Controller;

// Model Packages

// Service Packages

// SQL packages

// javalin packages
import io.javalin.Javalin;
import io.javalin.http.Context;


public class GatchaController {
    // Object Service instances


    // Default constructor.
    public GatchaController() {

    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // routes

        return app;
    }

    // Handlers go here
}
