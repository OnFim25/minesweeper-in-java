import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    MyFrame(){
        this.setSize(600,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(new Mypanel());
        this.setLayout(new GridLayout());
        this.pack();
        this.setVisible(true);
    }
}
