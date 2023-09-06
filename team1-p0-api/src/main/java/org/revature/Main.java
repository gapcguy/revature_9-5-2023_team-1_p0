package org.revature;

import Controller.GatchaController;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        GatchaController controller = new GatchaController();
        Javalin app = controller.startAPI();
        app.start(8080);
    }
}
