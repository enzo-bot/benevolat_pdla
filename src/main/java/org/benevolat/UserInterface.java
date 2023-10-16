package org.benevolat;
import javax.swing.*;
import java.awt.event.*;


public class UserInterface extends JFrame{
    public UserInterface() {
        super("Bénévolat");

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e){

                System.exit(0);
            }
        };

        addWindowListener(l);
        setSize(800,400);

        this.addAllComponents();

        setLayout(null);
        setVisible(true);

    }

    public void addAllComponents() {
        InputField name_field = new InputField("Nom",300,0, 200,50, false);

        InputField password_field = new InputField("Mot de passe",300,50, 200,50, true);

        String[] types = {"Bénévole", "Demandeur"};
        JComboBox type_combo = new JComboBox(types);
        type_combo.setBounds(100,0,100,100);
        //type_combo.addItemListener(types);

        JButton createAccount = new JButton("Créer");
        createAccount.setBounds(350,150,100,100);
        createAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println(name_field.getText());
            }
        });

        this.add(name_field);
        this.add(password_field);
        this.add(type_combo);
        this.add(createAccount);

    }

    public static void main(String [] args){
        JFrame frame = new UserInterface();


    }
}
