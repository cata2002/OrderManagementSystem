package model;

public class Product {
    private int id;
    private String prodName;
    private String description;
    private double price;
    private  int stock;

    public Product() {
    }

    /**
     *
     * @param id
     * @param prodName
     * @param description
     * @param price
     * @param stock
     * product constructor
     */
    public Product(int id, String prodName, String description, double price, int stock) {
        this.id = id;
        this.prodName = prodName;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", prodName='" + prodName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }

    /**
     *
     * @return boolean
     * checks if product price and stock are correct(>0)
     */
    public boolean assertProduct(){
        if(this.price<0) return false;
        if(this.stock<0) return false;
        return true;
    }
}
