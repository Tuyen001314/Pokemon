package controller;

import jdk.jfr.Frequency;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame implements ActionListener, Runnable {

    private int maxTime = 300;
    public int maxTimee = maxTime;
    public int time = maxTime;
    private int row = 8;
    private int col = 10;
    private int width = 900;
    private int height = 600;
    public JLabel lbScore;
    private JProgressBar progressTime;
    private JButton buttonNewGame;
    private JButton buttonSuggest;
    private JButton buttonBack;
    private JButton buttonTurn;
    private JButton buttonRank;
    private ButtonEvent graphicsPanel;
    private JPanel mainPanel;
    private JLabel lbLevel;
    private JLabel picLabel;
    private ConnectSQL sql = new ConnectSQL();

    public boolean pause = false;
    public boolean resume = false;

    public MainFrame() {
        //setLayout(null);
        setTitle("Pokemon Game");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height); //Cài đặt kích cỡ của Frame hiển thị
        setLocationRelativeTo(null); //Chương trình khi chạy sẽ hiển thị ở chính giữa màn hình
        setVisible(true); //Hiển thị Frame

        picLabel = new JLabel();
        add(picLabel);
        picLabel.setBounds(4, 185, 205, 368);
        Image image = new ImageIcon(getClass().getResource(
                "/icon/32.png")).getImage();
        Icon icon = new ImageIcon(image.getScaledInstance(198, 420,
                image.SCALE_DEFAULT));
        picLabel.setIcon(icon);
        add(mainPanel = createMainPanel());
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createGraphicsPanel(), BorderLayout.CENTER);
        panel.add(createControlPanel(), BorderLayout.WEST);
        panel.add(createStatusPanel(), BorderLayout.PAGE_END);
        return panel;
    }

    private JPanel createGraphicsPanel() {
        graphicsPanel = new ButtonEvent(this, row, col);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.pink);
        panel.add(graphicsPanel);
        return panel;
    }

    private JPanel createControlPanel() {
        lbScore = new JLabel("0");
        progressTime = new JProgressBar(0, 100);
        progressTime.setBackground(Color.pink);
        progressTime.setValue(100);
        lbLevel = new JLabel("Normal");

        // create panel container score and time
        JPanel panelLeft = new JPanel(new GridLayout(0, 1, 7, 7));
        panelLeft.add(new JLabel("Score:"));
        panelLeft.add(new JLabel("Time:"));
        panelLeft.add(new JLabel("Level:"));

        JPanel panelCenter = new JPanel(new GridLayout(0, 1, 7, 7));
        panelCenter.add(lbScore);
        panelCenter.add(progressTime);
        panelCenter.add(lbLevel);

        JPanel panelScoreAndTime = new JPanel(new BorderLayout(5, 0));
        panelScoreAndTime.add(panelLeft, BorderLayout.WEST);
        panelScoreAndTime.add(panelCenter, BorderLayout.CENTER);

        // create panel container panelScoreAndTime and button new game
        JPanel panelControl = new JPanel(new BorderLayout(10, 10));
        panelControl.setBorder(new EmptyBorder(10, 3, 10, 3));
        panelControl.add(panelScoreAndTime, BorderLayout.CENTER);

        JPanel panelPic = new JPanel(new BorderLayout(10, 10));

        buttonNewGame = new JButton();
        add(buttonNewGame);
        buttonNewGame.setBounds(15, 105, 80, 30);
        buttonNewGame.setText("New Game");
        buttonNewGame.setBackground(Color.YELLOW);
        buttonNewGame.setBorder(new LineBorder(Color.orange));
        buttonNewGame.addActionListener(this);

        buttonBack = new JButton();
        add(buttonBack);
        buttonBack.setBounds(136,145,67,30);
        buttonBack.setText("Back");
        buttonBack.setBackground(Color.orange);
        buttonBack.setBorder(new LineBorder(Color.orange));
        buttonBack.addActionListener(this);

        buttonSuggest = new JButton();
        add(buttonSuggest);
        buttonSuggest.setBounds(110, 105, 80, 30);
        buttonSuggest.setText("Suggest");
        buttonSuggest.setBackground(Color.pink);
        buttonSuggest.setBorder(new LineBorder(Color.red));
        buttonSuggest.addActionListener(this);

        buttonRank = new JButton();
        add(buttonRank);
        buttonRank.setBounds(3,145,67,30);
        buttonRank.setText("Rank");
        buttonRank.setBorder(new LineBorder(Color.cyan));
        buttonRank.setBackground(Color.CYAN);
        buttonRank.addActionListener(this);


        buttonTurn = new JButton();
        add(buttonTurn);
        buttonTurn.setBounds(69, 145, 67, 30);
        buttonTurn.setText("Turn");
        buttonTurn.setBackground(Color.green);
        buttonTurn.setBorder(new LineBorder(Color.green));
        buttonTurn.addActionListener(this);

        // use panel set Layout BorderLayout to panel control in top
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Hi " + Main.name2));
        panel.add(panelControl, BorderLayout.PAGE_START);
        panel.add(panelPic, BorderLayout.CENTER);


        return  panel;
    }

    // create status panel container author
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(Color.white);
        return panel;
    }

    // create a button

    public void newGame() {
        time = maxTime;
        graphicsPanel.removeAll(); //(gỡ hết các Icon đi)
        mainPanel.add(createGraphicsPanel(), BorderLayout.CENTER); //thêm lại 1 bảng các Icon mới
        mainPanel.validate();   // dùng để set thuộc tính hợp lệ cho Component được tạo ra
        mainPanel.setVisible(true);
        lbScore.setText("0");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonNewGame) {
            showDialogNewGame("Your game hasn't done. Do you want to create a new game?", "Warning", 0);
        }
        else if (e.getSource() == buttonSuggest) {
            ArrayList<Point> list = graphicsPanel.algorithm.suggestPoint() ;
            if (list != null){
                Point p1 = list.get(0);
                Point p2 = list.get(1);
                graphicsPanel.btn[p1.x][p1.y].setBorder(new LineBorder(Color.red));
                graphicsPanel.btn[p1.x][p1.y].setBackground(Color.pink);
                graphicsPanel.btn[p2.x][p2.y].setBorder(new LineBorder(Color.red));
                graphicsPanel.btn[p2.x][p2.y].setBackground(Color.pink);
            }
            else graphicsPanel.turnMatrix();
        }
        else if (e.getSource() == buttonTurn) {
            graphicsPanel.turnMatrix();
        }
        else if(e.getSource() == buttonRank) {
            new JTableFrame();
        }
        else if(e.getSource() == buttonBack)  {
            dispose();
            new StartFrame();
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressTime.setValue((int) ((double) time / maxTime * 100));
        }
    }

    public boolean showDialogNewGame(String message, String title, int t) {
        pause = true;
        resume = false;
        int select = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                null, null);
        if (select == 0) {
            pause = false;
            newGame();
            return true;
        } else {
            if (t == 1) {
                System.exit(0);
                return false;
            } else {
                resume = true;
                return true;
            }
        }
    }

    public JProgressBar getProgressTime() {
        return progressTime;
    }

    public void setProgressTime(JProgressBar progressTime) {
        this.progressTime = progressTime;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isResume() {
        return resume;
    }

    public void setResume(boolean resume) {
        this.resume = resume;
    }
}