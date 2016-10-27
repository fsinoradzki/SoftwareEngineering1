import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class Layout extends JFrame {
    public Layout(JTable grades) {
        //panels for the split frame
        JPanel navPanel = new JPanel();
        //JPanel tabsPanel = new JPanel();
        
        //creating the tabbedpane
        JTabbedPane tabbedPane = new JTabbedPane();  
        //tabsPanel.add(tabbedPane);

        
        //creating the split pane
        //JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navPanel, tabsPanel);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navPanel, tabbedPane);
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerLocation(300);
        splitPane.setBackground(Color.WHITE);
        
        //navigation panel
        JLabel navTitle = new JLabel("NAVIGATION");
        navTitle.setFont(new Font("Arial", Font.BOLD, 18));
        navPanel.add(navTitle);
        navPanel.setBackground(Color.WHITE);
        
        //grades panel
        JPanel gradesPanel = new JPanel();
        JScrollPane gradeScrollPane = new JScrollPane(grades);
        String title = "Class Name";
        gradeScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title, TitledBorder.LEFT, TitledBorder.TOP));
        gradeScrollPane.setBackground(Color.WHITE);
        tabbedPane.add(gradeScrollPane, "Grades");
        
        //rubric panel
        JPanel rubricPanel = new JPanel();
        rubricPanel.setBackground(Color.CYAN);
        tabbedPane.add(new JScrollPane(rubricPanel), "Rubric");
        
        //stats panel
        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(Color.GREEN);
        tabbedPane.add(new JScrollPane(statsPanel), "Statistics");
        
        getContentPane().add(splitPane, BorderLayout.CENTER);
        setSize(1500, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    
    public static void main(String[] args) {

	Vector < Vector < Integer >> SandG = new Vector < Vector < Integer >> ();
    Vector < Integer > test1 = new Vector < Integer > ();
    Vector < Integer > test2 = new Vector < Integer > ();
    Vector < Integer > test3 = new Vector < Integer > ();
    Vector < Integer > test4 = new Vector < Integer > ();

    //Creates the initial 2D Vector
    test1.addElement (123);
    test1.addElement (10);
    test1.addElement (20);
    test1.addElement (30);
    SandG.addElement (test1);
    test2.addElement (456);
    test2.addElement (40);
    test2.addElement (50);
    test2.addElement (60);
    SandG.addElement (test2);
    test3.addElement (789);
    test3.addElement (70);
    test3.addElement (80);
    test3.addElement (90);
    SandG.addElement (test3);
    test4.addElement (000);
    test4.addElement (0);
    test4.addElement (0);
    test4.addElement (0);
    SandG.addElement (test4);
    Vector <String> columnNames = new Vector<String>();
    columnNames.addElement("HEYYYYY");
    columnNames.addElement("HOOOOOOOOOOOOOOOOOO");
    columnNames.addElement("HEYYYYYYYYYYYYYYYYYYY");
    columnNames.addElement("HEOOOOOOOOOOOOOOOOOOOO");
    JTable table = new JTable(SandG, columnNames);
    Layout theGui = new Layout(table);
    }
    
}
