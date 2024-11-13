
import java.awt.LayoutManager;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PlanPanel extends JPanel {
    private ArrayList<Plan> planList = new ArrayList<>();
    private Main main;
    private Plan firstPlan = new Plan(1, main);

    public PlanPanel(Main m){
        setLayout((LayoutManager) new BoxLayout(this, BoxLayout.Y_AXIS));
        planList.add(firstPlan);
        add(firstPlan);
        setBorder(BorderFactory.createEmptyBorder());
        main = m;
    }

    public void addPlan(){
        if(planList.size() < 5){
            planList.add(new Plan(planList.size()+1, main));
            add(planList.get(planList.size()-1));
            repaint();
            revalidate();
        }
        else{
            JOptionPane.showMessageDialog(this, "Too many plans. Limit of 5.");
        }
    }

    public void removePlan(int n){
        remove(planList.get(n-1));
        planList.remove(n-1);
        for(int i = 0; i < planList.size(); i++){
            planList.get(i).changeNumber(i+1);
        }
        repaint();
        revalidate();
    }

    public void doCalculations() {
        for(int i = 0; i < planList.size(); i++){
            planList.get(i).doCalculations();
        }      
        
        Display display = new Display(planList.size(), planList);
    }
}
