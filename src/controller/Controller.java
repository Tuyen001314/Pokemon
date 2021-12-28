package controller;

import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;

import static java.lang.Math.min;

public class Controller {
    private int row;
    private int col;
    private int[][] matrix;
    public int[] cd = {-1, 0, 0, 1};
    public int[] cc = {0, -1, 1, 0};
    public int x;
    public static String name;

    public Controller( int row, int col) {
        this.row = row;
        this.col = col;
        System.out.println((row-2) + "x" + (col-2));
        createMatrix();
        showMatrix();
    }

    public PointLine checkTwoPoint(Point p1, Point p2) {
        if(checkTwoPoint1(p1.x, p1.y, p2.x, p2.y)) {
            return new PointLine(p1, p2);
        }
        return null;
    }

    public boolean checkTwoPoint1(int dx1, int dy1, int dx2, int dy2){
        if(matrix[dx1][dy1] != matrix[dx2][dy2] || dx1 < 1 || dy1 < 1 || dx2 > row - 1 || dy2 > col - 1) return false;
        if(matrix[dx1][dy1] == 0 && matrix[dx2][dy2] == 0) return false;
        if(solve(dx1, dy1, dx2, dy2)) return true;
        return false;
    }

    public boolean checkTwoPoint2(int dx1, int dy1, int dx2, int dy2){
        if(matrix[dx1][dy1] != matrix[dx2][dy2] || dx1 < 1 || dy1 < 1 || dx2 > row - 1 || dy2 > col - 1) return false;
        else if(matrix[dx1][dy1] == 0 && matrix[dx2][dy2] == 0) return false;
        else if(solve7(dx1, dy1, dx2, dy2)) {
            System.out.println("ahihi " +  matrix[dx1][dy1] + " " + matrix[dx2][dy2] );
            return true;
        }
        return false;
    }

    public ArrayList suggestPoint(){
        for(int i = 1; i < row-1; i++) {
            for(int j = 1; j < col-1; j++) {
                for(int x = 1; x < row-1; x++) {
                    for (int y = 1; y < col-1; y++) {
                        if(checkTwoPoint2(i,j,x,y))
                        {
                            Point p1 = new Point(i,j);
                            Point p2 = new Point(x,y);
                            ArrayList<Point> arrayList = new ArrayList<>();
                            arrayList.add(p1);
                            arrayList.add(p2);
                            return arrayList;
                        }
                    }
                }
            }
        }
        return null;
    }

