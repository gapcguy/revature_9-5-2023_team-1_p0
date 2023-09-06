package Model;

public class Account {

    public int account_id;
    public String username, password;


    // Default argument-free constructor.
    public Account() {}

    /*  Constructor overload - Used for when creating a new account. */
    public Account(String username, String Password) {
        this.username = username;
        this.password = password;
    }

    /* Constructor overload - used for when retrieving an account. */
    public Account(int account_id, String username, String password) {
        this.account_id = account_id;
        this.username   = username;
        this.password   = password;

    }

    // Getters
    /* Getter method for getting an account ID */
    public int getAccount_id() { return account_id; }

    /* Getter method for getting the user name */
    public String getUsername() { return username; }

    /* Getter method for getting the password */
    public String getPassword() { return password; }

    // Setters
    /* Setter method for the account id */
    public void setAccount_id(int account_id) { this.account_id = account_id; }

    /* Setter method for the username */
    public void setUsername(String username) { this.username = username; }

    /* Setter method for the password */
    public void setPassword(String password) { this.password = password; }


    // Override of the default equals() function. Checks when two objects are identical. Allows Assert.assertEquals
    // and List.contains to function.
    @Override
    public boolean equals(Object o) {
        if (this == o)                               { return true;  }
        if (o == null || getClass() != o.getClass()) { return false; }

        Account account = (Account) o;

        return account_id == account.account_id && username.equals(account.username) && password.equals(account.password);
    }

    // Overrides the default toString() method to allow for a string representation of the Accounts class.
    @Override
    public String toString() {
        return "Account{" + "account_id=" + account_id + ", username='" + username + '\'' + ", password='" + password + '\'' + '}';
    }

}
