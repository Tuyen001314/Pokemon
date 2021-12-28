package controller;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class ConnectSQL {
    private Connection conn;
    public static int i = 1;
    public ConnectSQL() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;"
                    + "databaseName=PokemonUser;" + "username=sa; password=linhlinh00");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ResultSet executeQuery(String query){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);
            return ps.executeQuery();
        } catch (SQLException throwables) {
            return null;
        }
    }
    public boolean addUser(UserPokemon user) {
        String sql = "insert into tblUser1(stt, name, score) values(?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, user.getStt());
            ps.setString(2, user.getName());
            ps.setInt(3, user.getScore());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
    public ArrayList<UserPokemon> getListUser(){
        ArrayList<UserPokemon> list = new ArrayList<>();
        String sql = "select * from tblUser1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                UserPokemon user = new UserPokemon();
                user.setStt(rs.getInt("stt"));
                user.setName(rs.getString("name"));
                user.setScore(rs.getInt("score"));

                list.add(user);
            }
        }
        catch (Exception e) {

        }
        return list;
    }

    public ArrayList<UserPokemon> deleteUser(){
        ArrayList<UserPokemon> list = new ArrayList<>();
        String sql = "delete from tblUser1";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeQuery();
            //sql = "delete from tblUser1";
        }
        catch (Exception e) {

        }
        return list;

    }
    public int getMaxStt(){
        String query = "SELECT max(stt) from tblUser1";

        try{
            ResultSet resultSet = executeQuery(query);
            while (resultSet.next()){
                return resultSet.getInt(1);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }
    public int sizeList(ArrayList <UserPokemon> list)
    {
        return list.size();
    }
    public void add(int rank, String name, int score) {
        addUser(new UserPokemon(rank, name, score));
    }

}
