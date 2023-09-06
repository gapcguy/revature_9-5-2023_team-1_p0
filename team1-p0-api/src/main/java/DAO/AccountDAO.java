package DAO;

import Model.Account;
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
            ps.setInt(3, account.getCoinBalance());

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
            String sql = "SELECT account_id, username, coin_balance FROM Account where username = ? AND password = ?";
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
}

