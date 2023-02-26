package clientview;


import javax.swing.*;
import javax.xml.stream.Location;
import java.awt.*;

public class LoginPanel extends JPanel {
    public JButton login;
    public JTextField name;
    int width;
    int height;
    public LoginPanel(int width, int height){
        this.width = width;
        this.height=height;
        this.setMinimumSize(new Dimension(width, height-200));
        login = new JButton();
        login.setText("Sign in");
        name=new JTextField();
        add(login);
        add(name);
        this.setLayout(null);
        name.setBounds((width-200)/2,height/2-100,200,50);
        name.setFont(new Font(null, Font.PLAIN, 24));
        name.setText("Type your login");
        login.setBounds((width-200)/2,height/2,200,50);
        this.setBackground(Color.CYAN);
    }
}
