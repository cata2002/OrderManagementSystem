package business_logic;

import data_access.ClientDAO;
import data_access.ProductDAO;
import model.Client;
import model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    private ClientDAO clientDAO=new ClientDAO();

    public ClientBLL() {
    }

    /**
     *
     * @return List<Client>
     *     returns list of all clients
     */
    public List<Client> getAllClients() {
        List<Client> st = clientDAO.findAll();
        if (st == null) {
            throw new NoSuchElementException("The client was not found!");
        }
        return st;
    }

    /**
     *
     * @return String[]
     * @throws SQLException
     * generates list of all ids from table, to put in comboBox in GUI
     */
    public String[] getIDs() throws SQLException {
        List<Client> st = clientDAO.findAll();
        String []results=new String[st.size()];
        for(int i=0;i< st.size();i++) {
            results[i]=String.valueOf(st.get(i).getId());
        }
        return results;
    }

    public Client findProductById(int id) {
        Client st = clientDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }
        return st;
    }
}
