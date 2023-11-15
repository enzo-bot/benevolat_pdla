package org.benevolat.views;

import org.benevolat.controllers.DBManager;
import org.benevolat.models.User;
import org.benevolat.models.UserType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInScreen extends JPanel {
    public SignInScreen(int itemHeight, int itemWidth, ActionListener listener) {
        super();

        DBManager dbManager = DBManager.getInstance();

        InputField name_field = new InputField("Nom",(this.getWidth()-itemWidth)/2,0, itemWidth,itemHeight, false);

        InputField password_field = new InputField("Mot de passe",300,50, 200,50, true);

        String[] types = {"Bénévole", "Demandeur", "Valideur"};
        JComboBox type_combo = new JComboBox(types);
        type_combo.setBounds(325,100,150,30);

        JButton createAccount = new JButton("Créer");
        createAccount.setBounds(350,150,100,30);

        JButton goBack = new JButton("Retour");
        goBack.setBounds(0,0,100,30);

        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                listener.actionPerformed(new ActionEvent(this,0,"firstwindow"));
            }
        });
        createAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    User user = new User(name_field.getText(), password_field.getText(), UserType.fromInt(type_combo.getSelectedIndex() + 1));
                    dbManager.addUser(user);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                listener.actionPerformed(new ActionEvent(this,0,"firstwindow"));

            }
        });

        this.add(goBack);
        this.add(name_field);
        this.add(password_field);
        this.add(type_combo);
        this.add(createAccount);
    }
}
