package DAO;


import Model.Account;
import Model.Transaction;
import Model.Toy;
import Utils.ConnectionUtil;

<<<<<<< Updated upstream
import Model.Toy;
import Model.Transaction;
import Utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

=======
import java.sql.*;
import java.util.ArrayList;
>>>>>>> Stashed changes
import java.util.List;
import java.util.Random;

import DAO.AccountDAO;

public class TransactionDAO {

    AccountDAO accountDAO;
    ToyDAO toyDAO;

    public TransactionDAO(){
        this.accountDAO = new AccountDAO();
        this.toyDAO = new ToyDAO();
    }

    private Toy chooseRandomToy(){
        List<Toy> treasureChest = toyDAO.getAvailableToys();
        int upperbound = treasureChest.size();
        Random rand = new Random();
        return treasureChest.get(rand.nextInt(upperbound));
    }

    private Transaction addTransaction(Transaction transaction){
        try ( Connection connection = ConnectionUtil.getConnection() ){
            String sql = "INSERT INTO transaction (account_id_fk,toy_name,toy_id_fk) VALUES (?,?, ?, ?);";
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

    public Transaction pull(Account account) throws Exception {
        accountDAO.decreaseCoinBalance(account,Transaction.getPullCost());
        Toy newToy = chooseRandomToy();
        boolean working = toyDAO.decrementQuantity(newToy.getToy_id(), newToy.getQuantity());
        if(!working){ throw new Exception("not able to pull toy :(");};
        Transaction curr = new Transaction(account.getAccount_id(),newToy.getToy_id(),newToy.getToyName());
        if(curr == null){ throw new Exception("not able to add transaction");};
        return curr;
    }

    public List<Toy> myToys(Account account){

        List<Toy> toys = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT toy_id_fk,toy_name,COUNT(transaction_id) FROM transaction WHERE account_id_fk = ? GROUP BY toy_id_fk";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            preparedStatement.setInt(1,account.getAccount_id());
            while(rs.next()){
                Toy toy = new Toy(rs.getInt("toy_id_fk"),
                        rs.getString("toy_name"),
                        rs.getInt("COUNT(transaction_id)"));
                toys.add(toy);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return toys;
    }
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


}
