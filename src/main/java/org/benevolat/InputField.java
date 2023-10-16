package org.benevolat;
import javax.swing.*;
import java.awt.event.*;

public class InputField extends JPanel{
//    private String title;
//    private JLabel nom_label;
    private JTextField name_field;

    public InputField(String title, int x, int y, int width, int height, boolean hidden) {
        super();
//        this.title = title;

        this.setBounds(x, y, width, height);

        JLabel nom_label = new JLabel(title);
        nom_label.setBounds(0,0,width/2,height);

        // Hypothèse : 15px par caractère
        if (!hidden) {
            name_field = new JTextField(width/30);
        } else {
            name_field = new JPasswordField(width/30);
        }
        name_field.setBounds(width/2,0,width/2,height);

        this.add(nom_label);
        this.add(name_field);
    }

    public String getText() {
        return this.name_field.getText();
    }
}
