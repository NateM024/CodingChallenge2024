
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main extends JPanel implements ActionListener{
    private JFrame frame;
    private Plan planOne;
    private JButton addPlan;
    private JButton compare; 
    private PlanPanel planPanel;
    
    public Main(){
        //frame stuff
        frame = new JFrame("Payment Calculator");
        Container can = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        can.add(this);
        frame.setLayout(new BorderLayout());

        //add components to GUI
        //plans
        planPanel = new PlanPanel(this);
        frame.add(planPanel, BorderLayout.NORTH);

        //addPlan button space 
        addPlan = new JButton("+ Add New Plan");
        addPlan.addActionListener(this);
        addPlan.setPreferredSize(new Dimension(500, 25));
        //addPlan.setFont(buttonFont);
        frame.add(addPlan, BorderLayout.CENTER);
        
        //compare button
        compare = new JButton("Calculate and Compare Plans");
        compare.addActionListener(this);
        compare.setPreferredSize(new Dimension(500, 50));
        Font buttonFont = new Font("Arial", Font.BOLD, 20); // Font name, style, size
        compare.setFont(buttonFont);
        frame.add(compare, BorderLayout.SOUTH);

        //frame stuff
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == compare){
            planPanel.doCalculations();
        }
        else if(e.getSource() == addPlan){
            planPanel.addPlan();
            frame.revalidate();
            frame.repaint();
            frame.pack();
        }
    }

    public void removePlan(int n){
        planPanel.removePlan(n);
        frame.pack();
    }
    public static void main(String[]args){
        new Main();
    }
}