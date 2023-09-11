package Controller;

import Utils.ConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;

public class Driver {
    //shortcut to main is main
    public static void main(String[] args) throws Exception{
        try(Connection conn = ConnectionUtil.getConnection()){
            System.out.println("Success");
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Fail");
        }
        ConnectionUtil.resetTestDatabase();

        GatchaController gc = new GatchaController();
    }
}
