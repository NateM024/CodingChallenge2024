
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Plan extends JPanel {
    private JTextField interestField;
    private JTextField yearField;
    private JTextField monthField;
    private JTextField costField;
    private double monthlyRate;
    private int numberOfPayments;
    private double monthlyPayment;
    private double totalPayment;
    private double totalInterest;
    private JLabel planLabel;
    private JLabel reset;
    private JLabel remove;
    private Main main;
    private int number;
    
    public Plan(int n, Main m){
        if(n%2==0){
            setBackground(new Color(220,220,221));
        }
        else{
            setBackground(new Color(239,239,240));
        }
        number = n;
        main = m;
        GridBagLayout gridbag = new GridBagLayout();
	    GridBagConstraints c = new GridBagConstraints();
	    setLayout(gridbag);
		c.fill = GridBagConstraints.HORIZONTAL;
	        
	    
        //top row
        c.ipady = 10;
        c.ipadx = 10;
        c.insets = new Insets(5,5,5,30);
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2; //combine rows
        planLabel = new JLabel("Plan " + number);
        add(planLabel, c);
        
        c.gridheight = 1;
        c.insets = new Insets(5,5,5,5);

        c.gridx = 1;
        c.gridy = 0;
        add(new JLabel("Annual Interest Rate %"), c);

        c.gridx = 2;
        c.gridy = 0;
        add(new JLabel("Years"), c);

        c.gridx = 3;
        c.gridy = 0;
        add(new JLabel("Months"), c);

        c.gridx = 4;
        c.gridy = 0;
        add(new JLabel("Loan Amount"), c);


        //bottom row
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 1;
        interestField = new JTextField("0"); 
        add(interestField, c);

        c.gridx = 2;
        c.gridy = 1;
        yearField = new JTextField("0");
        add(yearField, c);

        c.gridx = 3;
        c.gridy = 1;
        monthField = new JTextField("0");
        add(monthField, c);

        c.gridx = 4;
        c.gridy = 1;
        costField = new JTextField("0");
        add(costField, c);

        c.gridheight = 2; 
        c.gridx = 5;
        c.gridy = 0;         

        if(n == 1){
            reset = new JLabel("                        ");
            add(reset,c);
        }
        else{
           
            remove = new JLabel("Remove Plan");
            remove.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                    System.out.println("remove plan");
                    main.removePlan(number);
                }
            });
            add(remove,c);
        }  

    }

    public void changeNumber(int n){
        number = n;
        if(n%2==0){
            setBackground(new Color(220,220,221));
        }
        else{
            setBackground(new Color(239,239,240));
        }
        planLabel.setText("Plan " + number);
        repaint();
        revalidate();
    }

    public double getInterest(){
        String text = interestField.getText(); // String to be converted

        try {
            double num = Double.parseDouble(text); // Convert String to int
            return num/100;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        }
        return 0;
    }
    public int getMonths(){
        String text = monthField.getText(); // String to be converted

        try {
            int num = Integer.parseInt(text); // Convert String to int
            return num;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        }
        return 0;
    }
    public int getYears(){ 
        String text = yearField.getText(); // String to be converted

        try {
            int num = Integer.parseInt(text); // Convert String to int
            return num;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        }
        return 0;
    }
    public int getCost(){ 
        String text = costField.getText(); // String to be converted

        try {
            int num = Integer.parseInt(text); // Convert String to int
            return num;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        }
        return 0;
    }

    public void doCalculations(){
        monthlyRate = getInterest()/12;
        numberOfPayments = getYears() * 12 + getMonths();
        monthlyPayment = (getCost() * monthlyRate * (Math.pow((1+monthlyRate), numberOfPayments)))/(Math.pow((1+monthlyRate),numberOfPayments)-1);
        totalPayment = monthlyPayment * numberOfPayments;
        totalInterest = monthlyPayment * numberOfPayments - getCost();
    }

    public double getMonthlyRate(){
        return Math.round(monthlyRate * 10000000.0) / 100000.0;
    }
    public int getNumberOfPayments(){
        return numberOfPayments;
    }
    public double getMonthlyPayment(){
        return Math.round(monthlyPayment * 100.0) / 100.0;
    }
    public double getTotalPayment(){
        return Math.round(totalPayment * 100.0) / 100.0;
    }
    public double getTotalInterest(){
        return Math.round(totalInterest * 100.0) / 100.0;
    }
}
