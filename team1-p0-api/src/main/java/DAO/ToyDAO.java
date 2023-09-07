package DAO;

import Model.Toy;


import Utils.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToyDAO {
    public List<Toy> getAvailableToys(){
        List<Toy> toys = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM toy WHERE quantity > 0;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Toy toy = new Toy(rs.getInt("toy_id"),
                        rs.getString("name"),
                        rs.getInt("quantity"));
                toys.add(toy);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return toys;
    }


    public boolean decrementQuantity(int id,int prevQuantity){
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "UPDATE toy SET quantity = quantity-1 WHERE toy_id = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();


            sql = "SELECT * FROM toy WHERE toy_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                Toy toy = new Toy(rs.getInt("toy_id"),
                        rs.getString("name"),
                        rs.getInt("quantity"));
                return toy.getQuantity() == prevQuantity-1;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean updateToy(Toy toy){
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "UPDATE toy SET name = ?, quantity = ?, cost = ?, WHERE toy_id = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,toy.getToyName());
            preparedStatement.setInt(2, toy.getQuantity());
            preparedStatement.setInt(3, toy.getCost());
            preparedStatement.setInt(4, toy.getToy_id());
            preparedStatement.executeUpdate();


            sql = "SELECT * FROM toy WHERE toy_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, toy.getToy_id());
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                Toy toyU = new Toy(rs.getInt("toy_id"),
                        rs.getString("name"),
                        rs.getInt("quantity"));
                return (toyU.getToy_id() == toy.getToy_id() && toyU.getToyName().equals(toy.getToyName()) && toy.getQuantity() == toyU.getQuantity());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Toy getToyByID(int id){
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM toy WHERE toy_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return new Toy(rs.getInt("toy_id"),rs.getString("name"), rs.getInt("quantity"), rs.getInt("cost"));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    public void deleteToyById(int id){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "Delete from toy where toy_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteToyByName(String id){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "Delete from toy where name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
