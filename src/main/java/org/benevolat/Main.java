package org.benevolat;
import java.sql.*;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_032",
                    "projet_gei_032", "aJuju0pu");

            drop_database(connection);
            create_database(connection);
            fill_database(connection);
            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
    }

    // TODO gérer l'exception
    static void create_database(Connection connection) throws Exception{
        Statement statement;
        statement = connection.createStatement();
        statement.addBatch("CREATE TABLE user(id int primary key auto_increment, name varchar(50) not null, type varchar(20) not null check(type in (\"Voluntary\")));");
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

    static void fill_database(Connection connection) throws Exception{
        Statement statement;
        statement = connection.createStatement();
        String[] noms = {"Claude", "Charlie", "Charlotte", "Charles"};
        for (String nom : noms) {
            statement.addBatch("INSERT INTO user (name, type) VALUES (\"" + nom + "\", \"Voluntary\");");
            //System.out.println("INSERT INTO user (name, type) VALUES (\"" + nom + "\", \"Voluntary\");");
        }
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


    static void drop_database(Connection connection) throws Exception {
        Statement statement;
        statement = connection.createStatement();
        statement.addBatch("DROP TABLE user;");
        statement.executeBatch();
        statement.close();
    }
}