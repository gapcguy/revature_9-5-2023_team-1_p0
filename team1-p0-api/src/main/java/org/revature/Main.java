package org.revature;

import org.revature.utils.ConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try(Connection con = ConnectionUtil.getConnection()) {
            System.out.println("connection successful");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
