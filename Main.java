
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main extends JPanel implements ActionListener{
    private JFrame frame;
    private JButton button;

    public Main(){
        frame = new JFrame();
        Container can = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        can.add(this);
        button = new JButton("Hello");
        button.addActionListener(this);
        frame.add(button);
        frame.setSize(400,400);
        frame.setVisible(true);
    }
    public static void main(String[]args){
        new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       System.out.println("Hello World");
    }
}