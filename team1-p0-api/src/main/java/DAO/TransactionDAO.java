package DAO;


import Model.Account;
import Model.Transaction;
import Model.Toy;
import Utils.ConnectionUtil;

import Model.Toy;
import Model.Transaction;
import Utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.*;

import java.util.List;
import java.util.Random;      // 0 usage. -- Remove if tests reveal it's not used.


public class TransactionDAO {

    AccountDAO accountDAO;
    ToyDAO toyDAO;

    public TransactionDAO(){
        this.accountDAO = new AccountDAO();
        this.toyDAO = new ToyDAO();
    }



    public Transaction addTransaction(Transaction transaction){
        try ( Connection connection = ConnectionUtil.getConnection() ){
            String sql = "INSERT INTO transaction (account_id_fk,toy_name,toy_id_fk) VALUES (?,?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, transaction.getAccount_id());
            ps.setString(2, transaction.getToyName());
            ps.setInt(3, transaction.getToy_id());

            ps.executeUpdate();
            ResultSet key = ps.getGeneratedKeys();

            if (key.next()) {
                int id = (int) key.getLong(1);
                return new Transaction(id, transaction.getAccount_id(), transaction.getToy_id(), transaction.getToyName());
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Toy> myToys(Account account){

        List<Toy> toys = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT toy_name,COUNT(transaction_id) FROM transaction WHERE account_id_fk = ? GROUP BY toy_name;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,account.getAccount_id());
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Toy toy = new Toy(
                        rs.getString("toy_name"),
                        rs.getInt("COUNT"));
                toys.add(toy);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return toys;
    }

    // 0 usage. -- Remove if tests reveal it's not used.
    public boolean deleteTransaction(int id){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "Delete from transaction where transaction_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTransaction(Transaction t){
        boolean b = false;
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "Update transaction set account_id_fk = ?, toy_id_fk = ?, where transaction_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, t.getAccount_id());
            ps.setInt(2, t.getToy_id());
            ps.setInt(3, t.getTransaction_id());
            ps.executeUpdate();
            b = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return b;
    }

    public List<Toy> getToysFromAccountId(int id) {
        List<Toy> Ts = new ArrayList<Toy>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT " +
                    "t.toy_id_fk, " +
                    "COUNT(t.transaction_id) AS transaction_count," +
                    "toys.name, " +
                    "toys.cost," +
                    "toys.quantity " +
                    "FROM " +
                    "transaction AS t " +
                    "JOIN " +
                    "toy AS toys " +
                    "ON t.toy_id_fk = toys.toy_id WHERE " +
                    "t.account_id_fk = ? " +
                    "GROUP BY " +
                    "t.toy_id_fk, toys.name, toys.quantity, toys.cost;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Toy t = new Toy(rs.getInt("toy_id_fk"), rs.getString("name"), rs.getInt("quantity"), rs.getInt("cost"));
                Ts.add(t);
            }
            return Ts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
