package Utils;

import org.h2.tools.RunScript;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//This Class is where we manage and establish our database connection
public class ConnectionUtil {

    // URL to RDS connection
    private static final String url = "jdbc:postgresql://p0demodatabase.ccn4nz832krl.us-east-2.rds.amazonaws.com:5432/postgres";

    // RDS credentials
    private static final String username = "team1";
    private static final String password = "password";

    // Static object which represents the connection to RDS. Since this is static, any DAO interacting with this
    // connection object refers back to here. 
    private static Connection connection = null;

    //This method will eventually return an object of type Connection, which we'll use to connect to our databse
    public static Connection getConnection() throws SQLException {

        //For compatibility with other technologies/frameworks, we'll need to register our PostgreSQL driver
        //This process makes the application aware of what Driver class we're using
        try {
            Class.forName("org.postgresql.Driver"); //searching for the postgres driver, which we have as a dependency
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); //This tells in the console us what went wrong
            System.out.println("problem occurred locating driver");
        }


        //Use our database credentials to establish a database connection
        //Hardcoded for now - It's possible hide them in the Environment Variables, feel free to look into it

        //I'm going to put the credentials in Strings, and use those strings in a method that gets connections

        /* these values get changed */


        //the above variables^^ aren't completely necessary, I'm just laying them out for clarity

        //This return statement is what returns out actual database Connection object
        //Note how this getConnection() method has a return type of Connection
        return DriverManager.getConnection(url, username, password);

    }

    public static void resetTestDatabase() throws SQLException {
        // If no connection exists, use the getConnection method to set it up.
        if(connection == null) {
            getConnection();
        } else {
            try {
                FileReader sqlRead = new FileReader("src/main/resources/Gatcha.sql");
                RunScript.execute(connection, sqlRead);
            } catch (SQLException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}