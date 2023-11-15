package org.benevolat.views;

import org.benevolat.controllers.DBManager;
import org.benevolat.models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstWindow extends JPanel {
    public FirstWindow(int itemHeight, int itemWidth, ActionListener listener) {
        super();

        JButton loginButton = new JButton("Connexion");
        loginButton.setBounds(350,150,100,30);

        JButton signinButton = new JButton("Inscription");
        signinButton.setBounds(350,150,100,30);

        FirstWindow window = this;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                listener.actionPerformed(new ActionEvent(this,0,"login"));
            }
        });
        signinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                listener.actionPerformed(new ActionEvent(this,0,"signin"));
            }
        });

        this.add(loginButton);
        this.add(signinButton);

    }
}
