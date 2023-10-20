package org.benevolat.views;

import javax.swing.*;
import java.awt.event.*;


public class UserInterface extends JFrame {
    public UserInterface() {
        super("Bénévolat");

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e){

                System.exit(0);
            }
        };

        addWindowListener(l);
        setSize(800,400);

//        SignInScreen signIn = new SignInScreen(50, 200);
//        signIn.setBounds(0, 0, this.getWidth(), this.getHeight());
//        this.add(signIn);

        LogInScreen logIn = new LogInScreen(50, 200);
        logIn.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.add(logIn);

        setLayout(null);
        setVisible(true);

    }




    //TODO A mettre dans un controleur
    public static void main(String [] args){
        JFrame frame = new UserInterface();
    }
}