    public void turnMatrix()
    {
        Random rand = new Random();    // object random
        int imgCount = 25;             // số ảnh
        int max = 8;                   // số ảnh giống nhau tối đa
        int arr[] = new int[imgCount + 1];
        ArrayList<Point> listPoint = new ArrayList<Point>();  // tạo list
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++)
            if(matrix[i][j] != 0)
            {
                listPoint.add(new Point(i, j));       // thêm điểm mới vào list
            }
        }
        int i = 0;
        do {
            int index = rand.nextInt(imgCount) + 1;    // random các số tương tự tên image
            if (arr[index] < max) {              // kiểm soát tránh trường hợp sinh trùng lặp quá nhiều
                arr[index] += 2;
                for (int j = 0; j < 2; j++) {
                    try {
                        int size = listPoint.size();
                        int pointIndex = rand.nextInt(size);
                        matrix[listPoint.get(pointIndex).x][listPoint
                                .get(pointIndex).y] = index;
                        listPoint.remove(pointIndex);
                    } catch (Exception e) {
                    }
                }
                i++;
            }
        } while (i < row * col / 2 - ButtonEvent.score/10);

    }
    public void showMatrix() {
        for(int i = 0; i < row ; i++)
        {
            for(int j = 0; j < col ; j++)
                if(i > 0 && i < row - 1 && j < col - 1 && j > 0)
                {
                    if(matrix[i][j] != -1)
                    {
                        System.out.printf("%3d", matrix[i][j]);
                    }
                    else {
                        matrix[i][j] = 0;
                        System.out.printf("%3d", matrix[i][j]);
                    }
                }
                else
                {
                    matrix[i][j] = 0;
                    System.out.printf("%3d", matrix[i][j]);
                }
            System.out.println();
        }
    }

    public boolean solve7(int dx1, int dy1, int dx2, int dy2) {
        int p = 1000000000;
        Queue<Data> Q = new ArrayDeque<>();
        Q.offer(new Data(dx1, dy1, 0, dx1, dy1));
        int LL = matrix[dx1][dy1];
        int pLL = matrix[dx2][dy2];
        matrix[dx2][dy2] = 0;
        matrix[dx1][dy1] = -1;
        while(!Q.isEmpty())
        {
            Data tmp = Q.peek();
            Q.remove();
            for(int x = 0; x <= 3; x++)
            {
                int dx = tmp.x + cd[x];
                int dy = tmp.y + cc[x];
                if(dx >= 0 && dy >= 0 && dx < row && dy < col && matrix[dx][dy] == 0)
                {
                    if(dx != dx2 || dy != dy2)
                    {
                        matrix[dx][dy] = -1;
                        if(dx != tmp.z && dy != tmp.t)
                        {
                            x = tmp.d+1;
                            Q.add(new Data(dx, dy, tmp.d+1, dx, dy));
                        }
                        else
                        {
                            x = tmp.d;
                            Q.add(new Data(dx, dy, tmp.d, tmp.z, tmp.t));
                        }
                    }
                    else
                    {
                        if(dx != tmp.z && dy != tmp.t)
                        {
                            x = tmp.d+1;
                        }
                        else
                        {
                            x = tmp.d;
                        }
                        p = min(p, x);
                        if(p <= 2)
                        {
                            matrix[dx1][dy1] = LL;
                            matrix[dx2][dy2] = pLL;
                            for(int i = 0; i < row ; i++)
                            {
                                for(int j = 0; j < col ; j++)
                                    if(i > 0 && i < row - 1 && j < col - 1 && j > 0)
                                    {
                                        if(matrix[i][j] == -1)
                                        {
                                            matrix[i][j] = 0;
                                        }
                                    }
                                    else
                                    {
                                        matrix[i][j] = 0;
                                    }
                            }
                            return true;
                        }
                    }
                }
            }
        }
        matrix[dx1][dy1] = LL;
        matrix[dx2][dy2] = pLL;
        for(int i = 0; i < row ; i++)
        {
            for(int j = 0; j < col ; j++)
                if(i > 0 && i < row - 1 && j < col - 1 && j > 0)
                {
                    if(matrix[i][j] == -1)
                    {
                        matrix[i][j] = 0;
                    }
                }
                else
                {
                    matrix[i][j] = 0;;
                }
        }
        return false;
    }

    public boolean solve(int dx1, int dy1, int dx2, int dy2) {
        int p = 1000000000;
        Queue<Data> Q = new ArrayDeque<>();
        Q.offer(new Data(dx1, dy1, 0, dx1, dy1));
        int LL = matrix[dx1][dy1];
        int pLL = matrix[dx2][dy2];
        matrix[dx2][dy2] = 0;
        matrix[dx1][dy1] = -1;
        while(!Q.isEmpty())
        {
            Data tmp = Q.peek();
            Q.remove();
            for(int i = 0; i <= 3; i++)
            {
                int dx = tmp.x + cd[i];
                int dy = tmp.y + cc[i];
                if(dx >= 0 && dy >= 0 && dx < row && dy < col && matrix[dx][dy] == 0)
                {
                    if(dx != dx2 || dy != dy2)
                    {
                        matrix[dx][dy] = -1;
                        if(dx != tmp.z && dy != tmp.t)
                        {
                            x = tmp.d+1;
                            Q.add(new Data(dx, dy, tmp.d+1, dx, dy));
                        }
                        else
                        {
                            x = tmp.d;
                            Q.add(new Data(dx, dy, tmp.d, tmp.z, tmp.t));
                        }
                    }
                    else
                    {
                        if(dx != tmp.z && dy != tmp.t)
                        {
                            x = tmp.d+1;
                        }
                        else
                        {
                            x = tmp.d;
                        }
                        p = min(p, x);
                        if(p <= 2) {

                            //System.out.println(p);
                            return true;
                        }
                    }
                }
            }
        }
        //System.out.println(p + " no");
        matrix[dx1][dy1] = LL;
        matrix[dx2][dy2] = pLL;
        return false;
    }

    private void createMatrix() {
        matrix = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++)
            if(i == 0 || i == row - 1 || j == 0 || j == col - 1){
                matrix[i][j] = 0;
            }
        }

        Random rand = new Random();    // object random
        int imgCount = 28;             // số ảnh
        int max = 6;                   // số ảnh giống nhau tối đa
        int arr[] = new int[imgCount + 1];
        ArrayList<Point> listPoint = new ArrayList<Point>();  // tạo list
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++) {
                listPoint.add(new Point(i, j));       // thêm điểm mới vào list
            }
        }
        int i = 0;
        do {
            int index = rand.nextInt(imgCount) + 1;    // random các số tương tự tên image
            if (arr[index] < max) {              // kiểm soát tránh trường hợp sinh trùng lặp quá nhiều
                arr[index] += 2;
                for (int j = 0; j < 2; j++) {
                    try {
                        int size = listPoint.size();
                        int pointIndex = rand.nextInt(size);
                        matrix[listPoint.get(pointIndex).x][listPoint
                                .get(pointIndex).y] = index;
                        listPoint.remove(pointIndex);
                    } catch (Exception e) {
                    }
                }
                i++;
            }
        } while (i < row * col / 2); // số cặp tối đa xuất hiện trong ma trận
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

}