package DAO;

import Model.Account;
import Model.Toy;
import Utils.ConnectionUtil;

import javax.xml.transform.Result;
import java.sql.*;

public class AccountDAO {
    public Account createAccount(Account account) {
        try ( Connection connection = ConnectionUtil.getConnection() ){
            String sql = "INSERT INTO Account (username, password, coin_balance) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.setInt(3, 50);

            ps.executeUpdate();
            ResultSet key = ps.getGeneratedKeys();

            if (key.next()) {
                int id = (int) key.getLong(1);
                return new Account(id, account.getUsername(), account.getPassword(), account.getCoinBalance());
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Account getUserAccount(Account account) {
        try(Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM Account where username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int accountId = rs.getInt("account_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int coin_balance = rs.getInt("coin_balance");
                return new Account(accountId, username, password, coin_balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public boolean increaseCoinBalance(Account account, int amountToAdd) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "UPDATE Account SET coin_balance = coin_balance + ? WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, amountToAdd);
            ps.setString(2, account.getUsername());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean decreaseCoinBalance(Account account, int costToPull){
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "UPDATE Account SET coin_balance = coin_balance - ? WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, costToPull);
            ps.setString(2, account.getUsername());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // May not be used. We may want to consider removing this on a final/cleanup push.
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
    public Account deleteAccountByName(String Name){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "Delete from Account where username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Name);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
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
}

