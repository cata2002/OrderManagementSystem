package business_logic;

import data_access.ProductDAO;
import model.Product;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    private ProductDAO productDAO=new ProductDAO();

    public ProductBLL() {
    }

    public Product findProductById(int id) {
        Product st = productDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }
        return st;
    }

    public List<Product> getAllProducts() {
        List<Product> st = productDAO.findAll();
        if (st == null) {
            throw new NoSuchElementException("The product was not found!");
        }
        return st;
    }

    /**
     *
     * @return String[]
     * generates an array of strings, representing all products, using the findall method
     */
    public String[] getProductNames() {
        List<Product> st = productDAO.findAll();
        String []p=new String[st.size()];
        for(int i=0;i< st.size();i++){
            p[i]=st.get(i).getProdName();
        }
        return p;
    }
}
