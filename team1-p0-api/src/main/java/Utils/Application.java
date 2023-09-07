package Utils;

import Controller.GatchaController;
import io.javalin.Javalin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Application {
    public static void main(String[] args) {
        databaseSetup();
        GatchaController gatchaControllerController = new GatchaController();
        Javalin app = gatchaControllerController.startAPI();
        app.start(8080);
    }


    /*  public Account(int account_id, String username, String password, int coin_balance) {
        this.account_id = account_id;
        this.username   = username;
        this.password   = password;
        this.coin_balance = coin_balance;

    }*/
    public static void databaseSetup() {
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps1 = conn.prepareStatement("drop table if exists account, treasurebox;");
            ps1.executeUpdate();
            PreparedStatement ps2 = conn.prepareStatement("create table account(" +
                    "account_id serial primary key, " +
                    "username text unique not null, " +
                    "password text not null," +
                    "coin_balance int Default 50 not null," +
                    "coin_income int Default 50 not null," +
                    "coin_outcome int Default 0 not null" +
                    ");");
            ps2.executeUpdate();
            PreparedStatement ps3 = conn.prepareStatement("insert into account (username, password) values " +
                    "('user1', 'dallas')," +
                    "('user2', 'reston')," +
                    "('user3', 'morgantown')," +
                    "('user4', 'dallas')," +
                    "('user5', 'dallas')," +
                    "('user6', 'tampa');");
            ps3.executeUpdate();
            PreparedStatement ps4 = conn.prepareStatement("drop table if exists toy;");
            ps4.executeUpdate();
            PreparedStatement ps5 = conn.prepareStatement("create table toy(" +
                    "toy_id serial primary key, " +
                    "name text unique not null, " +
                    "quantity int not null);");
            ps5.executeUpdate();
            PreparedStatement ps6 = conn.prepareStatement("insert into toy (name, quantity) Values" +
                    "('barbie', 10)," +
                    "('spongebob', 25), " +
                    "('fidget spinner', '4'), " +
                    "('gamecube', '100');");
            ps6.executeUpdate();
            PreparedStatement ps7 = conn.prepareStatement("drop table if exists treasurebox;");
            ps7.executeUpdate();
            PreparedStatement ps8 = conn.prepareStatement("create table treasurebox(" +
                    "transaction_id serial primary key, " +
                    "account_id_fk int references account(account_id)," +
                    "toy_name text unique not null, " +
                    "toy_id_fk int references toy(toy_id));");
            ps8.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
