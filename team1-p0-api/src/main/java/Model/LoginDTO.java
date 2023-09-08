package Model;
// DATA TRANSFER OBJECT for login credentials
public class LoginDTO {
    private String username;
    private String password;

    //ALL ARGS + GETTERS/SETTERS

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUser() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
