package org.benevolat;

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
        catch (Exception exception) {
            System.out.println(exception);
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

    void recreate_database() throws Exception{
        this.drop_database();
        this.create_database();
        this.fill_database();
    }

    // TODO gérer l'exception
    void create_database() throws Exception{
        Statement statement;
        statement = this.connection.createStatement();
        statement.addBatch("CREATE TABLE user(id int primary key auto_increment, " +
                "name varchar(50) not null, " +
                "password varchar(50) not null," +
                "type varchar(20) not null check(type in (\"Voluntary\")));");
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

     void fill_database() throws Exception{
        String[] noms = {"Claude", "Charlie", "Charlotte", "Charles"};
        for (String nom : noms) {
            this.add_user(nom,nom, "Voluntary");
            //System.out.println("INSERT INTO user (name, type) VALUES (\"" + nom + "\", \"Voluntary\");");
        }
    }


    void drop_database() throws Exception {
        Statement statement;
        statement = this.connection.createStatement();
        statement.addBatch("DROP TABLE user;");
        statement.executeBatch();
        statement.close();
    }

    void add_user(String name, String password, String type) throws Exception {
        Statement statement;
        statement = this.connection.createStatement();
        statement.addBatch("INSERT INTO user (name,password,type) VALUES (\"" + name + "\",\"" + password +"\", \"" + type + "\");");
        statement.executeBatch();
        statement.close();
    }
}
