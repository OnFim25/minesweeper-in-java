import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Mypanel extends JPanel implements MouseListener {
    int number_of_rows=15;
    int number_of_columns=number_of_rows;
    int num_of_bombs=35;
    int font= 11;
    int[][] count=new int[number_of_rows][number_of_columns];
    String[][] corner_edge_center=new String[number_of_rows][number_of_columns];
    boolean[][] is_flaged=new boolean[number_of_rows][number_of_columns];//to check if the button is flaged(=true) or not(=false)
    boolean[][] is_bomb=new boolean[number_of_rows][number_of_columns];
    boolean[][] is_flagable=new boolean[number_of_rows][number_of_columns]; // to check if the button is revealed(=false) or not(=true)
    JButton[][] buttons=new JButton[number_of_rows][number_of_columns];
    Random random=new Random();
    Mypanel(){
        this.setPreferredSize(new Dimension(600,600));
        this.setBackground(Color.BLACK);
        this.setLayout(new GridLayout(number_of_rows,number_of_columns));
        for(int i=0;i<number_of_rows;i++){
            for(int j=0;j<number_of_columns;j++){
                buttons[i][j]=new JButton();
                buttons[i][j].addMouseListener(this);
                buttons[i][j].setFocusable(false);
                buttons[i][j].setFont(new Font("",Font.BOLD,font));
                buttons[i][j].setBackground(Color.orange);
                //buttons[i][j].setForeground(Color.BLUE);
                this.add(buttons[i][j]);
            }
        }
        random_bombs(num_of_bombs);
        check_bomb();
    }
    //=========================================================//
    public void random_bombs(int num_of_bombs){
        for(int i=0;i<num_of_bombs;i++){
            int bomb_row=random.nextInt(0,number_of_rows);
            int bomb_column=random.nextInt(0,number_of_columns);
            if(is_bomb[bomb_row][bomb_column]){
                i--;
            }
            is_bomb[bomb_row][bomb_column]=true;
            //buttons[bomb_row][bomb_column].setBackground(Color.green);
        }
    }
    public void flood_fill(int row,int column){
        for(int i=-1;i<=1;i++){
            for(int j=-1;j<=1;j++){
                if((row+i)!=row || (column+j)!=column) {
                    int neib_row = row + i;
                    int neig_column = column + j;
                    if(neib_row>=0
                            && neib_row<=number_of_rows-1
                            && neig_column>=0 && neig_column<=number_of_columns-1
                            && !is_flagable[neib_row][neig_column]
                            && !is_bomb[neib_row][neig_column]) {


                        is_flagable[neib_row][neig_column] = true;
                        buttons[neib_row][neig_column].setBackground(Color.darkGray);
                        if (count[neib_row][neig_column] != 0) {
                           color_count(neib_row,neig_column);
                        }
                        if(count[neib_row][neig_column] == 0) {
                            flood_fill(neib_row, neig_column);
                        }
                    }
                    }
                }
            }
        }
    public void check_bomb(){
        corner_edge_null_checker();
        for(int i=0;i<number_of_rows;i++){
            for(int j=0;j<number_of_columns;j++){
                if(is_bomb[i][j]) {
                    if (corner_edge_center[i][j] == null) {
                        count[i][j - 1]++;
                        count[i][j + 1]++;
                        count[i - 1][j]++;
                        count[i + 1][j]++;
                        count[i - 1][j - 1]++;
                        count[i - 1][j + 1]++;
                        count[i + 1][j - 1]++;
                        count[i + 1][j + 1]++;
                    }
                    if(corner_edge_center[i][j]=="left_edge"){
                        count[i][j+1]++;
                        count[i-1][j]++;
                        count[i+1][j]++;
                        count[i-1][j+1]++;
                        count[i+1][j+1]++;
                    }
                    if(corner_edge_center[i][j]=="top_edge"){
                        count[i][j-1]++;
                        count[i][j+1]++;
                        count[i+1][j-1]++;
                        count[i + 1][j + 1]++;
                    }
                    if(corner_edge_center[i][j]=="right_edge"){
                        count[i][j - 1]++;
                        count[i - 1][j]++;
                        count[i + 1][j]++;
                        count[i - 1][j - 1]++;
                        count[i + 1][j - 1]++;
                    }
                    if(corner_edge_center[i][j]=="bottom_edge"){
                        count[i-1][j]++;
                        count[i][j - 1]++;
                        count[i][j + 1]++;
                        count[i - 1][j - 1]++;
                        count[i - 1][j + 1]++;
                    }
                    if(corner_edge_center[i][j]=="top_left_corner"){
                        count[i][j + 1]++;
                        count[i + 1][j]++;
                        count[i + 1][j + 1]++;
                    }
                    if(corner_edge_center[i][j]=="top_right_corner"){
                        count[i][j - 1]++;
                        count[i + 1][j]++;
                        count[i + 1][j - 1]++;
                    }
                    if(corner_edge_center[i][j]=="bottom_left_corner"){
                        count[i-1][j]++;
                        count[i][j + 1]++;
                        count[i - 1][j + 1]++;
                    }
                    if(corner_edge_center[i][j]=="bottom_right_corner"){
                        count[i-1][j]++;
                        count[i][j - 1]++;
                        count[i - 1][j - 1]++;
                    }
                }
            }
        }
    }
    public void corner_edge_null_checker(){
        for(int i=0;i<number_of_rows;i++){
            for(int j=0;j<number_of_columns;j++){
                //=====edge checking=====//
                if(i==0){
                    corner_edge_center[i][j]="top_edge";
                    //buttons[i][j].setBackground(Color.MAGENTA);
                }
                if(i==number_of_rows-1){
                    corner_edge_center[i][j]="bottom_edge";
                    //buttons[i][j].setBackground(Color.MAGENTA);
                }
                if(j==0){
                    corner_edge_center[i][j]="left_edge";
                    //buttons[i][j].setBackground(Color.MAGENTA);
                }
                if(j==number_of_columns-1){
                    corner_edge_center[i][j]="right_edge";
                    //buttons[i][j].setBackground(Color.MAGENTA);
                }
                //=====corner checking=====//
                if(i==0 && j==0){
                    corner_edge_center[i][j]="top_left_corner";
                    //buttons[i][j].setBackground(Color.red);
                }
                if(i==0 && j==number_of_columns-1){
                    corner_edge_center[i][j]="top_right_corner";
                    //buttons[i][j].setBackground(Color.red);
                }
                if(i==number_of_rows-1 && j==0){
                    corner_edge_center[i][j]="bottom_left_corner";
                    //buttons[i][j].setBackground(Color.red);
                }
                if(i==number_of_rows-1 && j==number_of_columns-1){
                    corner_edge_center[i][j]="bottom_right_corner";
                    //buttons[i][j].setBackground(Color.red);
                }
            }
        }
    }
    public void color_count(int row,int column){
        if (count[row][column] == 1) {
            buttons[row][column].setForeground(new Color(0, 255, 0));
            buttons[row][column].setText("" + count[row][column]);
        }
        if (count[row][column] == 2) {
            buttons[row][column].setForeground(new Color(0, 175, 0));
            buttons[row][column].setText("" + count[row][column]);
        }
        if (count[row][column] == 3) {
            buttons[row][column].setForeground(Color.yellow);
            buttons[row][column].setText("" + count[row][column]);
        }
        if (count[row][column] == 4) {
            buttons[row][column].setForeground(Color.ORANGE);
            buttons[row][column].setText("" + count[row][column]);
        }
        if (4 <= count[row][column] && count[row][column] <= 8) {
            buttons[row][column].setForeground(Color.RED);
            buttons[row][column].setText("" + count[row][column]);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (int i = 0; i < number_of_rows; i++) {
                for (int j = 0; j < number_of_columns; j++) {
                    if (e.getSource() == buttons[i][j]) {
                        is_flagable[i][j] = true;
                        buttons[i][j].setBackground(Color.darkGray);
                       if(count[i][j] == 0){
                           flood_fill(i,j);
                       }
                        if (count[i][j] != 0 && !is_bomb[i][j]) {
                            color_count(i,j);
                        }
                        if(is_bomb[i][j]){
                            for(int k=0;k<number_of_rows;k++){
                                for(int l=0;l<number_of_columns;l++){
                                    if(is_bomb[k][l]){
                                        buttons[k][l].setBackground(Color.RED);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < number_of_rows; i++) {
            for (int j = 0; j < number_of_columns; j++) {
                if (e.getButton() == MouseEvent.BUTTON3 && !is_flaged[i][j]) {
                    if (e.getSource() == buttons[i][j] && !is_flagable[i][j] ) {
                        buttons[i][j].setForeground(Color.BLUE);
                        buttons[i][j].setText("F");
                        is_flaged[i][j]=true;
                    }
                }
            }
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}