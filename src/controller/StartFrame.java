package controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame implements ActionListener {

    private JPanel startPanel;
    private JButton buttonStart;
    private JTextField enterNickName;
    private JLabel picLabel;
    private MainFrame frame;

    private int width = 900;
    private int height = 600;

    public StartFrame() {
        setLayout(null);
        setTitle("Pokemon");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);


        JLabel label = new JLabel();
        add(label);
        Font font1 = new Font("Serif", Font.ITALIC, 16);
        label.setText("NickName: ");
        label.setFont(font1);
        label.setBounds(255, 378, 90, 30);

        enterNickName = new JTextField();
        enterNickName.setText("  ");
        add(enterNickName);
        enterNickName.setBounds(335, 378, 180, 30);
        enterNickName.setBorder(null);

        picLabel = new JLabel();
        add(picLabel);
        picLabel.setBounds(0, 0, width, height);
        Image image = new ImageIcon(getClass().getResource(
                "/icon/31.png")).getImage();
        Icon icon = new ImageIcon(image.getScaledInstance(width, height,
                image.SCALE_DEFAULT));
        picLabel.setIcon(icon);

        buttonStart = new JButton();
        add(buttonStart);
        buttonStart.setBounds(380, 420, 80, 35);
        buttonStart.setText("START");
        buttonStart.setBackground(Color.YELLOW);
        buttonStart.setBorder(new LineBorder(Color.yellow));
        buttonStart.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonStart) {
            Main.name2 = enterNickName.getText().trim();
            if (!Main.name2.equals("")) {
                frame = new MainFrame();
                dispose();
                MyTimeCount timeCount = new MyTimeCount();
                timeCount.start();
                new Thread(frame).start();
            } else {
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "Need to enter a name before starting", "Warning",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    class MyTimeCount extends Thread {

        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (frame.isPause()) {
                    if (frame.isResume()) {
                        frame.time--;
                    }
                } else {
                    frame.time--;
                }
            }
        }
    }
}

