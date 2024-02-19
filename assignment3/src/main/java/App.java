import business_logic.ClientBLL;
import business_logic.ProductBLL;
import data_access.ClientDAO;
import data_access.OrderDAO;
import data_access.ProductDAO;
import model.Bill;
import model.Client;
import model.Orders;
import model.Product;
import presentation.GUI;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static model.Bill.recordBill;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        GUI ui=new GUI();
    }
}
