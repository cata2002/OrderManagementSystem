package data_access;

import connection.ConnectionFactory;
import model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductDAO extends AbstractDAO<Product> {
    /**
     *
     * @param name
     * @return int
     *
     * return product quantity based on its name
     */
    public int getQuantity(String name) {
        Connection connection = ConnectionFactory.getConnection();
        String query = "SELECT stock FROM product WHERE prodname = '" + name + "'";
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs;
        try {
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if(rs.next()) return rs.getInt("stock");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     *
     * @param name
     * @return double
     * return product price based on its name
     */
    public double getPrice(String name) {
        Connection connection = ConnectionFactory.getConnection();
        String query = "SELECT price FROM product WHERE prodname = '" + name + "'";
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs;
        try {
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if(rs.next()) return rs.getDouble("price");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     *
     * @param name
     * @return int
     * returns product id based on its name
     */
    public int getIdByName(String name) {
        Connection connection = ConnectionFactory.getConnection();
        String query = "SELECT id FROM product WHERE prodname = '" + name + "'";
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs;
        try {
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if(rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     *
     * @param prodName
     * @param quantityOrdered
     * @return boolean
     * @throws SQLException
     *
     * updates remaining stock based on the quantity ordered
     */
    public boolean updateStock(String prodName, int quantityOrdered) throws SQLException {
        int currentStock=this.getQuantity(prodName);
        Connection connection=ConnectionFactory.getConnection();
        String query="UPDATE product SET stock="+(currentStock-quantityOrdered)+" where prodname='"+prodName+"';";
        if(currentStock-quantityOrdered<0) return false;
        Statement statement= connection.createStatement();
        statement.executeUpdate(query);
        return true;
    }
}
