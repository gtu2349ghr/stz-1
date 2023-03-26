package com.financial.Vo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JCheckBoxDemo02 extends JFrame{
    private JLabel dispalyLabel;
    public JCheckBoxDemo02(final String s){
        super(s);
        JMenuBar bar=new JMenuBar();
        JMenu cityMenu=new JMenu("陕西");

        bar.add(cityMenu);

        dispalyLabel  =new JLabel("你的家乡在哪里！",SwingConstants.CENTER);
        dispalyLabel.setFont(new Font("Serif", Font.PLAIN,50));
        getContentPane().add(dispalyLabel,BorderLayout.CENTER);
        setJMenuBar(bar);
        JMenuItem xian = new JMenuItem("西安");
        cityMenu.add(xian);
        cityMenu.add(new JCheckBox("西安"));
        cityMenu.add(new JMenuItem("咸阳"));
        cityMenu.add(new JMenuItem("宝鸡"));
        cityMenu.add(new JMenuItem("渭南"));
        cityMenu.add(new JMenuItem("延安"));
        cityMenu.add(new JMenuItem("汉中"));
        cityMenu.add(new JMenuItem("安康"));
        cityMenu.add(new JMenuItem("商洛"));
        cityMenu.add(new JMenuItem("铜川"));
        setSize(800,500);
        setVisible(true);
        xian.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispalyLabel.setText("我的家乡是西安");
            }
        });

    }


    public static void main(String[] args) {
        new JCheckBoxDemo02("美丽三秦");
    }
}