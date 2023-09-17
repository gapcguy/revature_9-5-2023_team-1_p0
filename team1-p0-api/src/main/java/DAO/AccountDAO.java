package DAO;

import Model.Account;
import Utils.ConnectionUtil;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class AccountDAO {
    public Account createAccount(Account account) {
        try ( Connection connection = ConnectionUtil.getConnection() ){
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO account (username, password, coin_balance) VALUES (?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.setInt   (3, 50);

            ps.executeUpdate();
            ResultSet key = ps.getGeneratedKeys();

            if (key.next()) {
                int id = (int) key.getLong(1);
                return new Account(id, account.getUsername(), account.getPassword(), account.getCoinBalance());
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean checkUser(String username){

        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM Account where username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Account getUserAccount(Account account) {
        try( Connection connection = ConnectionUtil.getConnection() ) {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM account where username = ? AND password = ?"
            );

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ResultSet rs = ps.executeQuery();


            if (rs.next()) {
                int    accountId    = rs.getInt   ("account_id"  );
                String username     = rs.getString("username"    );
                String password     = rs.getString("password"    );
                int    coin_balance = rs.getInt   ("coin_balance");

                return new Account(accountId, username, password, coin_balance);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public static boolean increaseCoinBalance(Account account, int amountToAdd) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE account SET coin_balance = coin_balance + ? WHERE username = ?"
            );

            ps.setInt   (1, amountToAdd          );
            ps.setString(2, account.getUsername());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean decreaseCoinBalance(Account account, int costToPull){
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE account SET coin_balance = coin_balance - ? WHERE username = ?"
            );

            ps.setInt   (1, costToPull);
            ps.setString(2, account.getUsername());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public Account deleteAccountByName(String Name){
        try(Connection conn = ConnectionUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement("DELETE FROM account WHERE username = ?");
            ps.setString(1, Name);
            ps.executeUpdate();
            return new Account();
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Account> getAllUsers(){
        try(Connection c = ConnectionUtil.getConnection()){
            PreparedStatement ps = c.prepareStatement("SELECT * FROM account");
            ResultSet rs = ps.executeQuery();
            ArrayList<Account> al = new ArrayList<>();
            while(rs.next()) {
                al.add(
                        new Account(
                                rs.getInt   ("account_id"  ),
                                rs.getString("username"    ),
                                rs.getString("password"    ),
                                rs.getInt   ("coin_balance")
                        )
                );
            }
            return al;
        } catch(SQLException e) { e.printStackTrace(); }
        return null;
    }

    /* unused functions

    public void deleteAccountById(int id){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "Delete from Account where account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
       public boolean updateAccount(Account account){
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "UPDATE account SET username = ?, password = ?, coin_balance = ? WHERE toy_id = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.setInt(3, account.getCoinBalance());
            preparedStatement.executeUpdate();


            sql = "SELECT * FROM toy WHERE toy_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account.getAccount_id());
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                Account account1 = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("coin_balance"));
                return (account1.getAccount_id() == account.getAccount_id() &&
                        account1.getUsername().equals(account.getUsername()) &&
                        account1.getPassword().equals(account.getPassword()) &&
                        account1.getCoinBalance() == account.getCoinBalance());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

     */
}
