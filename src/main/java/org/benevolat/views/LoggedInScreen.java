package org.benevolat.views;

import org.benevolat.controllers.DBManager;
import org.benevolat.models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoggedInScreen extends JPanel {
    public LoggedInScreen(int itemHeight, int itemWidth, User user) {
        super();

        //DBManager dbManager = DBManager.getInstance();

        JLabel nom = new JLabel("Bienvenue sur votre espace de " + user.getType() + ", " + user.getName());

        this.add(nom);
    }
}
