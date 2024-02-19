package model;

import connection.ConnectionFactory;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @param id
 * @param prodName
 * @param quantity
 * @param price
 * record to store bills for orders
 */
public record Bill(int id, String prodName, int quantity, double price){
    public static void recordBill(Bill b) throws SQLException {
        Connection connection= ConnectionFactory.getConnection();
        Statement statement= connection.createStatement();
        String query="INSERT INTO bill VALUES("+b.id+",'"+b.prodName+"',"+b.quantity+","+b.price+")";
        statement.executeUpdate(query);
    }
};
