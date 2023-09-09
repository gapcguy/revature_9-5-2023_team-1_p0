package Utils;

import Controller.GatchaController;
import io.javalin.Javalin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Application {
    public static void main(String[] args) {
        databaseSetup(); // Cleanup suggestion: Read the comment block above the function definition pertaining to this.
        GatchaController gatchaControllerController = new GatchaController();
        Javalin app = gatchaControllerController.startAPI();
        app.start(8080);
    }

    // 0 usage. -- Remove if tests reveal it's not used.
    /*  public Account(int account_id, String username, String password, int coin_balance) {
        this.account_id = account_id;
        this.username   = username;
        this.password   = password;
        this.coin_balance = coin_balance;

    }*/

    /* Cleanup suggestion:
     *
     * As what's in resources/Gatcha.sql is a duplicate of the SQL queries, perhaps it might be worth it to
     * either replace the references to this function with Utils.ConnectionUtil.resetTestDatabase() (since its sole
     * existence is to read the SQL file, or just use this as a convenience function and reference
     * Utils.ConnectionUtil.resetTestDatabase() here (possibly refactoring the function definition to a more
     * appropriate "setupDatabase()." Thoughts?
     *
     * - Michael
     */
    public static void databaseSetup() {
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps1 = conn.prepareStatement("drop table if exists transaction, account, toy;");
            ps1.executeUpdate();
            PreparedStatement ps2 = conn.prepareStatement("create table account(" +
                    "account_id serial primary key, " +
                    "username text unique not null, " +
                    "password text not null," +
                    "coin_balance int Default 50 not null);");
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
                    "quantity int not null," +
                    "cost int not null default 50);");
            ps5.executeUpdate();
            PreparedStatement ps6 = conn.prepareStatement("insert into toy (name, quantity) Values" +
                    "('barbie', 10)," +
                    "('spongebob', 25), " +
                    "('fidget spinner', '4'), " +
                    "('gamecube', '100')" +
                    ",('sandy','0');");
            ps6.executeUpdate();
            /*
            PreparedStatement ps7 = conn.prepareStatement("drop table if exists transaction;");
            ps7.executeUpdate(); */
            PreparedStatement ps8 = conn.prepareStatement("create table transaction(" +
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
