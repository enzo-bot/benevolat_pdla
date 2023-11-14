package org.benevolat.views;

import org.benevolat.controllers.DBManager;
import org.benevolat.models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstWindow extends JPanel {

    public FirstWindow(int itemHeight, int itemWidth, UserInterface ui) {
        super();

        JButton loginButton = new JButton("Connexion");
        loginButton.setBounds(350,150,100,30);

        JButton signinButton = new JButton("Inscription");
        signinButton.setBounds(350,150,100,30);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ui.changePanel(new LogInScreen(itemHeight, itemWidth));
            }
        });
        signinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ui.changePanel(new SignInScreen(itemHeight, itemWidth));
            }
        });

        this.add(loginButton);
        this.add(signinButton);

    }
}
