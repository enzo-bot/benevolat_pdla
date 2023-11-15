package org.benevolat.controllers;

import org.benevolat.MultipleUsersInDBException;
import org.benevolat.NoUserFoundException;
import org.benevolat.UserAlreadyExistingException;
import org.benevolat.models.*;

import java.sql.*;

public class DBManager {
    private Connection connection;
    static private DBManager instance;
    private DBManager() {
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_032",
                    "projet_gei_032", "aJuju0pu");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static DBManager getInstance() {
        if (DBManager.instance == null) {
            DBManager.instance = new DBManager();
            //DBManager.instance.recreateDatabase();
        }
        return DBManager.instance;
    }

    protected void finalize() {
        try {
            this.connection.close();
        } catch (SQLException e) {

        }
    }

    public void recreateDatabase() {
        try {
            this.dropDatabase();
            this.createDatabase();
            this.fillDatabase();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // TODO gérer l'exception
    private void createDatabase() throws SQLException{
        Statement statement;
        statement = this.connection.createStatement();
        statement.addBatch("CREATE TABLE user(id int primary key auto_increment, " +
                "name varchar(50) not null, " +
                "password varchar(50) not null," +
                "type int not null check(type in (1,2,3)));");
        statement.executeBatch();
            /*int code;
            String title;
            while (resultSet.next()) {
                code = resultSet.getInt("code");
                title = resultSet.getString("title").trim();
                System.out.println("Code : " + code
                        + " Title : " + title);
            }
        resultSet.close();*/
        statement.close();
    }

     private void fillDatabase() throws SQLException{
        String[] names = {"Claude", "Charlie", "Charlotte", "Charles"};
        for (String name : names) {
            User user = new User(name, name, UserType.Voluntary);
            try {
                this.addUser(user);
            } catch (UserAlreadyExistingException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void dropDatabase() throws SQLException {
        Statement statement;
        statement = this.connection.createStatement();
        statement.addBatch("DROP TABLE user;");
        statement.executeBatch();
        statement.close();

    }

    public void addUser(User user) throws UserAlreadyExistingException {
        Statement statement;
        try {
            try {
                this.getUser(user.getName(), user.getPassword());
                throw new UserAlreadyExistingException();
            } catch (NoUserFoundException e) {
                statement = this.connection.createStatement();
                statement.addBatch("INSERT INTO user (name,password,type) VALUES (\"" + user.getName() + "\",\"" + user.getPassword() +"\", \"" + user.getType().getId() + "\");");
                statement.executeBatch();
                statement.close();
            } catch (MultipleUsersInDBException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public User getUser(String name, String password) throws NoUserFoundException,MultipleUsersInDBException,SQLException {
        Statement statement;
        ResultSet result;

        statement = this.connection.createStatement();
        result = statement.executeQuery("SELECT name,password,type FROM user WHERE name=\"" + name + "\" and password=\"" + password + "\";");

        if (!result.next()) {
            throw new NoUserFoundException();
        }

        UserType type = null;
        try {
            type = UserType.fromInt(result.getInt("type"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (result.next()) {
            throw new MultipleUsersInDBException();
        }

        statement.close();
        return new User(name,password,type);
    }

}
