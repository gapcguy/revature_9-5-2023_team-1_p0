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


            sql = "SELECT * FROM message WHERE message_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Toy toy = new Toy(rs.getInt("toy_id"),
                        rs.getString("name"),
                        rs.getInt("quantity"));
                return toy.quantity == prevQuantity-1;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
