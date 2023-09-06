package Controller;

import Utils.Application;
import Utils.ConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;

public class Driver {
    //shortcut to main is main
    public static void main(String[] args) {
        try(Connection conn = ConnectionUtil.getConnection()){
            System.out.println("Success");
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Fail");
        }
        Application.databaseSetup();
    }
}
