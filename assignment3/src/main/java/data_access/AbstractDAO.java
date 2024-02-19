package data_access;

import connection.ConnectionFactory;
import model.Client;
import model.Orders;
import model.Product;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AbstractDAO<T> {
    private final Class<T> type;

    public AbstractDAO() {
        type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     *
     * @param id int
     * @return T
     * returns an entry based on its id
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<T> results = createObjects(resultSet);
            if(results.get(0)!=null)return results.get(0);
        } catch (SQLException e) {
            System.out.println("couldnt connect");
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     *
     * @param id integer
     * @throws SQLException
     * delete an entry based on the id
     */
    public void delete(int id) throws SQLException {
        String query="DELETE FROM "+ type.getSimpleName()+ " WHERE id="+id;
        Connection connection=ConnectionFactory.getConnection();
        PreparedStatement statement=connection.prepareStatement(query);
        statement.executeUpdate();
    }

    /**
     *
     * @param t generic type
     * @throws SQLException
     * @throws IllegalAccessException
     * updates fields of type given as parameter
     */
    public void update(T t) throws SQLException, IllegalAccessException {
        Connection connection = null;
        PreparedStatement statement = null;
        String query;
        StringBuilder genQ=new StringBuilder();
        genQ.append("UPDATE "+type.getSimpleName()+" SET ");
        for(Field f: type.getDeclaredFields()){
            genQ.append(f.getName()+" = "+"?,");
        }
        genQ.deleteCharAt(genQ.lastIndexOf(","));
        genQ.append(" WHERE id = ?");
        query=genQ.toString();
        connection=ConnectionFactory.getConnection();
        statement= connection.prepareStatement(query);
        int index=1;
        for(Field f: type.getDeclaredFields()){
            f.setAccessible(true);
            Object obj=f.get(t);
            statement.setObject(index,obj);
            index++;
        }
        Field[] fields= type.getDeclaredFields();
        fields[0].setAccessible(true);
        statement.setInt(index, (Integer) fields[0].get(t));
        statement.executeUpdate();
    }

    /**
     *
     * @param o Object
     * @throws SQLException
     *  Inserts an object into a table
     */
    public void insert(Object o) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String query="INSERT INTO "+type.getSimpleName()+" VALUES(DEFAULT,?,?)";
        String query1="INSERT INTO "+type.getSimpleName()+" VALUES(DEFAULT,?,?,?,?)";
        String query2="INSERT INTO "+type.getSimpleName().toLowerCase()+" VALUES(DEFAULT,?,?,?)";
        if(Client.class.isInstance(o)){
            connection=ConnectionFactory.getConnection();
            statement= connection.prepareStatement(query);
            statement.setString(1,((Client) o).getName());
            statement.setString(2,((Client) o).getContactInfo());
            statement.executeUpdate();
        }
        if(Product.class.isInstance(o)){
            connection=ConnectionFactory.getConnection();
            statement= connection.prepareStatement(query1);
            statement.setString(1,((Product) o).getProdName());
            statement.setString(2,((Product) o).getDescription());
            statement.setDouble(3,((Product) o).getPrice());
            statement.setInt(4,((Product) o).getStock());
            statement.executeUpdate();
        }
        if(Orders.class.isInstance(o)){
            connection=ConnectionFactory.getConnection();
            statement= connection.prepareStatement(query2);
            statement.setInt(1,((Orders) o).getProductId());
            statement.setInt(2,((Orders) o).getCustomerId());
            statement.setInt(3,((Orders) o).getQuantity());
            statement.executeUpdate();
        }
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);
    }

    /**
     *
     * @return List<T>
     *     returns a list with all entries of a table in the database
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM "+type.getSimpleName();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            List<T> results = createObjects(resultSet);
            return results;
        } catch (SQLException e) {
            System.out.println("couldnt connect");
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     *
     * @param resultSet
     * @return List<T>
     *     creates a list of objects based on the type given at runtime
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>(20);
        Constructor[] ctors = type.getConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<T> getIds() throws SQLException {
        String query="SELECT id FROM "+type.getSimpleName();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        connection=ConnectionFactory.getConnection();
        statement= connection.prepareStatement(query);
        resultSet=statement.executeQuery();
        return createObjects(resultSet);
    }

    /**
     *
     * @return JTable
     *  generates a JTable with all the entries in a table of a database
     */
    public JTable generateTable(){
        List<T> data=findAll();
        Object[][] contents=new Object[data.size()][10];
        String [] columns = new String[10];
        int indexO=-1;
        JTable table=new JTable();
        if(!data.isEmpty()){
            columns=new String[data.get(0).getClass().getDeclaredFields().length];
            int index=0;
            for(Field f:data.get(0).getClass().getDeclaredFields()){
                f.setAccessible(true);
                columns[index++]=f.getName();
            }
            for(Object o:data){
                //indexO=0;
                int index1=0;
                indexO++;
                contents[indexO]=new Object[data.get(0).getClass().getDeclaredFields().length];
                for(Field f:o.getClass().getDeclaredFields()){
                    f.setAccessible(true);
                    try {
                        contents[indexO][index1++]=f.get(o);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        table= new JTable(contents,columns);
        table.setVisible(true);
        table.setEnabled(true);
        return table;
    }
}
