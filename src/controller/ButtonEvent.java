package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ButtonEvent extends JPanel implements ActionListener {

    //public ButtonEvent controller;
    private int row;
    private int col;
    private int bound = 2;
    private int size = 58;
    public static int score = 0;
    public JButton[][] btn;
    private Point p1 = null;
    private Point p2 = null;
    public Controller algorithm;    // object của controller
    private PointLine line;
    private MainFrame frame;
    private Color backGroundColor = Color.gray;
    private int tmp = 0;


    public ButtonEvent(MainFrame frame, int row, int col) {
        this.frame = frame;
        this.row = row + 2;
        this.col = col + 2;

        setLayout(new GridLayout(row, col, bound, bound)); //Set Layout cho khối JPanel được tạo ra bằng GridLayout
        // (sắp xếp các component thành dạng bảng) với các tham số hàng, cột và khoảng cách giữa các component
        // theo chiều ngang và chiều dọc bằng bound = 2
        setBackground(backGroundColor); //Cài đặt màu nền cho khối JPanel
        setPreferredSize(new Dimension((size + bound) * col, (size + bound)
                * row)); //Cài đặt kích cỡ của khối Panel
        setBorder(new EmptyBorder(12, 12, 12, 12)); //Thêm viền bên ngoài của khối Panel.
        setAlignmentY(JPanel.CENTER_ALIGNMENT);

        newGame();

    }

    private void newGame() {
        algorithm = new Controller(this.row, this.col);
        addArrayButton();

    }



    //thực hiện việc tạo ra các button trong bảng, và setIcon cho các button được tạo ra. Chúng ta cần tạo ra đủ số
    // button tương ứng với kích cỡ của bảng, và các icon cho mỗi button được lấy ra bằng hàm getIcon(int index) với
    // giá trị index chính là giá trị tương ứng của ma trận tại vị trí của button đó
    private void addArrayButton() {
        btn = new JButton[row][col];
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++) {
                btn[i][j] = createButton(i + "," + j);
                Icon icon = getIcon(algorithm.getMatrix()[i][j]);
                btn[i][j].setIcon(icon);
                btn[i][j].setBackground(Color.lightGray);
                add(btn[i][j]);
            }
        }
    }

    // lấy ra ảnh có tên là index trong packpage icon
    private Icon getIcon(int index) {
        int width = 58, height = 58;
        Image image = new ImageIcon(getClass().getResource(
                "/icon/" + index + ".png")).getImage();
        Icon icon = new ImageIcon(image.getScaledInstance(width, height,
                image.SCALE_SMOOTH));
        return icon;
    }

    private JButton createButton(String action) {
        JButton btn = new JButton();
        btn.setActionCommand(action);
        btn.setBorder(null);
        btn.addActionListener(this);
        return btn;
    }

    public void execute(Point p1, Point p2) {
        System.out.println("delete");
        setDisable(btn[p1.x][p1.y]);
        setDisable(btn[p2.x][p2.y]);
    }

    private void setDisable(JButton btn) {
        btn.setIcon(null);
        btn.setBorder(null);
        btn.setBackground(backGroundColor);
        btn.setEnabled(false);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String btnIndex = e.getActionCommand(); //sẽ trả về tọa độ của Icon mà chúng ta vừa click vào
        int indexDot = btnIndex.lastIndexOf(",");
        int x = Integer.parseInt(btnIndex.substring(0, indexDot));
        int y = Integer.parseInt(btnIndex.substring(indexDot + 1, btnIndex.length()));
        if (p1 == null) {
            p1 = new Point(x, y);
            btn[p1.x][p1.y].setBorder(new LineBorder(Color.red));
            btn[p1.x][p1.y].setBackground(Color.pink);
        }
        else if (p2 == null) {
            btn[p1.x][p1.y].setBackground(Color.lightGray);
            p2 = new Point(x, y);
            System.out.println("(" + p1.x + "," + p1.y + ")" + " --> " + "("
                    + p2.x + "," + p2.y + ")");
            line = algorithm.checkTwoPoint(p1, p2);
            algorithm.showMatrix();
            if (line != null) {
                System.out.println("line != null");
                algorithm.getMatrix()[p1.x][p1.y] = 0;
                algorithm.getMatrix()[p2.x][p2.y] = 0;
                algorithm.showMatrix();
                execute(p1, p2);
                line = null;
                score += 10;
                frame.time++;
                frame.lbScore.setText(score + "");
                if (frame.time == 10)
                    frame.showDialogNewGame("You lost. Do you want play new game ?", "Good Luck", 0);
                else
                {
                    if(score == 10 * (row-2) * (col-2) / 2) {

                        frame.showDialogNewGame("You win. Do you want play new game ?", "Congratulation", 0);
                        ConnectSQL sql = new ConnectSQL();
                        tmp = sql.getMaxStt()+1;
                        sql.add(tmp,Main.name2,score);
                    }

                }
            }
            btn[p1.x][p1.y].setBorder(null);
            p1 = null;
            p2 = null;
            System.out.println("done");
        }
    }

    public void turnMatrix() {
        algorithm.turnMatrix();
        removeAll();

        btn = new JButton[row][col];
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++) {
                int fka = algorithm.getMatrix()[i][j];
                if (fka != 0){
                    btn[i][j] = createButton(i + "," + j);
                    Icon icon = getIcon(fka);
                    btn[i][j].setIcon(icon);
                    btn[i][j].setBackground(Color.lightGray);
                    add(btn[i][j]);
                }
                else {
                    btn[i][j] = new JButton();
                    add(btn[i][j]);
                    setDisable(btn[i][j]);
                }
            }
        }
        validate();
    }

}