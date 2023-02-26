package clientview;


import model.SubmittedOrder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ClientFrame extends JFrame {

    public LoginPanel loginPanel;
    public ScrollingPanel scrollingPanel;
    public JButton myorders;
    public JPanel buttons;
    public JButton subscribe;
    public JButton unsubscribe;
    public JButton browser;
    public JButton eg;
    public StatusPanel statusPanel;
    public JButton addtocart;
    public JButton removefromcart;
    public JButton submit;
    public JButton refresh;
    public List<SubmittedOrder> submittedOrderList=new ArrayList<>();
    public ClientFrame()  {
        this.setResizable(true);
        this.setMinimumSize(new Dimension(1000,1000));
        loginPanel = new LoginPanel(this.getWidth(), this.getHeight());
        this.add(loginPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        loginPanel.setVisible(true);
        myorders = new JButton();
        browser = new JButton("BROWSER");
        eg=new JButton();
        subscribe = new JButton("SUBSCRIBE");
        addtocart=new JButton("ADD");
        removefromcart = new JButton("REMOVE");
        submit = new JButton("SUBMIT");
        unsubscribe=new JButton("UNSUBSCRIBE");
        refresh=new JButton("GET STATUS");
    }
    public void setButtonsVisibility(Boolean subscribeb, Boolean unsubscribeb, Boolean addtocartb, Boolean removefromcartb, Boolean submitb, Boolean refreshb){
        subscribe.setVisible(subscribeb);
        unsubscribe.setVisible(unsubscribeb);
        addtocart.setVisible(addtocartb);
        removefromcart.setVisible(removefromcartb);
        submit.setVisible(submitb);
        refresh.setVisible(refreshb);
    }
    public void setButtons(){
        myorders.setText("MY ORDERS");
        buttons = new JPanel();
        int a=42;
        browser.setBounds(42, 25, 150, 50);
        myorders.setBounds(192+a,25,150,50);
        refresh.setBounds(384+a, 25 ,150,50 );
        eg.setBounds(10,10,2,2);
        subscribe.setBounds(576+a,25,150,50);
        unsubscribe.setBounds(768+a,25,150,50);
        addtocart.setBounds(384+a,25,150,50);
        removefromcart.setBounds(576+a, 25, 150, 50);
        submit.setBounds(768+a,25,150,50);
        buttons.add(refresh);
        buttons.add(eg);
        buttons.add(myorders);
        buttons.add(browser);
        buttons.add(subscribe);
        buttons.add(addtocart);
        buttons.add(removefromcart);
        buttons.add(submit);
        buttons.add(unsubscribe);
        setButtonsVisibility(true, true, false, false, false,true);
        buttons.setBackground(Color.green);
        buttons.setLayout(null);
        buttons.setPreferredSize(new Dimension(200,100));
        this.add(buttons, BorderLayout.SOUTH);
        buttons.setVisible(true);
        System.out.println(this.getSize().width);
    }
    public void submit(ActionListener listener){submit.addActionListener(listener);}
    public void removefromcartListener(ActionListener listener){removefromcart.addActionListener(listener);}
    public void addtocartListener(ActionListener listenerforadd){addtocart.addActionListener(listenerforadd);}
    public void login(ActionListener listenforlogin){loginPanel.login.addActionListener(listenforlogin);}
    public void myorders(ActionListener listenformyorders){myorders.addActionListener(listenformyorders);}
    public void browser(ActionListener listenforbrowser){browser.addActionListener(listenforbrowser);}
    public void unsubsribe(ActionListener listenerforsend){unsubscribe.addActionListener(listenerforsend);}
    public void eg(ActionListener listenforegg){eg.addActionListener(listenforegg);}
    public void subscribe(ActionListener listenfordelete){
        subscribe.addActionListener(listenfordelete);}
    public void setRefresh(ActionListener listener){refresh.addActionListener(listener);}
}
