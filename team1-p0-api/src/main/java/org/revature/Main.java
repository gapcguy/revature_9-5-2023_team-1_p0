package org.revature;

import Controller.GotchaController;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        GotchaController controller = new GotchaController();
        Javalin app = controller.startAPI();
        app.start(8080);
    }
}
