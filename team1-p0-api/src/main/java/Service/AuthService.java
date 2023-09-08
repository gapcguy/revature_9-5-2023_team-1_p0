/* package Service;

import Model.Account;
import Model.LoginDTO;

public class AuthService {

    public Account login(String username, String password) {
        LoginDTO lDTO = new LoginDTO(username, password);
        // Assume we are calling a DAO method that gets an employee by username and password
        // if an employee is returned, we know it got valid login credentials.
        // Otherwise, there are no users with that username/password combo. Login failed!

        if(lDTO.getUser().equals(username)) {
            Account acct = new Account(username, password);

            return acct;
        } else {
            return null;
        }
    }
} */
