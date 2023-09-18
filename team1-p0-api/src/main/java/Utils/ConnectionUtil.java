package Utils;

import Service.AccountService;
import org.h2.tools.RunScript;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//This Class is where we manage and establish our database connection
public class ConnectionUtil {

    // Connection information for VPS.
    private static final String url = "jdbc:mariadb://revature.michaelwarner.info/revature_p0";

    // RDS credentials
    private static final String username = "team1";
    private static final String password = "u~96db0M2";

    /*
        // URL to RDS connection
        private static final String url = "jdbc:postgresql://p0demodatabase.ccn4nz832krl.us-east-2.rds.amazonaws.com:5432/postgres";

        // RDS credentials
        private static final String username = "team1";
        private static final String password = "password";
    */
    // Static object which represents the connection to RDS. Since this is static, any DAO interacting with this
    // connection object refers back to here.
    private static Connection connection = null;

    //This method will eventually return an object of type Connection, which we'll use to connect to our databse
    public static Connection getConnection() throws SQLException {

        //For compatibility with other technologies/frameworks, we'll need to register our PostgreSQL driver
        //This process makes the application aware of what Driver class we're using
        try {
            //Class.forName("org.postgresql.Driver"); //searching for the postgres driver, which we have as a dependency
            Class.forName("org.mariadb.jdbc.Driver"); // Updated from PostgreSQL to MariaDB for VPS compatibility
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

        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    public static void resetTestDatabase() throws SQLException {
        // If no connection exists, use the getConnection method to set it up.
        if (connection == null) {
            getConnection();
        }
        try {
            FileReader sqlRead = new FileReader("src/main/resources/Gatcha.sql");
            // System.out.println(sqlRead);                                                                             // commented out. Unneeded for functionality.
            RunScript.execute(connection, sqlRead);
            sqlRead.close();
        } catch (SQLException | IOException e) {                                                                        // changed from FileNotFoundException to IOException.
            e.printStackTrace();                                                                                        // IOException covers a slightly more broad scope of errors, and
        }                                                                                                               // is necessary to close the file.
    }

    public static void testLogin(HttpClient httpClient, String username, String password) throws IOException, InterruptedException {
        AccountService as = new AccountService();
        if (as.userExists(username)){
            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/account/login"))
                    .POST(HttpRequest.BodyPublishers.ofString("{" +
                            "\"username\": \"" + username + "\", " +
                            "\"password\": \"" + password + "\" }"))
                    .header("Content-Type", "application/json")
                    .build();

            httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        }else{
            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/account/register"))
                    .POST(HttpRequest.BodyPublishers.ofString("{" +
                            "\"username\": \"user13\", " +
                            "\"password\": \"password\" }"))
                    .header("Content-Type", "application/json")
                    .build();
            httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        }



    }
}