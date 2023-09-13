package org.revature;

import Controller.GatchaController;
import Utils.ConnectionUtil;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        try{
        ConnectionUtil.resetTestDatabase();} catch(Exception e){
            e.printStackTrace();
        }
        GatchaController controller = new GatchaController();
        Javalin          app        = controller.startAPI();
        app.start(8080);
    }
}
