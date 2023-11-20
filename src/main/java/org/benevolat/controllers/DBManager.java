package org.benevolat.controllers;

import org.benevolat.InvalidUserTypeIdException;
import org.benevolat.MultipleUsersInDBException;
import org.benevolat.NoUserFoundException;
import org.benevolat.UserAlreadyExistingException;
import org.benevolat.models.*;

import java.sql.*;
import java.util.ArrayList;

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

        statement.addBatch("CREATE TABLE request(FOREIGN KEY (`id_user`) REFERENCES user(`id`)," +
                "id int auto_increment, " +
                "title varchar(50) not null, " +
                "content varchar(250) not null," +
                "primary key(id_user, id));");
        statement.executeBatch();
        statement.close();
    }

     private void fillDatabase() throws SQLException{
        String[] names = {"Claude", "Charlie", "Charlotte", "Charles"};
        for (String name : names) {
            User user = new User(name, name, UserType.Asker);
            Request r = new Request(user, "Ménage", "Faire le ménage");
            try {
                this.addUser(user);
            } catch (UserAlreadyExistingException e) {
                throw new RuntimeException(e);
            }
            try {
                this.addRequest(r);
            } catch (NoUserFoundException | MultipleUsersInDBException e) {
                throw new RuntimeException(e);
            }
        }


    }


    private void dropDatabase() throws SQLException {
        Statement statement;
        statement = this.connection.createStatement();
        statement.addBatch("DROP TABLE user;");
        statement.addBatch("DROP TABLE request;");
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
                statement.addBatch("INSERT INTO user (name,password,type) VALUES (\"" + user.getName() +
                        "\",\"" + user.getPassword() +"\", \"" + user.getType().getId() + "\");");
                statement.executeBatch();
                statement.close();
            } catch (MultipleUsersInDBException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addRequest(Request request) throws NoUserFoundException,MultipleUsersInDBException {
        Statement statement;
        try {
            statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT id FROM user WHERE name=\"" + request.getAsker().getName() +
                    "\" and password=\"" + request.getAsker().getPassword() + "\";");
            if (!result.next()) {
                throw new NoUserFoundException();
            }
            statement.close();


            statement = this.connection.createStatement();
            statement.addBatch("INSERT INTO request (id_user,title,content) VALUES (\"" + result.getInt("id") + "\",\"" +
                    request.getTitle() +"\", \"" + request.getContent() + "\");");

            if (result.next()) {
                throw new MultipleUsersInDBException();
            }
            statement.executeBatch();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Request> getAllRequestFromUser(User user)  throws NoUserFoundException,MultipleUsersInDBException {
        Statement statement;
        ArrayList<Request> res = new ArrayList<>();
        try {
            statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT id FROM user WHERE name=\"" + user.getName() +
                    "\" and password=\"" + user.getPassword() + "\";");
            if (!result.next()) {
                throw new NoUserFoundException();
            }
            statement.close();

            statement = this.connection.createStatement();
            ResultSet result2 = statement.executeQuery("SELECT (title, content) FROM request WHERE id_user=\"" + result.getInt("id") + "\";");
            while (result2.next()) {
                res.add(new Request(user, result2.getString("title"), result2.getString("content")));
            }

            if (result.next()) {
                throw new MultipleUsersInDBException();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public User getUser(String name, String password) throws NoUserFoundException,MultipleUsersInDBException,SQLException {
        Statement statement;
        ResultSet result;

        statement = this.connection.createStatement();
        result = statement.executeQuery("SELECT name,password,type FROM user WHERE name=\"" + name +
                "\" and password=\"" + password + "\";");

        if (!result.next()) {
            throw new NoUserFoundException();
        }

        UserType type = null;
        try {
            type = UserType.fromInt(result.getInt("type"));
        } catch (InvalidUserTypeIdException e) {
            throw new RuntimeException(e);
        }

        if (result.next()) {
            throw new MultipleUsersInDBException();
        }

        statement.close();
        return new User(name,password,type);
    }

}
