package presentation;

import business_logic.ClientBLL;
import business_logic.ProductBLL;
import data_access.ClientDAO;
import data_access.OrderDAO;
import data_access.ProductDAO;
import model.Bill;
import model.Client;
import model.Orders;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static model.Bill.recordBill;

public class GUI extends JFrame{

    private JButton orderButton=new JButton("Add order");
    private JButton clientButton=new JButton("View clients");
    private JButton clientManageButton=new JButton("Manage clients");
    private JButton productButton=new JButton("View products");
    private JButton productManageButton=new JButton("Manage products");
    private JFrame productManageFrame;
    private JFrame clientManageFrame;
    private JFrame productFrame;
    private JFrame clientFrame;
    public ClientBLL clientBLL=new ClientBLL();
    public ProductBLL productBLL=new ProductBLL();
    public ProductDAO productDAO=new ProductDAO();
    public ClientDAO clientDAO=new ClientDAO();

    public OrderDAO orderDAO=new OrderDAO();

    /**
     * UI class constructor
     */
    public GUI() {
        this.setBounds(100, 100, 400, 400);
        this.setTitle("Order management");
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        productButton.setBounds(100,70,200,25);
        this.getContentPane().add(productButton);

        productManageButton.setBounds(100,105,200,25);
        this.getContentPane().add(productManageButton);

        productButton.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                productFrame=new JFrame("Product table");
                productFrame.setBounds(100,100,600,600);
                ProductDAO productDAO=new ProductDAO();
                JTable table1=new JTable();
                table1=productDAO.generateTable();
                JScrollPane sp = new JScrollPane(table1);
                productFrame.add(sp);
                productFrame.setVisible(true);
            }
        });

        productManageButton.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {

                productFrame=new JFrame("Product table");
                productFrame.setBounds(700,100,600,600);
                ProductDAO productDAO=new ProductDAO();
                JTable table1=new JTable();
                table1=productDAO.generateTable();
                JScrollPane sp = new JScrollPane(table1);
                productFrame.add(sp);
                productFrame.setVisible(true);

                productManageFrame=new JFrame("Product management");
                productManageFrame.setBounds(100,100,600,600);
                productManageFrame.setLayout(null);

                JLabel addLabel = new JLabel("Add product");
                addLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
                addLabel.setHorizontalAlignment(SwingConstants.CENTER);
                addLabel.setBounds(260,20,100,30);
                productManageFrame.getContentPane().add(addLabel);

                JLabel nameLabel=new JLabel("Name: ");
                nameLabel.setBounds(25,80,100,20);
                productManageFrame.getContentPane().add(nameLabel);

                JTextField insertNameField=new JTextField();
                insertNameField.setBounds(150,83,120,20);
                productManageFrame.getContentPane().add(insertNameField);

                JLabel descriptionLabel=new JLabel("Description: ");
                descriptionLabel.setBounds(25,100,100,20);
                productManageFrame.getContentPane().add(descriptionLabel);

                JTextField insertDescField=new JTextField();
                insertDescField.setBounds(150,103,120,20);
                productManageFrame.getContentPane().add(insertDescField);

                JLabel priceLabel=new JLabel("Price: ");
                priceLabel.setBounds(25,120,100,20);
                productManageFrame.getContentPane().add(priceLabel);

                JTextField insertPriceField=new JTextField();
                insertPriceField.setBounds(150,123,120,20);
                productManageFrame.getContentPane().add(insertPriceField);

                JLabel stockLabel=new JLabel("Stock: ");
                stockLabel.setBounds(25,140,100,20);
                productManageFrame.getContentPane().add(stockLabel);

                JTextField insertStockField=new JTextField();
                insertStockField.setBounds(150,143,120,20);
                productManageFrame.getContentPane().add(insertStockField);

                JButton addProdButton=new JButton("Add product");
                addProdButton.setBounds(350,105,120,20);
                productManageFrame.getContentPane().add(addProdButton);

                addProdButton.addActionListener(new ActionListener()  {
                    public void actionPerformed(ActionEvent e) {
                        Product toAdd=new Product(1,insertNameField.getText(),insertDescField.getText(),Double.parseDouble(insertPriceField.getText()),Integer.parseInt(insertStockField.getText()));
                        if(!toAdd.assertProduct())
                            showMessage("Wrong product data");
                        else {
                            try {
                                showMessage("Product added!");
                                productDAO.insert(toAdd);
                                JTable table1=new JTable();
                                table1=productDAO.generateTable();
                                JScrollPane sp = new JScrollPane(table1);
                                productFrame.add(sp);
                                productFrame.setVisible(true);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                });


                JLabel deleteLabel = new JLabel("Delete product");
                deleteLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
                deleteLabel.setHorizontalAlignment(SwingConstants.CENTER);
                deleteLabel.setBounds(258,180,120,30);
                productManageFrame.getContentPane().add(deleteLabel);

                JLabel deleteId=new JLabel("ID for product to be deleted: ");
                deleteId.setBounds(25,240,180,20);
                productManageFrame.getContentPane().add(deleteId);

                JTextField deleteField=new JTextField();
                deleteField.setBounds(210,240,80,20);
                productManageFrame.getContentPane().add(deleteField);

                JButton deleteProdButton=new JButton("Delete product");
                deleteProdButton.setBounds(350,240,120,20);
                productManageFrame.getContentPane().add(deleteProdButton);

                deleteProdButton.addActionListener(new ActionListener()  {
                    public void actionPerformed(ActionEvent e) {
                        int id=Integer.parseInt(deleteField.getText());
                        try {
                            if(productBLL.findProductById(id)!=null) {
                                showMessage("Product deleted");
                                productDAO.delete(id);
                                JTable table1 = new JTable();
                                table1 = productDAO.generateTable();
                                JScrollPane sp = new JScrollPane(table1);
                                productFrame.add(sp);
                                productFrame.setVisible(true);
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });




                JLabel editLabel = new JLabel("Edit product");
                editLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
                editLabel.setHorizontalAlignment(SwingConstants.CENTER);
                editLabel.setBounds(260,340,100,30);
                productManageFrame.getContentPane().add(editLabel);

                JLabel editId=new JLabel("Select product ID to edit: ");
                editId.setBounds(25,390,160,20);
                productManageFrame.getContentPane().add(editId);

                JTextField editIdField=new JTextField();
                editIdField.setBounds(190,393,160,20);
                productManageFrame.getContentPane().add(editIdField);

                JLabel editName=new JLabel("New product name: ");
                editName.setBounds(25,410,160,20);
                productManageFrame.getContentPane().add(editName);

                JTextField editNameField=new JTextField();
                editNameField.setBounds(190,413,160,20);
                productManageFrame.getContentPane().add(editNameField);

                JLabel editDesc=new JLabel("New product description: ");
                editDesc.setBounds(25,430,160,20);
                productManageFrame.getContentPane().add(editDesc);

                JTextField editDescField=new JTextField();
                editDescField.setBounds(190,433,160,20);
                productManageFrame.getContentPane().add(editDescField);

                JLabel editPrice=new JLabel("New product price: ");
                editPrice.setBounds(25,450,160,20);
                productManageFrame.getContentPane().add(editPrice);

                JTextField editPriceField=new JTextField();
                editPriceField.setBounds(190,453,160,20);
                productManageFrame.getContentPane().add(editPriceField);

                JLabel editStock=new JLabel("New stock: ");
                editStock.setBounds(25,470,160,20);
                productManageFrame.getContentPane().add(editStock);

                JTextField editStockField=new JTextField();
                editStockField.setBounds(190,473,160,20);
                productManageFrame.getContentPane().add(editStockField);

                JButton editProduct=new JButton("Edit product");
                editProduct.setBounds(400,423,160,20);
                productManageFrame.getContentPane().add(editProduct);

                editProduct.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Product changed=productDAO.findById(Integer.parseInt(editIdField.getText()));
                        String newName=editNameField.getText();
                        String newInfo=editDescField.getText();
                        int newStock=0;
                        double newPrice=0;
                        if(!editStockField.getText().isEmpty())
                            newStock=Integer.parseInt(editStockField.getText());
                        if(!editPriceField.getText().isEmpty())
                            newPrice=Double.parseDouble(editPriceField.getText());
                        if(!newName.isEmpty()) changed.setProdName(newName);
                        else changed.setProdName(changed.getProdName());
                        if(!newInfo.isEmpty()) changed.setDescription(newInfo);
                        else changed.setDescription(changed.getDescription());
                        if(!editPriceField.getText().isEmpty()) changed.setPrice(newPrice);
                        else changed.setPrice(changed.getPrice());
                        if(!editStockField.getText().isEmpty()) changed.setStock(newStock);
                        else changed.setStock(changed.getStock());
                        try {
                            productDAO.update(changed);
                            showMessage("Product updated");
                            JTable table1=new JTable();
                            table1=productDAO.generateTable();
                            JScrollPane sp = new JScrollPane(table1);
                            productFrame.add(sp);
                            productFrame.setVisible(true);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (IllegalAccessException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });

                productManageFrame.setVisible(true);
            }
        });

        clientButton.setBounds(100,140,200,25);
        this.getContentPane().add(clientButton);

        clientManageButton.setBounds(100,175,200,25);
        this.getContentPane().add(clientManageButton);

        clientManageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clientFrame=new JFrame("Client table");
                clientFrame.setBounds(700,100,600,600);
                //ClientDAO clientDAO=new ClientDAO();
                JTable table1=new JTable();
                table1=clientDAO.generateTable();
                JScrollPane sp = new JScrollPane(table1);
                clientFrame.add(sp);
                clientFrame.setVisible(true);

                clientManageFrame=new JFrame("Client management");
                clientManageFrame.setBounds(100,100,600,600);
                clientManageFrame.setLayout(null);

                JLabel addLabel = new JLabel("Add client");
                addLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
                addLabel.setHorizontalAlignment(SwingConstants.CENTER);
                addLabel.setBounds(260,20,100,30);
                clientManageFrame.getContentPane().add(addLabel);

                JLabel nameLabel=new JLabel("Name: ");
                nameLabel.setBounds(25,80,100,20);
                clientManageFrame.getContentPane().add(nameLabel);

                JTextField insertNameField=new JTextField();
                insertNameField.setBounds(150,83,120,20);
                clientManageFrame.getContentPane().add(insertNameField);

                JLabel descriptionLabel=new JLabel("Contact info: ");
                descriptionLabel.setBounds(25,100,100,20);
                clientManageFrame.getContentPane().add(descriptionLabel);

                JTextField insertDescField=new JTextField();
                insertDescField.setBounds(150,103,120,20);
                clientManageFrame.getContentPane().add(insertDescField);


                JButton addProdButton=new JButton("Add client");
                addProdButton.setBounds(350,105,120,20);
                clientManageFrame.getContentPane().add(addProdButton);

                addProdButton.addActionListener(new ActionListener()  {
                    public void actionPerformed(ActionEvent e) {
                        Client toAdd=new Client(1,insertNameField.getText(),insertDescField.getText());
                        try {
                            clientDAO.insert(toAdd);
                            showMessage("Client added!");
                            JTable table1=new JTable();
                            table1=clientDAO.generateTable();
                            JScrollPane sp = new JScrollPane(table1);
                            clientFrame.add(sp);
                            clientFrame.setVisible(true);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });


                JLabel deleteLabel = new JLabel("Delete client");
                deleteLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
                deleteLabel.setHorizontalAlignment(SwingConstants.CENTER);
                deleteLabel.setBounds(258,180,120,30);
                clientManageFrame.getContentPane().add(deleteLabel);

                JLabel deleteId=new JLabel("ID for client to be deleted: ");
                deleteId.setBounds(25,240,180,20);
                clientManageFrame.getContentPane().add(deleteId);

                JTextField deleteField=new JTextField();
                deleteField.setBounds(210,240,80,20);
                clientManageFrame.getContentPane().add(deleteField);

                JButton deleteProdButton=new JButton("Delete client");
                deleteProdButton.setBounds(350,240,120,20);
                clientManageFrame.getContentPane().add(deleteProdButton);

                deleteProdButton.addActionListener(new ActionListener()  {
                    public void actionPerformed(ActionEvent e) {
                        int id=Integer.parseInt(deleteField.getText());
                        try {
                            if(clientBLL.findProductById(id)!=null) {
                                clientDAO.delete(id);
                                showMessage("Client deleted");
                                JTable table1=new JTable();
                                table1=clientDAO.generateTable();
                                JScrollPane sp = new JScrollPane(table1);
                                clientFrame.add(sp);
                                clientFrame.setVisible(true);
                            }
                            else showMessage("Incorrect Id");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });


                JLabel editLabel = new JLabel("Edit client");
                editLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
                editLabel.setHorizontalAlignment(SwingConstants.CENTER);
                editLabel.setBounds(260,340,100,30);
                clientManageFrame.getContentPane().add(editLabel);

                JLabel editId=new JLabel("Select ID for client to edit: ");
                editId.setBounds(25,390,160,20);
                clientManageFrame.getContentPane().add(editId);

                JTextField editIdField=new JTextField();
                editIdField.setBounds(190,393,160,20);
                clientManageFrame.getContentPane().add(editIdField);

                JLabel editName=new JLabel("New client name: ");
                editName.setBounds(25,418,160,20);
                clientManageFrame.getContentPane().add(editName);

                JTextField editNameField=new JTextField();
                editNameField.setBounds(190,421,160,20);
                clientManageFrame.getContentPane().add(editNameField);

                JLabel editDesc=new JLabel("New client contact: ");
                editDesc.setBounds(25,450,160,20);
                clientManageFrame.getContentPane().add(editDesc);

                JTextField editDescField=new JTextField();
                editDescField.setBounds(190,453,160,20);
                clientManageFrame.getContentPane().add(editDescField);

                JButton editClient=new JButton("Edit client");
                editClient.setBounds(400,423,160,20);
                clientManageFrame.getContentPane().add(editClient);

                editClient.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Client changed=clientDAO.findById(Integer.parseInt(editIdField.getText()));
                        String newName=editNameField.getText();
                        String newInfo=editDescField.getText();
                        if(!newInfo.isEmpty()) changed.setContactInfo(newInfo);
                        else changed.setContactInfo(changed.getContactInfo());
                        if(!newName.isEmpty()) changed.setName(newName);
                        else changed.setName(changed.getName());
                        try {
                            clientDAO.update(changed);
                            showMessage("Client updated");
                            JTable table1=new JTable();
                            table1=clientDAO.generateTable();
                            JScrollPane sp = new JScrollPane(table1);
                            clientFrame.add(sp);
                            clientFrame.setVisible(true);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (IllegalAccessException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                clientManageFrame.setVisible(true);

            }
        });

        clientButton.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                JFrame clientFrame=new JFrame("Client table");
                clientFrame.setBounds(100,100,600,600);
                ClientDAO clientDAO=new ClientDAO();
                JTable table1=new JTable();
                table1=clientDAO.generateTable();
                JScrollPane sp = new JScrollPane(table1);
                clientFrame.add(sp);
                clientFrame.setVisible(true);
            }
        });


        orderButton.setBounds(100,210,200,50);
        this.getContentPane().add(orderButton);


        orderButton.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                JFrame orderFrame = new JFrame("Make order");
                orderFrame.setLayout(null);
                orderFrame.setBounds(100, 100, 400, 400);
                JLabel customerID = new JLabel("CustomerID");
                customerID.setBounds(20, 20, 120, 20);
                orderFrame.getContentPane().add(customerID);
                JLabel product = new JLabel("Product");
                product.setBounds(20, 50, 120, 20);
                orderFrame.getContentPane().add(product);
                JLabel quantity = new JLabel("Quantity");
                quantity.setBounds(20, 80, 120, 20);
                orderFrame.getContentPane().add(quantity);
                String[] ids;
                try {
                    ids = clientBLL.getIDs();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JComboBox allIds = new JComboBox(ids);
                allIds.setBounds(120,20,120,20);
                orderFrame.getContentPane().add(allIds);

                String []products;
                products=productBLL.getProductNames();
                JComboBox productsList=new JComboBox(products);
                productsList.setBounds(120,50,120,20);
                orderFrame.getContentPane().add(productsList);

                int maxStock=productDAO.getQuantity(productsList.getItemAt(productsList.getSelectedIndex()).toString());
                String []quantities=new String[21];
                for(int i=0;i<20;i++)
                    quantities[i]= String.valueOf(i+1);
                JComboBox remainingStock=new JComboBox(quantities);
                remainingStock.setBounds(120,80,120,20);
                orderFrame.getContentPane().add(remainingStock);

                JButton makeOrderButton=new JButton("Make order");
                makeOrderButton.setBounds(200,140,100,20);
                orderFrame.getContentPane().add(makeOrderButton);

                makeOrderButton.addActionListener(new ActionListener()  {
                    public void actionPerformed(ActionEvent e) {
                        Orders order=new Orders(1,Integer.parseInt(allIds.getItemAt(allIds.getSelectedIndex()).toString()),productDAO.getIdByName(productsList.getItemAt(productsList.getSelectedIndex()).toString()),Integer.parseInt(remainingStock.getItemAt(remainingStock.getSelectedIndex()).toString()));
                        Bill b=new Bill(Integer.parseInt(allIds.getItemAt(allIds.getSelectedIndex()).toString()),productsList.getItemAt(productsList.getSelectedIndex()).toString(),Integer.parseInt(remainingStock.getItemAt(remainingStock.getSelectedIndex()).toString()),productDAO.getPrice(productsList.getItemAt(productsList.getSelectedIndex()).toString()));
                        double total=productDAO.getPrice(productsList.getItemAt(productsList.getSelectedIndex()).toString())*Integer.parseInt(remainingStock.getItemAt(remainingStock.getSelectedIndex()).toString());
                        try {
                            if(productDAO.updateStock(productsList.getItemAt(productsList.getSelectedIndex()).toString(),Integer.parseInt(remainingStock.getItemAt(remainingStock.getSelectedIndex()).toString()))) {
                                orderDAO.insert(order);
                                recordBill(b);
                                showMessage("Order registered! Your total is "+total);
                                productDAO.updateStock(productsList.getItemAt(productsList.getSelectedIndex()).toString(), Integer.parseInt(remainingStock.getItemAt(remainingStock.getSelectedIndex()).toString()));
                            }
                            else showMessage("Insufficient stock!");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });

                orderFrame.setVisible(true);
            }
        });

        this.setVisible(true);
    }
    public void showMessage(String msg){
        JOptionPane.showMessageDialog(null,msg);
    }
}
