package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.List;

public class JTableFrame extends JFrame {
    private static final long serialVersionUID = -6464587060272247354L;
    private Connection connect = null;
    public ConnectSQL sql = new ConnectSQL();
    private JTable jtable = new JTable();
    private DefaultTableModel tableModel = new DefaultTableModel();


    public JTableFrame(){
        String []colsName = {"Rank", "Name", "Score"};
        tableModel.setColumnIdentifiers(colsName);  // đặt tiêu đề cột cho tableModel
        jtable.setModel(tableModel);    // kết nối jtable với tableModel

        initComponent();    // Khởi tạo các components trên JFrame
        updateData(sql.getListUser()); // gọi hàm view để truy suất dữ liệu sau đó truyền cho hàm updateData để đưa dữ liệu vào tableModel và hiện lên Jtable trong Frame
    }

    public void updateData(List<UserPokemon> listUserPokemon){
        String []colsName = {"Rank", "Name", "Score"};
        tableModel.setColumnIdentifiers(colsName); // Đặt tiêu đề cho bảng theo các giá trị của mảng colsName
         for (UserPokemon user : listUserPokemon) {
                String rows[] = new String[3];
                rows[0] = String.valueOf(user.getStt()); // lấy dữ liệu tại cột số 1
                rows[1] = user.getName(); // lấy dữ liệu tai cột số 2
                rows[2] = String.valueOf(user.getScore());
                tableModel.addRow(rows); // đưa dòng dữ liệu vào tableModel để hiện thị lên jtable
            }
    }

    public void initComponent(){
        this.setSize(350, 400);
        JScrollPane scroll = JTable.createScrollPaneForTable(jtable);
        this.add(scroll); // Đưa thanh cuộn vào Frame (hiện thanh cuộn trên frame)
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
