package org.benevolat.views;

import org.benevolat.controllers.DBManager;
import org.benevolat.models.User;
import org.benevolat.models.UserType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInScreen extends JPanel {
    public LogInScreen(int itemHeight, int itemWidth, ActionListener listener) {
        super();

        DBManager dbManager = DBManager.getInstance();

        InputField name_field = new InputField("Nom",(this.getWidth()-itemWidth)/2,0, itemWidth,itemHeight, false);

        InputField password_field = new InputField("Mot de passe",300,50, 200,50, true);

        JButton loginButton = new JButton("Se connecter");
        loginButton.setBounds(350,150,100,30);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    User user = dbManager.getUser(name_field.getText(), password_field.getText());
                    System.out.println("User logged");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        JButton goBack = new JButton("Retour");
        goBack.setBounds(0,0,100,30);

        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                listener.actionPerformed(new ActionEvent(this,0,"firstwindow"));
            }
        });

        this.add(goBack);
        this.add(name_field);
        this.add(password_field);
        this.add(loginButton);
    }
}
