import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Display {
    private JFrame frame;
    private JTable table1;
    private JTable table2 = null;
    private ArrayList<Plan> plans;
    private JScrollPane scrollPane2;
    private JPanel panel;
    private JLabel heading;

    public Display(int n, ArrayList p){
        frame = new JFrame("Data Display");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        plans = p;
        frame.add(new JLabel(" "), BorderLayout.NORTH);

        //JTable stuff 
        table1 = createFirstTable(n);
        JScrollPane scrollPane1 = new JScrollPane(table1);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(500, 250)); // Maximum height of 250 pixels
        panel.add(scrollPane1);
    
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Get the row and column of the clicked cell
                int row = table1.rowAtPoint(e.getPoint());
                int column = table1.columnAtPoint(e.getPoint());

                if(column == 0){
                    if(table2 != null){
                        panel.remove(scrollPane2);
                        panel.remove(heading);
                    }
                    heading = new JLabel((String)table1.getValueAt(row, column));

                    JTable newData = createSecondTable(n, row);
                    scrollPane2 = new JScrollPane(newData);
                    scrollPane2.setPreferredSize(new Dimension(500, 200));
                    table2 = newData;
                    panel.add(heading);
                    panel.add(scrollPane2);
                    panel.revalidate();
                    panel.repaint();
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(548, 350);
        
       // frame.pack();
        frame.setVisible(true);
    }

    public JTable createFirstTable(int n){
        String[] columns = new String[n+1];
        columns[0] = "";
        for(int i = 1; i < columns.length; i++){
            columns[i] = "Plan " + (i);
        }

        //make data in table
        Object [][] data = new Object[5][columns.length];
        data[0][0] = "Monthly \n Interest Rate";
        data[1][0] = "Number of \n Payments";
        data[2][0] = "Monthly \n Payment";
        data[3][0] = "Total Payment";
        data[4][0] = "Total Interest";
        
        for(int i = 0; i < data.length; i++){
            for(int j = 1; j < data[i].length; j++){
                switch (i) {
                    case 0:
                        data[i][j] = plans.get(j-1).getMonthlyRate() + "%";
                        break;
                    case 1:
                        data[i][j] = plans.get(j-1).getNumberOfPayments();
                        break;
                    case 2:
                        data[i][j] = "$" + plans.get(j-1).getMonthlyPayment();
                        break;
                    case 3:
                        data[i][j] = "$" + plans.get(j-1).getTotalPayment();
                        break;
                    case 4:
                        data[i][j] = "$" + plans.get(j-1).getTotalInterest();
                        break;
                    default:
                        break;
                }
                    
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, columns);

        JTable table = new JTable(model);
        Font font = new Font("Arial", Font.PLAIN, 14); 
        table.setFont(font);
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // Use the parent class renderer (JLabel) to handle basic rendering
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Wrap text for multiline support
                label.setText("<html>" + value.toString().replace("\n", "<br>") + "</html>");
                
                return label;
            }
        });
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setText("<html>" + value.toString().replace("\n", "<br>") + "</html>");
                
                // Adjust the row height based on the text content
                int rowHeight = label.getPreferredSize().height; // Add extra padding
                table.setRowHeight(row, rowHeight);

                return label;
            }
        });
       // table.setRowHeight(20);
        return table;
    }

    public JTable createSecondTable(int n, int r){
        Object [] data2 = new Object[n+2];
        String[] smallColumns = new String[n+2];


        if(r == 1){
            Object [] data1 = new Object[n];
            //fill array
            for(int i = 0; i < data1.length; i++){
                data1[i] = table1.getValueAt(r,i+1);
             }

            //order array
            for(int i = 0; i < data1.length -1; i++){
                for(int j = 0; j < data1.length -i-1; j++){
                     if ((int) data1[j] > (int)data1[j+1]) {
                        int temp = (int)data1[j];
                        data1[j] = data1[j + 1];
                        data1[j + 1] = temp;
                    }
               }
            }    

            //top row
            smallColumns[0] = " ";
            String[] temp = new String[n];
            for(int i = 1; i <= data1.length; i++){
                for(int j = 0; j < data1.length; j++){
                    if(table1.getValueAt(r, i) == data1[j]) {
                        temp[j] = "Plan " + String.valueOf(i);
                    }
                }
            }
            //fill top row
            for(int i = 0; i < temp.length; i++){
                smallColumns[i+1] = temp[i];
            }
    
            smallColumns[smallColumns.length-1] = " ";
            //fill 2nd row
            data2[0] = "Lowest";
            for(int i = 1; i < smallColumns.length-1; i++){
                data2[i] = (int)data1[i-1];
            } 
            data2[smallColumns.length-1] = "Highest";
    
        }
        else{
            ArrayList<Double> data1 = new ArrayList<>();
            //fill array
            for(int i = 0; i < n; i++  ){
                String str = (String) table1.getValueAt(r, i+1);
                str = str.replaceAll("[^0-9.]", "");            
                data1.add(Double.valueOf(str));
            }
            //order array
            for(int i = 0; i < data1.size() -1; i++){
                for(int j = 0; j < data1.size() -i-1; j++){
                    if (data1.get(j) > data1.get(j+1)) { 
                        Double temp = data1.get(j);
                        data1.set(j,data1.get(j+1));
                        data1.set(j + 1, temp);
                    }
                }
            }
            
            //top row
            smallColumns[0] = " ";
            String[] temp = new String[n];
            for(int i = 1; i <= data1.size(); i++){
                for(int j = 0; j < data1.size(); j++){
                    String str = (String) table1.getValueAt(r,i);
                    str = str.replaceAll("[^0-9.]", "");

                    if(Objects.equals(Double.valueOf(str), data1.get(j))) {
                        temp[j] = "Plan " + String.valueOf(i);
                    }
                }
            }
            //fill top row
            for(int i = 0; i < temp.length; i++){
                smallColumns[i+1] = temp[i];
            }
            smallColumns[smallColumns.length-1] = " ";
            //fill 2nd row
            data2[0] = "Lowest";
            for(int i = 1; i < smallColumns.length-1; i++){
                data2[i] = data1.get(i-1);
            } 
            data2[smallColumns.length-1] = "Highest";
    
        }
       
        Object [][] data2D = {data2};

        DefaultTableModel smallModel = new DefaultTableModel(data2D, smallColumns);
        JTable table = new JTable(smallModel);
        Font font = new Font("Arial", Font.PLAIN, 14); 
        table.setFont(font);
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
        table.setRowHeight(20);
        return table;
    }
}


// Custom TableCellRenderer
class CustomTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                  boolean hasFocus, int row, int column) {
                                 
        // Call the superclass to get the default component
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Change color for a specific row (e.g., row 2)
        if (row%2==0) { // Row 2 (index 1)
            component.setBackground(new Color(239,239,240));
        }
        // Change color for a specific column (e.g., "Fruit" column at index 1)
        // else if (column == 1) { // Column 1 ("Fruit")
        //     component.setBackground(Color.PINK);
        // }
        // // Change color for a specific cell (row 2, column 1)
        // else if (row == 1 && column == 1) {
        //     component.setBackground(Color.GREEN);
        // }
        else {
            // Default background color
            component.setBackground(new Color(220,220,221));
        }

        return component;
    }

}