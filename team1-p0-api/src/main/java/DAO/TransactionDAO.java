package DAO;

<<<<<<< Updated upstream
import Model.Account;
import Model.Transaction;

=======
import Model.Toy;
import Model.Transaction;
import Utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
>>>>>>> Stashed changes
import java.util.List;

public class TransactionDAO {

<<<<<<< Updated upstream
    public Transaction pull(Account account){
        return null;
    }

    public List<Transaction> myToys(Account account){
        return null;
    }
=======
    public TransactionDAO(){
    }

    public boolean AddTransaction(Transaction t){
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "Insert into transaction values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, t.getTransaction_id());
            preparedStatement.setInt(2, t.getAccount_id());
            preparedStatement.setInt(3, t.getToy_id());
            ResultSet rs = preparedStatement.executeQuery();
            return true;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Transaction> GetTransactionsByAccountID(int id){
        List<Transaction> Ts = new ArrayList<Transaction>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM transaction WHERE account_id_fk = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Transaction t = new Transaction(rs.getInt("transaction_id"), rs.getInt("account_id_fk"), rs.getInt("toy_id_fk"));
                Ts.add(t);
            }
            return Ts;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return Ts;
    }

    public List<Transaction> GetTransactionsByToyID(int id){
        List<Transaction> Ts = new ArrayList<Transaction>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM transaction WHERE toy_id_fk = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Transaction t = new Transaction(rs.getInt("transaction_id"), rs.getInt("account_id_fk"), rs.getInt("toy_id_fk"));
                Ts.add(t);
            }
            return Ts;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return Ts;
    }

    public Transaction GetTransactionByID(int id){
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM transaction WHERE transaction_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return new Transaction(rs.getInt("transaction_id"), rs.getInt("account_id_fk"), rs.getInt("toy_id_fk"));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

>>>>>>> Stashed changes

}
