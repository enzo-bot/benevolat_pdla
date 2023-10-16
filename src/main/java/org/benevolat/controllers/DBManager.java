package org.benevolat.controllers;

import org.benevolat.models.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
        }
        return DBManager.instance;
    }

    protected void finalize() {
        try {
            this.connection.close();
        } catch (SQLException e) {

        }
    }

    public void recreate_database() {
        try {
            this.drop_database();
            this.create_database();
            this.fill_database();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // TODO gérer l'exception
    private void create_database() throws SQLException{
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

     private void fill_database() throws SQLException{
        String[] names = {"Claude", "Charlie", "Charlotte", "Charles"};
        for (String name : names) {
            User user = new User(name, name, UserType.Voluntary);
            this.add_user(user);
        }
    }


    private void drop_database() throws SQLException {
        Statement statement;
        statement = this.connection.createStatement();
        statement.addBatch("DROP TABLE user;");
        statement.executeBatch();
        statement.close();

    }

    public void add_user(User user) {
        Statement statement;
        try {
            statement = this.connection.createStatement();
            statement.addBatch("INSERT INTO user (name,password,type) VALUES (\"" + user.getName() + "\",\"" + user.getPassword() +"\", \"" + user.getType().ordinal() + "\");");
            statement.executeBatch();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
