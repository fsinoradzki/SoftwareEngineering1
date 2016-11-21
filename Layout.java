/*
  Authors: Ben Barriage, Phoebe Nezamis, Frank Sinoradzki
  Date: 11/14/16
  Purpose: Gradebook Functionality with working GUI
  Known Bugs: Centipede, bess beetle, cricket
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class Layout extends JFrame {
    public static class LogIn extends JFrame {
        public LogIn() {
            super("Authentication");

	    File mydir = new File("./Classes/");
	    if(!mydir.exists())
		mydir.mkdir();
	    
            JButton loginButton = new JButton("Login");
            JPanel loginPanel = new JPanel();
            JPasswordField password = new JPasswordField(15);
            JLabel enterPass = new JLabel ("Enter your password");
            
            loginPanel.add(enterPass);
            loginPanel.add(password);
            loginPanel.add(loginButton);
            
            getContentPane().add(loginPanel);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);    
            setSize(300, 200);
            setLocation(500, 280);
            
	    String userPassword = "12345";
	    
            loginPanel.getRootPane().setDefaultButton(loginButton);
            
            loginButton.addActionListener((ActionEvent e) -> {
		    try{
			File psswd = new File("Classes/password.txt");
			if(!psswd.exists()){
			    psswd.createNewFile();
			    FileWriter fw = new FileWriter(psswd);
			    BufferedWriter bw = new BufferedWriter(fw);
			    bw.write(password.getText());
			    bw.close();
			    LogIn entry = new LogIn();
			}
			else{
			    String passwordEntered = password.getText();
			    Scanner sc = new Scanner(psswd);
			    if(passwordEntered.equals(sc.nextLine())){     
				File file = new File("./Classes/ClassList.txt");
				//if the file is empty, runs new class
				if (file.length() <= 1) {
				    System.out.println("Is it empty?");
				    ClassList semester = new ClassList();
				    Class c1 = new Class();
				    Vector<Class> temp = new Vector<Class>();
				    semester.list = temp;
				    AddClass firstClass = new AddClass(semester, c1);
				    
				    firstClass.setVisible(true);
				    firstClass.setLocation(500, 500);
				    firstClass.setSize(350, 150);
				    
				    dispose();
				}
				else {
				    
				    ClassList semester = new ClassList();
				    semester.loadClassFile();
				    semester.loadLast();
				    
				    Class currClass = semester.lastEdited;
				    semester.loadTable(currClass);
				    Object[][]data = semester.currClassData;
				    String columns[] = semester.columnNames;
				    
				    JTable table = new JTable(data, columns);
				    
				    Layout layout = new Layout(table,semester);
				    layout.setVisible(true);
				    layout.setSize(1500, 1000);
				    layout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				    
				    dispose();
				}
			    }               
			    else {
				JOptionPane.showMessageDialog(null, "Wrong Passwrd");
				password.setText("");
				password.requestFocus();
			    }
			}
		    }catch(IOException err){}
		});
        }
    }
    
    public static class AddClass extends JFrame {
        public AddClass(ClassList semester, Class c1) {
            super("Add Class");
	   
	    JPanel addClass = new JPanel();
            addClass.setLayout(new BorderLayout());
            
            //all the elements
            JButton save = new JButton("Continue");
	    Color addClassColor = new Color(65,169,181);
	    save.setBackground(addClassColor);
            JLabel label1 = new JLabel("Class Name:", SwingConstants.CENTER);
            JTextField className = new JTextField("", 15);
            JLabel label2 = new JLabel("Class Number:", SwingConstants.CENTER);
            JTextField classNum = new JTextField("", 15);
            
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(0, 2, 10, 10));
            JPanel savePanel = new JPanel();
            
            inputPanel.add(label1);
            inputPanel.add(className);
            inputPanel.add(label2);
            inputPanel.add(classNum);
            
            savePanel.add(save);
            
            addClass.add(inputPanel, BorderLayout.CENTER);
            addClass.add(savePanel, BorderLayout.SOUTH);
            
            save.addActionListener((ActionEvent e) -> {
		if(className.getText().trim().equals("") ||  classNum.getText().trim().equals("")) {
		    JOptionPane.showMessageDialog(null, "Data Not Entered");   
		}
		else if(!classNum.getText().matches("[0-9]+")) {
		    JOptionPane.showMessageDialog(null, "Invalid Class Number");
		    classNum.requestFocus();
		}
		else {
		    String name = className.getText();
		    int number = Integer.valueOf(classNum.getText());
		    Class c3 = new Class();
		    c3.className= name;
		    c3.classNum = number;
		    c3.createFile();
		    c3.createStudentsFile();
		    c3.createRubricFile();
		    c3.createAssignmentsFile();
		    semester.list.addElement(c3);
		    semester.createClassFile();
		    //calls the new student pop up once the class has been saved
		    semester.lastEdited = c3;
		    AddStudent studentPopUp = new AddStudent(semester);
		    studentPopUp.setVisible(true);
		    studentPopUp.setLocation(500, 500);
		    studentPopUp.setSize(350, 150);
		    
                    dispose(); //this just shuts the window once everything is done
		}
            });
            
            getContentPane().add(addClass);
        }    
    }
    
    public static class AddAssignment extends JFrame {
        public AddAssignment(ClassList semester) { //this will need to call variables needed for assignment
            super("New Assignment");
	   
            JPanel addAssignment = new JPanel();
            addAssignment.setLayout(new BorderLayout());
            
            //all the elements 
            JButton save = new JButton("Save");
	    Color addAssignmentColor = new Color(65,169,181);
	    save.setBackground(addAssignmentColor);
            JLabel label1 = new JLabel("Assignment Type:", SwingConstants.CENTER);
            String[] types = {"Homework", "Quiz", "Lab"}; 
            JComboBox<String> assignmentType = new JComboBox<String>(types);
            
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(0, 2, 10, 10));
            JPanel savePanel = new JPanel();
            
            inputPanel.add(label1);
            inputPanel.add(assignmentType);
            
            savePanel.add(save, SwingConstants.CENTER);
            
            addAssignment.add(inputPanel, BorderLayout.CENTER);
            addAssignment.add(savePanel, BorderLayout.SOUTH);
          
            save.addActionListener((ActionEvent e) -> {
		    int type = assignmentType.getSelectedIndex();
		    semester.lastEdited.addAssignment(type);
		    semester.lastEdited.createAssignmentsFile();
		    semester.saveLast();
		    semester.loadTable(semester.lastEdited);
		    Object[][]data = semester.currClassData;
		    String columns[] = semester.columnNames;
		    JTable table = new JTable(data, columns);
		    Layout test = new Layout(table, semester);
		    test.setVisible(true);
		    test.setSize(1500, 1000);
		    test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               	    dispose(); //this just shuts the window once everything is done
		    
           });
            getContentPane().add(addAssignment);
	   

        }
        
    }
    
    public static class AddStudent extends JFrame {
        public AddStudent(ClassList semester) { //this will need to call variables needed for student
            super("New Student");
	    
            JPanel addStudent = new JPanel();
            addStudent.setLayout(new BorderLayout());
            
            //all the elements
            JButton save = new JButton("Save");
            JButton savePlus = new JButton("Save & Add Another");
	    Color addStudentColor = new Color(65,169,181);
	    save.setBackground(addStudentColor);
	    savePlus.setBackground(addStudentColor);
            JLabel label1 = new JLabel("First Name:");
            JTextField firstName = new JTextField("", 25);
            JLabel label2 = new JLabel("Last Name:");
            JTextField lastName = new JTextField("", 25);
	    JLabel label3 = new JLabel("Student ID:");
	    JTextField studentID = new JTextField("", 10);

            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(0, 2, 10, 10));
            JPanel savePanel = new JPanel();
            
            inputPanel.add(label1);
            inputPanel.add(firstName);
	    inputPanel.add(label2);
	    inputPanel.add(lastName);
            inputPanel.add(label3);
            inputPanel.add(studentID);
            
            savePanel.add(save, SwingConstants.CENTER);
            savePanel.add(savePlus);
            
            addStudent.add(inputPanel, BorderLayout.CENTER);
            addStudent.add(savePanel, BorderLayout.SOUTH);

	    
            save.addActionListener((ActionEvent e) -> {
		    if(studentID.getText().trim().equals("") || firstName.getText().trim().equals("") || lastName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Data Not Entered");
		    }
		    else if (!studentID.getText().matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null, "Invalid Student ID");
			studentID.requestFocus();
		    }
		    else {
		    	Student test = new Student("Test","McTesterson",000);
		    	test.fName = firstName.getText();
			test.lName = lastName.getText();
		   	test.student_id = Integer.valueOf(studentID.getText());
		    	semester.lastEdited.addStudent(test);
		    	semester.lastEdited.createStudentsFile();
		    	semester.lastEdited.createFile();
			semester.saveLast();
			semester.loadTable(semester.lastEdited);
			Object[][]data = semester.currClassData;
			String columns[] = semester.columnNames;
			JTable table = new JTable(data, columns);
			Layout update = new Layout(table, semester);
			update.setVisible(true);
			update.setSize(1500, 1000);
			update.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			dispose(); 
		    }
            });
            
            savePlus.addActionListener((ActionEvent e) -> {
		    if(studentID.getText().trim().equals("") || firstName.getText().trim().equals("") || lastName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Data Not Entered");
		    }
		    else if(!studentID.getText().matches("[0-9]+")) {
		    	JOptionPane.showMessageDialog(null, "Invalid Student ID");
			studentID.requestFocus();
		    }
		    else {
		    	Student test = new Student("Testy","McTesterson",000);
		    	test.fName = firstName.getText();
			test.lName = lastName.getText();
		    	test.student_id = Integer.valueOf(studentID.getText());
		    	semester.lastEdited.addStudent(test);
		    	semester.lastEdited.createStudentsFile();
			semester.lastEdited.createFile();
		    	AddStudent studentPopUp = new AddStudent(semester);
		  	studentPopUp.setVisible(true);
		    	studentPopUp.setLocation(500, 500);
		    	studentPopUp.setSize(350, 150);

		    	dispose();
		    }
            });            
                     
            getContentPane().add(addStudent); 
        }
    }
    
    public class FinalGrades extends JFrame {
        public FinalGrades(Class c1) { //this will need to call any variables needed for final grade computation
            super("Final Grades");
	    JPanel finalGrades = new JPanel(new BorderLayout());
	    Color finalGradesColor = new Color(65, 169, 181);
	    JPanel gradesButtons = new JPanel();
	    JPanel gradesPanel = new JPanel(new GridLayout(0, 2, 10, 5));

	    JButton ok = new JButton("Okay");
	    ok.setBackground(finalGradesColor);
	    gradesButtons.add(ok);
	    ok.addActionListener((ActionEvent e) -> {
		dispose();
	    });		    

            JLabel label1 = new JLabel("Student Grades:", SwingConstants.CENTER);
	    finalGrades.add(label1, BorderLayout.NORTH);

	    //add grades to gradesPanel here
	    for(int i=0; i<c1.gradebook.size(); i++) {
		double grade = c1.getWeightedAverageForStudent(i);	
		DecimalFormat df = new DecimalFormat("#.##");	
		grade = Double.valueOf(df.format(grade));
		JLabel studentName = new JLabel(c1.students.get(i).getFirstName() + " " + c1.students.get(i).getLastName());
		JLabel studentGrade = new JLabel(Double.toString(grade));
	 	gradesPanel.add(studentName);
		gradesPanel.add(studentGrade);
	    }

            finalGrades.add(new JScrollPane(gradesPanel), BorderLayout.CENTER);
            finalGrades.add(gradesButtons, BorderLayout.SOUTH);
            
            getContentPane().add(finalGrades);
}
    }
    
    public class EditRubric extends JFrame {
        public EditRubric(Class c1) { //will need to call variables needed for rubric
            super("Edit Rubric");
	    
	    String HWValue = Integer.toString(c1.R.getHWValue());
	    String QuizValue = Integer.toString(c1.R.getQuizValue());
	    String LabValue = Integer.toString(c1.R.getLabValue());
            String ECValue = Integer.toString(c1.R.getExtraCreditValue());
	    String ParticValue = Integer.toString(c1.R.getParticipationValue());
	
            //all the elements
	    
            JPanel editRubric = new JPanel(new BorderLayout());
            JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
            JPanel savePanel = new JPanel();
	    Color editRubricColor = new Color(65,169,181);
            JButton save = new JButton("Save");
	    save.setBackground(editRubricColor);
	    JLabel label1 = new JLabel("Homework Value:", SwingConstants.CENTER);
            JTextField homework = new JTextField(HWValue, 10);
            JLabel label2 = new JLabel("Quiz Value:", SwingConstants.CENTER);
            JTextField quiz = new JTextField(QuizValue, 10);
	    JLabel label3 = new JLabel("Lab Value:", SwingConstants.CENTER);
	    JTextField lab = new JTextField(LabValue, 10);
	    JLabel label4 = new JLabel("Participation:", SwingConstants.CENTER);
	    JTextField pG = new JTextField(ParticValue, 10);
	    JLabel label5 = new JLabel("Extra Credit:", SwingConstants.CENTER);
	    JTextField ExC = new JTextField(ECValue, 10);
            
            inputPanel.add(label1);
            inputPanel.add(homework);
            inputPanel.add(label2);
            inputPanel.add(quiz);
            inputPanel.add(label3);
	    inputPanel.add(lab);
	    inputPanel.add(label4);
	    inputPanel.add(pG);
	    inputPanel.add(label5);
	    inputPanel.add(ExC);
            
            save.addActionListener((ActionEvent e) -> {
		    // test.student_id = Integer.valueOf(studentID.getText())
		    int HW = Integer.valueOf(homework.getText());
		    int QZ = Integer.valueOf(quiz.getText());
		    int LAB = Integer.valueOf(lab.getText());
		    int participation = Integer.valueOf(pG.getText());
		    int extraC = Integer.valueOf(ExC.getText());
		    c1.R.editRubricValues(HW, QZ, LAB, participation, extraC);
		    c1.createRubricFile();
                
                dispose();
            });
            
            savePanel.add(save);
            editRubric.add(inputPanel);
            editRubric.add(savePanel, BorderLayout.SOUTH);
            
            getContentPane().add(editRubric);
        }
    }
    
    public Layout(JTable grades, ClassList semester) { //pretty much I think layout will have to call like every variable unless 
                                   //there's another way to do things that I'm just missing? 
        super("Your Gradebook");
        Class c1 = semester.lastEdited;
        //will be set equal to the actual class name variable at some point, used for titles in all panels
        //String title = "Class Name";
	String title = c1.className+c1.classNum;

        //panels for the split frame
        JPanel navPanel = new JPanel(new BorderLayout());
  	Dimension navButtonSize = new Dimension(200, 25);
        
        //creating the tabbedpane
        JTabbedPane tabbedPane = new JTabbedPane();  
        
        //creating the split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navPanel, tabbedPane);
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerLocation(250);
        splitPane.setBackground(Color.WHITE);
        
        //navigation panel
        JLabel navTitle = new JLabel("NAVIGATION", SwingConstants.CENTER);
        navTitle.setFont(new Font("Arial", Font.BOLD, 18));
        
        JPanel navButtonPanel = new JPanel();
	navButtonPanel.setBackground(Color.WHITE);
	navButtonPanel.setLayout(new BoxLayout(navButtonPanel, BoxLayout.PAGE_AXIS));
	navButtonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        JButton addNewClassButton = new JButton("Add New Class");
	addNewClassButton.setMaximumSize(navButtonSize);
	addNewClassButton.setBackground(new Color(141, 232, 156));
	addNewClassButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addNewClassButton.addActionListener((ActionEvent e) -> {
	    AddClass addClassPopUp = new AddClass(semester, c1);
            addClassPopUp.setVisible(true);
            addClassPopUp.setLocation(500, 500);
            addClassPopUp.setSize(350, 150);
        });
        navButtonPanel.add(addNewClassButton);
	navButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	        
        navPanel.add(navTitle, BorderLayout.NORTH);
        navPanel.add(navButtonPanel, BorderLayout.CENTER);
        navPanel.setBackground(Color.WHITE);
	for(int i =0;i<semester.list.size();i++){
	    int number =semester.list.get(i).classNum;
	    String name =semester.list.get(i).className;
	    String key = name+number;
	    JButton ClassButton = new JButton(key);          
	    ClassButton.setMaximumSize(navButtonSize);
	    if(title.equals(key)) {
		ClassButton.setBackground(new Color(229, 249, 255));
	    }
	    else {	   
	    	ClassButton.setBackground(new Color(166, 221, 237));
	    }
	    ClassButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    navButtonPanel.add(ClassButton);
	    navButtonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
	    if(!title.equals(key)) {
	    	ClassButton.addActionListener((ActionEvent e) -> {
		    Class last = new Class(name, number);
		    semester.lastEdited = last;
		    semester.saveLast();
		    semester.loadTable(last);
		    Object[][]data = semester.currClassData;
		    String columns[] = semester.columnNames;
		    JTable table = new JTable(data, columns);
		    Layout test = new Layout(table, semester);
		    test.setVisible(true);
                    test.setSize(1500, 1000);
                    test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 		
		    dispose();
		});
	    }
	}
        
        //grades panel
        JPanel gradesPanel = new JPanel();
        gradesPanel.setLayout(new BorderLayout());
        
        //panel for buttons under grades
        JPanel gradesButtons = new JPanel();
        
        //save grades button

        JButton saveGradesButton = new JButton("Save Grades");
	Color gradesButtonColor = new Color(190,190,190);
	saveGradesButton.setBackground(gradesButtonColor);
        saveGradesButton.addActionListener((ActionEvent e) -> {
		if(c1.validTable(grades)==true){
		    c1.createFile(grades);   
		    JOptionPane.showMessageDialog(null, "Grades Saved");
		}
		else
		    JOptionPane.showMessageDialog(null, "Invalid Grades");
        });
        
        //add Assignment Button
        JButton addAssignmentButton = new JButton ("Add Assignment");
	addAssignmentButton.setBackground(gradesButtonColor);
        addAssignmentButton.addActionListener((ActionEvent e) -> {
            AddAssignment assignmentPopUp = new AddAssignment(semester);
            assignmentPopUp.setVisible(true);
            assignmentPopUp.setLocation(500, 500);
            assignmentPopUp.setSize(350, 110);
	    
	    //dispose();
        });
        
        //add Student Button
        JButton addStudentButton = new JButton ("Add Student");
	addStudentButton.setBackground(gradesButtonColor);
        addStudentButton.addActionListener((ActionEvent e) -> {
            AddStudent studentPopUp = new AddStudent(semester);
            studentPopUp.setVisible(true);
            studentPopUp.setLocation(500, 500);
            studentPopUp.setSize(350, 150);
	    //dispose();
        });
        
        //final Grades Button
        JButton finalGradesButton = new JButton ("Compute Final Grades");
	finalGradesButton.setBackground(gradesButtonColor);
        finalGradesButton.addActionListener((ActionEvent e) -> {
            FinalGrades finalGradesPopUp = new FinalGrades(c1);
            finalGradesPopUp.setVisible(true);
            finalGradesPopUp.setSize(300,250);
            finalGradesPopUp.setLocation(500,500);
        });
        
        gradesButtons.add(saveGradesButton);
        gradesButtons.add(addAssignmentButton);
        gradesButtons.add(addStudentButton);
        gradesButtons.add(finalGradesButton);
        gradesButtons.setBackground(Color.WHITE);
        
        //scroll pane for grades table & table formatting
        JScrollPane gradeScrollPane = new JScrollPane(grades);
        gradeScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title + " Grades", TitledBorder.LEFT, TitledBorder.TOP));
        grades.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	for(int i=0; i<5; i++) 
	    grades.getColumnModel().getColumn(i).setPreferredWidth(100);
	for(int i=5; i<grades.getColumnCount(); i++)
	    grades.getColumnModel().getColumn(i).setPreferredWidth(50);
        gradeScrollPane.setBackground(Color.WHITE);
        gradesPanel.add(gradeScrollPane, BorderLayout.CENTER);
        gradesPanel.add(gradesButtons, BorderLayout.SOUTH);
        tabbedPane.add(gradesPanel, "Grades");

        //rubric panel
        JPanel rubricPanel = new JPanel(new BorderLayout());
        tabbedPane.add(new JScrollPane(rubricPanel), "Rubric");
        
        //content panel, displays rubric 
        JPanel rubricContent = new JPanel();
	rubricContent.setLayout(new BoxLayout(rubricContent, BoxLayout.PAGE_AXIS));
        rubricContent.setBackground(Color.WHITE);
        rubricContent.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title + " Rubric", TitledBorder.LEFT, TitledBorder.TOP));
	
	//labels + formatting for rubric panel 
	Font rubricFont = new Font("Arial", Font.PLAIN, 18);
	JLabel HWLabel = new JLabel("Homework Value: " + c1.R.getHWValue());
	HWLabel.setFont(rubricFont);
	JLabel QuizLabel = new JLabel("Quiz Layout: " + c1.R.getQuizValue());    
	QuizLabel.setFont(rubricFont);
	JLabel LabLabel = new JLabel ("Lab Value: " + c1.R.getLabValue());
	LabLabel.setFont(rubricFont);
	JLabel ParticLabel = new JLabel ("Participation Value: " + c1.R.getParticipationValue());
	ParticLabel.setFont(rubricFont);
	JLabel ECLabel = new JLabel ("Extra Credit Value: " + c1.R.getExtraCreditValue());
	ECLabel.setFont(rubricFont);    
		
	rubricContent.add(HWLabel);
	rubricContent.add(QuizLabel);
	rubricContent.add(LabLabel);
	rubricContent.add(ParticLabel);
	rubricContent.add(ECLabel);
 
       //button panel for rubric
        JPanel rubricButtons = new JPanel();
        rubricButtons.setBackground(Color.WHITE);
        JButton editRubricButton = new JButton("Edit Rubric");
	editRubricButton.setBackground(gradesButtonColor);
        rubricButtons.add(editRubricButton, SwingConstants.CENTER);
        editRubricButton.addActionListener((ActionEvent e) -> {
            EditRubric editRubricPopUp = new EditRubric(c1);
            
            editRubricPopUp.setVisible(true);
            editRubricPopUp.setSize(310, 250);
            editRubricPopUp.setLocation(500, 500);
        });
	
        rubricPanel.add(rubricContent, BorderLayout.CENTER);
        rubricPanel.add(rubricButtons, BorderLayout.SOUTH);
        
        
        //stats panel
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setBackground(Color.WHITE);
        tabbedPane.add(new JScrollPane(statsPanel), "Statistics");

	//calling & declaring all stats values
	double classAvg = c1.getClassAverage();
	double classStdDev = c1.getClassStdDev();
	double classHigh = c1.getClassMax();
	double classLow = c1.getClassMin();
	double classMode = c1.getMode();
	double classMedian = c1.getMedian();
       	DecimalFormat df = new DecimalFormat("#.##");
	classAvg = Double.valueOf(df.format(classAvg));
	classStdDev = Double.valueOf(df.format(classStdDev));
	classHigh = Double.valueOf(df.format(classHigh));
	classLow = Double.valueOf(df.format(classLow));
	classMode = Double.valueOf(df.format(classMode));
	classMedian = Double.valueOf(df.format(classMedian));
	
	//creating/formatting the stats panel
	JPanel statsContent = new JPanel();
	statsContent.setLayout(new BoxLayout(statsContent, BoxLayout.PAGE_AXIS));
	statsContent.setBackground(Color.WHITE);
	statsContent.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title + " Statistics", TitledBorder.LEFT, TitledBorder.TOP));

	JLabel classAvgLabel = new JLabel("Class Average: " + classAvg);
	JLabel classStdDevLabel = new JLabel("Class Standard Deviation: " + classStdDev);
	JLabel classHighLabel = new JLabel("Class High: " +classHigh);
	JLabel classLowLabel = new JLabel("Class Low: " +classLow);
	JLabel classModeLabel = new JLabel("Class Mode: "+classMode);
	JLabel classMedianLabel = new JLabel("Class Median: "+classMedian);
	
	classAvgLabel.setFont(rubricFont);
	classStdDevLabel.setFont(rubricFont);
	classHighLabel.setFont(rubricFont);
	classLowLabel.setFont(rubricFont);
	classModeLabel.setFont(rubricFont);
	classMedianLabel.setFont(rubricFont);
	
	statsContent.add(classAvgLabel);
	statsContent.add(classStdDevLabel);
	statsContent.add(classHighLabel);
	statsContent.add(classLowLabel);
	statsContent.add(classModeLabel);
	statsContent.add(classMedianLabel);

	statsPanel.add(statsContent, BorderLayout.CENTER);
  
        getContentPane().add(splitPane, BorderLayout.CENTER);
        setSize(1500, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    public static class ClassList{ //overarching class composed of Classes
	private Vector<Class> list; //contains all the classes of the program
	Object [][]currClassData; //used for table display, specifically data
	String[] columnNames; //used for table display, specifically reading
	Class lastEdited; //keeps track of which class was most recently edited

	/*
	  Purpose: dynamically load contents of a class for display in a layout
	  @param currClass: the class to be displayed in the layout
	  @precondition: a class has been chosen for display
	  @postcondition: a class' data and assignment types have been passed to the program
	 */
	public void loadTable(Class currClass){
	    String columns[] = new String[currClass.values.size()+3]; //accounts for the First, last, and studentID spaces   
	    columns[0]="First Name";
	    columns[1]="Last Name";
	    columns[2]="Student ID";
	    String title= " "; //will be used for display of an assignment type
	    int hwNum =0; //keeps track of how many hw have been created
	    int quizNum=0; //keeps track of how many quizzes have been created
	    int labNum=0; //keeps track of how many labs have been created
	    for (int i=0;i<currClass.values.size();i++){
	    if(currClass.values.get(i)==0){
		hwNum++;
		title = "HW "+hwNum;
	    }
	    if(currClass.values.get(i)==1){
		quizNum++;
		title = "Quiz "+quizNum;
	    }
	    if(currClass.values.get(i)==2){
		labNum++;
		title = "Lab "+labNum;
	    }
	    if(currClass.values.get(i)==3)
		title = "Participation";
	    if(currClass.values.get(i)==4)
		title = "Extra Credit";
	    columns[i+3]=title; //adds the title of each assignment to the columns for proper type display
	    }
	    
	    Object[][]data = new Object[currClass.students.size()][currClass.values.size()+3]; //accounts for number of students and grades with +3 added to account for first/last names and iD
	    for(int i =0;i<currClass.students.size();i++){
		data[i][0]=currClass.students.get(i).fName; //fills first name of each student for the class
		data[i][1]=currClass.students.get(i).lName; //fills last name of each student for the class
		for(int k =2;k<=currClass.gradebook.get(i).size()+1;k++){ //fills remainder of data table with grades from the class' gradebook 
		    data[i][k] = currClass.gradebook.get(i).get(k-2); 
		}
	    }
	    //passes all column names and data to the current class
	    columnNames = columns; 
	    currClassData = data;
	}

	/*
	  Purpose: Keep track of last edited file for use when reopening program, so user sees most recent data submitted
	  @precondition: lastEdit.txt may or may not exist, either way it does not hold the true last edited class name and number
	  @postcondition: lastEdit.txt has been created and saved, it holds the name and number of the last edited class
	 */
	public void saveLast(){
	    try{
		File file = new File("./Classes/lastEdit.txt"); //opens lastEdit.txt for editing 
		if(!file.exists())
		    file.createNewFile(); //creates file if it does not already exist, should only happen when running program for first time
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(String.valueOf(this.lastEdited.classNum)); //grabs classNumber from the last Edited class and writes it to file 
		bw.write(" ");
		bw.write(this.lastEdited.className); //grabs className from the last Edited class and writes it to a file 
		bw.write("\n");
		bw.close();
	    }catch(IOException e){}
	}

	/*
	  Purpose: retrieves last edited file from lastEdit.txt in order to ensure user sees most recently changed data 
	  @precondition: lastEdit.txt exists for retrieval
	  @postcondition: className and classNum have been retrieved from the lastEdit.txt file 
	 */
	public void loadLast(){
	    try{
		File file = new File("./Classes/lastEdit.txt");
    		BufferedReader br = new BufferedReader (new FileReader (file));
		String line;
		while ((line = br.readLine ()) != null) //checks whether a line still holds content 
		    {
			StringTokenizer st = new StringTokenizer (line); 
			while (st.hasMoreTokens ())
			    {
				int number = Integer.valueOf(st.nextToken()); //stores the class Number in the classList
				String name = st.nextToken(); //stores the class Name in the classList
				Class test = new Class(name,number);//runs constructor on a class with the lastEdited className, classNumber 
				this.lastEdited = test; //selects chosen class as the last Edited class in the classList
				
			    }
		    }	      
	    }catch (IOException e){}
	}
	/*
	  Purpose: convert a list of classes into a classList
	  @precondition: a list of classes has been passed to the function 
	  @postcondition: the classList has been updated with the new list of classes
	 */
	public void addClasses(Vector<Class> item){
	    this.list = item;
	}

	/*
	  Purpose: load all classes from ClassList.txt into the main program to be edited and displayed
	  @precondition: a classList file has been created containing current classes
	  @postcondition: the classes for the program have been loaded from the ClassList.txt
	 */
	public void loadClassFile(){
	    try{
		File file = new File("./Classes/ClassList.txt"); //file used for loading classes from 
		Vector<Class>semester = new Vector<Class>(); //created to store classes as they are loaded from the file 
		BufferedReader br = new BufferedReader (new FileReader (file));
		String line;
		while ((line = br.readLine ()) != null) //checks whether file is empty 
		    {
			StringTokenizer st = new StringTokenizer (line); 
			while (st.hasMoreTokens ()) //if a line still has content it will continue
			    {
				int number = Integer.valueOf(st.nextToken()); //grabs number of the class from a file 
				String name = st.nextToken(); //grabs name of the class from a file 
				if (!st.hasMoreTokens ()){ //once a line is done, it will create a class with a constructor consisting of class name and class number 
				    Class sample = new Class(name,number);
				    semester.addElement (sample);//adds class to a list which will be assigned to the classList's list variable 
				}
			    }
		    }	      
		this.list = semester; //assigns new classList to the class List
	    }catch(IOException e){}
	}

	/*
	  Purpose: creates a classList file storing all classNames and classNums 
	  @precondition: classes have been loaded into a classList to be saved
	  @postcondition: classes have been saved into a file from a classList 
	 */
	public void createClassFile(){
	    try{
		File file = new File("./Classes/ClassList.txt");
		if(!file.exists()) //if the file has not been created yet, it will be created. Will really only be used for first time use 
		    file.createNewFile();
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		for(int i =0;i<list.size();i++){ //runs through all classes in the class List 
		    bw.write(String.valueOf(list.get(i).classNum)); //stores the classNum as a string in the file 
		    bw.write(" ");
		    bw.write(list.get(i).className); //stores the className into the file 
		    bw.write("\n");
		}
		bw.close();
	    }catch(IOException e){}
	}
	
    }
    
    public static class Student{
	private String fName;	//Student first name
	private String lName; //student last name
	private int student_id;	//Student ID

	//Constructor for Student
	public Student (String first_name, String last_name, int ID)
	{
	    fName = first_name;
	    lName = last_name;
	    student_id = ID;
	}
	
	
	//Returns the student's first name
	public String getFirstName ()
	{
	    return fName;
	}

	//returns the student's last name
	public String getLastName(){
	    return lName;
	}

	//Returns the student's ID
	public int getID ()
	{
	    return student_id;
	}
    }

    public static class Rubric
    {
	private int HWValue;	//Value of a homework assignment for a class
	private int quizValue;	//Value of a quiz for a class
	private int labValue;	//Value of a lab assignment for a class
	private int participationValue;	//Value of participation for a class
	private int extraCreditValue;	//Value of extra credit for a class
	
	//Default constructor
	public Rubric ()
	{
	    HWValue = 10;
	    quizValue = 100;
	    labValue = 50;
	    participationValue=50;
	    extraCreditValue=100;
	}
	
	//Constructor with values
	public Rubric(int HW, int quiz, int lab, int PV, int ECV){
	    HWValue = HW;
	    quizValue = quiz;
	    labValue = lab;
	    participationValue=PV;
	    extraCreditValue=ECV;}   //Constructor with values
 
	
	//Returns value for a homework assignment
	public int getHWValue ()
	{
	    return HWValue;
	}
	
	//Returns value for a quiz
	public int getQuizValue ()
	{
	    return quizValue;
	}
	
	//Returns value for a lab
	public int getLabValue ()
	{
      return labValue;
	}
	
	//Returns participation value
	public int getParticipationValue(){
	    return participationValue;}
	
	//Returns extra credit value
	public int getExtraCreditValue(){
	    return extraCreditValue;}
	
	//Call this to change the values for each type of assignment
	public void editRubricValues(int HW, int quiz, int lab, int PV, int ECV){
	    HWValue = HW;
	    quizValue = quiz;
	    labValue = lab;
	    participationValue=PV;
	    extraCreditValue=ECV;}
    }
    
    
    public static class Class
    {
	private Vector < Vector < Integer >> gradebook;	//Vector matrix that stores the student ID as the first
	//As the first element in every row. Every other
	//cell is a grade
	private Vector<Student>students;
	private Vector<Integer> values;
	private int numHWs;		//# of homework assignments
	private int numQuizzes;	//# of quizzes
	private int numLabs;	//# of lab assignments
	private Rubric R;		//The rubric for the class
	private String className;	//name of class
	private int classNum;
	Boolean locked;          //boolean value for locking class 
	
	
	//Default constructor
	public Class()
	{
	    gradebook = new Vector<Vector<Integer>>();
	    Student testy = new Student("Testy","McTesterson",-1); //default student to be replaced once an actual student has been added
	    Vector<Integer> grades = new Vector<Integer>();
	    grades.addElement(-1);
	    gradebook.addElement(grades);
	    students = new Vector<Student>();
	    values = new Vector<Integer>();
	    this.addAssignment(3);
	    this.addAssignment(4);
	    numHWs = 0;
	    numQuizzes = 0;
	    numLabs = 0;
	    R = new Rubric();
	    locked = false;
	    className = "default";
	    classNum = 000;
	}
	
	//Simple Constructor used in initial testing 
	public Class (Vector < Vector < Integer >> SandG,Vector<Student> pupils,Vector<Integer> indexes, int HWs, int Quizzes,
		      int Labs, String name, int number)
	{
	    gradebook = new Vector < Vector < Integer >> (SandG);
	    students = new Vector <Student>(pupils);
	    values = new Vector<Integer>(indexes);
	    numHWs = HWs;
	    numQuizzes = Quizzes;
	    numLabs = Labs;
	    className = name;
	    classNum = number;
	    R = new Rubric ();
	    locked = false;
	    String key = this.className+this.classNum;
	    File mydir = new File("./Classes/"+key);
	    if(!mydir.exists()) //creates a directory for the class if it has not yet been created
		mydir.mkdir();
	}
	
	/*
	  Purpose: constuct a class for display/editing from standard files associated with the class
	  @param name: used when constructing the key used in file loading
	  @param number: used when constructing the key used in file loading
	  @precondition: a class name and number have been passed to be used by the constructor
	  @postcondition: a class has been constructed from its loaded files 
	 */
	public Class(String name,int number)
	{
	    className = name; 
	    classNum = number;
	    this.returnStudentsFile(); //grabs student vector from a file for the class' Student vector
	    this.returnAssignmentsFile(); //grabs Assignment type vector from a file for the class' Assignments vector
	    this.returnRubricFile(); //grabs Rubric content from a file for the class' Rubric variable 
	    numHWs =0; 
	    numLabs = 0;
	    numQuizzes =0;
	    for(int i=0;i<this.values.size()-1;i++){ //used to keep track of each number of assignment type 
		if(this.values.get(i)==0)
		    numHWs++;
		if(this.values.get(i)==1)
		    numQuizzes++;
		if(this.values.get(i)==2)
		    numLabs++;
	    }
	    locked = false; //ensures the class can currently be edited 
	    try
		{
		    String key = this.className+this.classNum; //used name and number to load from directoy and files for class 
		    File mydir = new File("./Classes/"+key); 
		    File file = new File("./Classes/"+key+"/"+key+"grades.txt");
		    Vector < Vector < Integer >> matrix = new Vector < Vector < Integer >> ();  //vector of vectors
		    BufferedReader br = new BufferedReader (new FileReader (file));
		    String line;
		    while ((line = br.readLine ()) != null) //continues to read file so long as it has content 
			{
			    StringTokenizer st = new StringTokenizer (line);
			    int num = 0;
			    Vector < Integer > test = new Vector < Integer > ();   //vector used for input
			    test.clear ();
			    while (st.hasMoreTokens ())
				{
				    int value1 = Integer.parseInt (st.nextToken ()); //grabs an int value for each parsed string token 
				    test.addElement (value1); //adds each integer to a vector which will be inserted into the gradebook vector
				    if (!st.hasMoreTokens ())
					matrix.addElement (test);   //inserts entire test vector into the main vector
				}
			}
		    gradebook = matrix; //changes class' gradebook to match that of the loaded grades from a file 
		    
		}
	    catch (IOException e)
		{
		}
	    
	}

	/*
	  Purpose: create standard students file for a class
	  @precondition: a class has been filled with a student's vector to be saved 
	  @postcondition: a class's students vector has been saved to a standard file 
	 */
	public void createStudentsFile(){
	    try{
		String key = this.className+this.classNum; //used to ensure standard file system based on className and classNum 
		File file = new File("./Classes/"+key+"/"+key+"Students.txt");
		if(!file.exists()) //used when the file has never been created before 
		    file.createNewFile();
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		for(int i =0;i<students.size();i++){
		    bw.write(String.valueOf(students.get(i).getID())); //stores studentID first in file 
		    bw.write(" ");
		    bw.write(students.get(i).getFirstName()); //stores first Name of student for display in table 
		    bw.write(" ");
		    bw.write(students.get(i).getLastName()); //stores last Name of student for display in table 
		    bw.write("\n");
		}
		bw.close();
	    }catch(IOException e){}
	}
	/*
	  Purpose: load students vector for a class from a standard file 
	  @precondition: a students file exists for a chosen class
	  @postcondition: a chosen class' students vector has been loaded from a standard file 
	 */
	public void returnStudentsFile(){
	  try{
	      String key = this.className+this.classNum; //used to ensure standard file system 
	      File file = new File("./Classes/"+key+"/"+key+"Students.txt");
	      Vector<Student>pupils = new Vector<Student>(); //temp vector to store students 
	      BufferedReader br = new BufferedReader (new FileReader (file));
              String line;
              while ((line = br.readLine ()) != null)
		  {
		      StringTokenizer st = new StringTokenizer (line); 
		      Student test = new Student("Testy","McTesterson", 999); //creates generic student with information to be replaced 
		      while (st.hasMoreTokens ())
			  {
			      test.student_id = Integer.valueOf(st.nextToken()); //stores first value as the student's ID 
			      test.fName = st.nextToken(); //stores second String value as student's first name 
			      test.lName = st.nextToken(); //stores last string value as student's last name 
			      if (!st.hasMoreTokens ())
				  pupils.addElement (test); //once a sting is done, a student is added to the student's vector
			  }
		  }	      
	      this.students = pupils; //class' student vector is assigned the same values as the loaded temporary vector
	      
	  }catch(IOException e){}
	}
	
	/*
	  Purpose: create standard file to contain the current values for the Rubric types
	  @precondition: a class has been created with a Rubric file, either default or edited
	  @postcondition: a standard file has been created containing the rubric's current values 
	*/
	public void createRubricFile(){
	  try{
	      String key = this.className+this.classNum; //used to ensure standard file format 
		  File file = new File("./Classes/"+key+"/"+key+"Rubric.txt");
		  if(!file.exists()) //used for initial file creation 
			  file.createNewFile();
		  FileWriter fw = new FileWriter(file);
		  BufferedWriter bw = new BufferedWriter(fw);
		  bw.write(String.valueOf(R.HWValue)); //stores first value as the rubric's HW value 
		  bw.write("\n");
		  bw.write(String.valueOf(R.quizValue)); //stores second value as the rubric's quiz value
		  bw.write("\n");
		  bw.write(String.valueOf(R.labValue)); //stores third value as the rubric's lab value 
		  bw.write("\n");
		  bw.write(String.valueOf(R.participationValue));//stores fourth value as the rubric's participation value 
		  bw.write("\n");
		  bw.write(String.valueOf(R.extraCreditValue)); //stores last valye as the rubric's extraCredit value 
		  bw.close();
	  }catch(IOException e){}
	}

	/*
	  Purpose: loads rubric type values from a standard file
	  @precondition: a rubric file exists for a class in standard format 
	  @postcondition: a class' rubric values will be loaded from a standard file 
	 */
	public void returnRubricFile(){
	    try{
		String key = this.className+this.classNum; //used to ensure standard file format is used 
		File file = new File("./Classes/"+key+"/"+key+"Rubric.txt");
		BufferedReader br = new BufferedReader (new FileReader (file));
		String line;
		Rubric test = new Rubric();
		//the following are used to represent empty values for each type in the standard rubricFile 
		test.HWValue = 0; 
		test.quizValue = 0;
		test.labValue = 0;
		test.participationValue = 0;
		test.extraCreditValue=0;
		int i = 0;
		while ((line = br.readLine ()) != null)
		    {
			StringTokenizer st = new StringTokenizer (line); 
			while (st.hasMoreTokens ())
			    {
				if(i ==0)
				    test.HWValue = Integer.valueOf(st.nextToken()); //first line value equal homework value
				if(i==1)
				    test.quizValue = Integer.valueOf(st.nextToken()); //second line value equals quiz value 
				if(i==2)
				    test.labValue = Integer.valueOf(st.nextToken()); //third line value equal lab value 
				if(i==3)
				    test.participationValue = Integer.valueOf(st.nextToken()); //fourth line value equals participation value 
				if(i==4)
				    test.extraCreditValue = Integer.valueOf(st.nextToken()); //fifth line value equals extraCredit value 
			    }
			i++;
		    }
		this.R = test; //passes temp rubric value to class' rubric content
	    }catch(IOException e){}
	}
	
	/*
	  Purpose: create a standard file to save the gradebook to for each class. This will be done by using the className and classNum to create a standard file name.
	  This standard file name will be className+ClassNum+.txt and an example would be CISC1600grades.txt where CISC is the class name and 1600 is the class number.
	  Each of these files will be stored within a subdirectory called "Classes".
	  @precondition: a class has been filled with gradebook values which will not be saved to a standard file 
	  @post condition: at the end of the function, a .txt file will have been written containing the entire gradebook of the class. This file will have a unique standard name so that other classes do not conflict with it.
	*/
	public void createFile ()
	{
	    try
		{
		    String key = this.className + this.classNum;	//used to standardize file format
		    File mydir = new File("./Classes/"+key);
		    if(!mydir.exists()) //used if file directory has not yet been created 
			mydir.mkdir();
		    File file = new File ("./Classes/" + key + "/"+key+"grades.txt");	//accesses file of standard format within a subdirectory
		    if (!file.exists ()) //used if file has not yet been created 
			file.createNewFile ();
		    
		    Vector < Vector < Integer >> matrix =new Vector < Vector < Integer >> (); //temporary 2D vector for storing gradebook values 
		    matrix = this.gradebook;
		    FileWriter fw = new FileWriter (file);
		    BufferedWriter bw = new BufferedWriter (fw);
		    for (int i = 0; i < matrix.size (); i++)
			{
			    for (int k = 0; k < (matrix.get (i).size ()); k++)
				{
				    bw.write (String.valueOf ((matrix.get (i)).get (k))); //stores values of the gradebook into strings to be written to the standard file 
				    bw.write (" ");
				}
			    bw.write ("\n");
			}
		    bw.close ();
		    
		}
	    catch (IOException e)
		{
		}
	}

	/*
	  Purpose: generate a file for a class based on grades from the JTable, used when saving grades in savegrades button of GUI 
	  @precondition: values in the JTable were edited and the user pushed the saveGrades button 
	  @postcondition: values from the JTable have been saved to the standard grade file
	 */
	public void createFile(JTable grades) {
		try {
		    String key = this.className + this.classNum; //used for standard file load/saving
		    File file = new File ("./Classes/" + key + "/" + key + "grades.txt");
		    if (!file.exists()) //used if the file does not yet exist 
			file.createNewFile();
		    FileWriter fw = new FileWriter(file);
		    BufferedWriter bw = new BufferedWriter(fw);
		    for(int i=0; i<grades.getRowCount(); i++) {  //checks each row/student in order to save grades 
			for(int j=2; j<grades.getColumnCount(); j++) { //checks each column for ID and grades of each student
			    if(grades.getModel().getValueAt(i,j)!=null) 
				bw.write(grades.getModel().getValueAt(i, j) + " ");
			    else 
				bw.write("0 "); //replaces null values of cell with 0's 
			}
			bw.write("\n");
		    }
		    bw.close();
		}
		catch(IOException e) {
		}
	}

      /*
	Purpose: create or modify a standard file containing a vector of integers representing assignment types
	@precondition: an assignment vector of types has been created and filled for a class, now chosen for saving  
	@postcondition: the assignment types for a class have been saved to a standard file 
       */
      public void createAssignmentsFile(){
		  try{
		      String key = this.className+this.classNum; //used for standard Assignment file format 
		  File file = new File("./Classes/"+key+"/"+key+"Assignments.txt");
		  if(!file.exists()) //used if file has not yet been created 
			  file.createNewFile();
		  FileWriter fw = new FileWriter(file);
		  BufferedWriter bw = new BufferedWriter(fw);
		  for(int i =0;i<values.size();i++){
		      bw.write(String.valueOf(values.get(i))); //saves Assignment types from values vector of the class 
		      bw.write("\n");
		  }
		  bw.close();
		  }catch(IOException e){}
      }
	
	/*
	  Purpose: to return the values of an assignment vector to a gradebook from a standard file 
	  @precondition: a class has been chosen for loading of integer values into its values (Assignments) vector 
	  @postcondition: values have been returned to represent the assignment types for a class
	*/
	public void returnAssignmentsFile(){
	    try{
		String key = this.className+this.classNum; //used for standard file format 
		File file = new File("./Classes/"+key+"/"+key+"Assignments.txt");
		Vector<Integer>indexes = new Vector<Integer>(); //temporary vector which will contain the assignment values  
		BufferedReader br = new BufferedReader (new FileReader (file));
		String line;
		while ((line = br.readLine ()) != null)
		    {
			StringTokenizer st = new StringTokenizer (line); 
			while (st.hasMoreTokens ())
			    {
				int value1 = Integer.parseInt (st.nextToken ()); //stores strings from file as integers to be stored in indexes vector
				if (!st.hasMoreTokens ())
				    indexes.addElement(value1); //pushes assignment value onto the index vector 
			    }
		    }	      
		this.values = indexes; //class' values vector is now equal to the temporary int vector created earlier 
	    }catch(IOException e){}
	}
	  
	  //Prints the whole gradebook
	  public void printGradebook ()
		  {
			  System.out.println (gradebook);
		  }

	  public String printStudentFirstName(int ID){
		  int position = 0;
		  for(int i =0;i<students.size();i++)
			  if(students.get(i).getID()==ID)
				  position = i;
		  return students.get(position).getFirstName();
	  }
      //If the ID is not found, returns the first student's name
      public String printStudentLastName(int ID){
	  int position = 0;
	  for(int i=1;i<students.size();i++)
	      if(students.get(i).getID() == ID)
		  position = i;
					
	  return students.get(position).getLastName();
      }

    //Prints out all grades for a specific student
    public void getGradesForStudent (int ID)
    {
      for (int i = 1; i < (gradebook.get (ID)).size (); i++)
	{
	  //                          System.out.println(gradebook.get(ID));
	  System.out.println ("Grade: " + ((gradebook.get (ID)).get (i)));
	}
    }

    //Prints out all grades for a specific assignment
    public void getGradesForClass (int assignment)
    {
      if (assignment != 0)
	{
	  for (int i = 0; i < gradebook.size (); i++)
	    System.out.println ("Grade: " +
				(gradebook.get (i)).get (assignment));
	}
      else
	System.out.println ("That's for student_id");
    }

    //Prints out the highest grade for a specific assignment
    public void printHighestGradeForAssignment (int assignment)
		  {
			  int max = (gradebook.get (1)).get (assignment);
			  if (assignment != 0)
			  {
				  for (int i = 1; i < gradebook.size (); i++)
					  if ((gradebook.get (i)).get (assignment) > max)
						  max = (gradebook.get (i)).get (assignment);
			  }
			  else
				  System.out.println ("That's for student_id");
			  
			  System.out.println ("The highest grade is: " + max);
		  }

    //Prints out the lowest grade for a specific assignment
    public void printLowestGradeForAssignment (int assignment)
    {
      int low = (gradebook.get (1)).get (assignment);
      if (assignment != 0)
	{
	  for (int i = 1; i < gradebook.size (); i++)
	    if ((gradebook.get (i)).get (assignment) < low)
	      low = (gradebook.get (i)).get (assignment);
	}
      else
	System.out.println ("That's for student_id");

      System.out.println ("The lowest grade is: " + low);
    }


    //Returns the value of the average for a given assignment
    public double getAverageForAssignment (int assignment)
    {
      int total = 0;
      if (assignment != 0)
	for (int i = 0; i < gradebook.size (); i++)
	  total += (gradebook.get (i)).get (assignment);
      else
	System.out.println ("That's for student_id");

      return (double) total / gradebook.size ();
    }

    //Returns the value of the average for a given student
    public double getAverageForStudent (int ID)
    {
      int total = 0;
      for (int i = 1; i < (gradebook.get (ID)).size (); i++)
	total += (gradebook.get (ID)).get (i);

      return (double) total / ((gradebook.get (ID)).size () - 1);
    }

    //Gets Standard Deviation for a given assignment
    public double getStanDevForAssignment (int assignment)
    {
      double total = 0;
      double av = 0;

      for (int i = 0; i < gradebook.size (); i++)
	total += (gradebook.get (i)).get (assignment);

      av = total / gradebook.size ();
      total = 0;

      for (int i = 0; i < gradebook.size (); i++)
	total +=
	  ((gradebook.get (i)).get (assignment) -
	   av) * ((gradebook.get (i)).get (assignment) - av);

      av = total / gradebook.size ();
      av = Math.sqrt (av);

      return av;
    }

    //Gets Standard Deviation for a given student
    public double getStanDevForStudent (int ID)
    {
      double total = 0;
      double av = 0;

      for (int i = 1; i < (gradebook.get (ID)).size (); i++)
	total += (gradebook.get (ID)).get (i);

      av = total / ((gradebook.get (ID)).size () - 1);
      total = 0;

      for (int i = 1; i < (gradebook.get (ID)).size (); i++)
	total +=
	  ((gradebook.get (ID)).get (i) -
	   av) * ((gradebook.get (ID)).get (i) - av);

      av = total / ((gradebook.get (ID)).size () - 1);
      av = Math.sqrt (av);

      return av;
    }
      //Returns the weighted average for the student (using the amount of different assignments)
      public double getWeightedAverageForStudent(int ID){
	  int total=0;
	  double divisor=0;
	  double av=0;
	  
	  //Gets the total points lost.
	  for(int i=1;i<(gradebook.get(ID)).size();i++){
	      if(i != 2)
		  total += (gradebook.get(ID)).get(i);
	  }
	  
	  //Gets the number of homeworks, quizzes, and labs, multiplies each by the respective rubric value
	  //And adds them all up. If there are 3 homeworks, no quizzes, and no labs, then on a default class 
	  //it would be: (3*10) + (0*100) + (0*50) + (participation value)
	  divisor = (numHWs*R.getHWValue())+(numQuizzes*R.getQuizValue())+(numLabs*R.getLabValue())+R.getParticipationValue();
	  //In the above example, this would be ((30-totalPointsLost)/30)*100 for your actual grade
	  
	  //Gets the average before extra credit, prints whether or not that value is valid
	  av = ((divisor-total)/divisor)*100;
	  isGradeValid(av);
	  //			System.out.println(divisor);
	  //			System.out.println(total);
	  //			System.out.println("Av before EC = " + av);
	  
	  //Gets the average with extra credit in mind. Returns that value.
	  av = ((divisor+(gradebook.get(ID)).get(2)-total)/divisor)*100;
	  //System.out.println(divisor);
	  //			System.out.println((gradebook.get(ID)).get(2));
	  //			System.out.println(total);
	  //			System.out.println("Av after EC = " + av);
	  
	  return av;
      }
      
      //gets type of assignment given an index
      public int getAssignmentType(int assignment){
	  int type = 0;
	  if(values.get(assignment) == 1)
	      type=1;
	  else if (values.get(assignment)==2)
	      type =2;
	  else if(values.get(assignment) == 3)
	      type = 3;
	  else if(values.get(assignment) == 4)
	      type = 4;
	  
	  return type;
      }
      
      //adds new student. Initializes their grades to 0.
      public void addStudent(Student kid){
	  //adds the student object to the Students vector
	  students.addElement(kid);
	  //creates the vector to add to gradebook. Sets the first value fo the student's ID
	  //All other values are intiialized to 0 
	  Vector<Integer> grades = new Vector<Integer>();
	  grades.addElement(kid.getID());
	  for (int i = 1;i<gradebook.get(0).size();i++)
	      grades.addElement(0);
	  
	  if(gradebook.size() == 1 && gradebook.get(0).get(0) == -1)
	      gradebook.get(0).set(0,grades.get(0));
	  else
	      gradebook.addElement(grades);
	  
      }
      
      //adds a new assignment based on the assignment type. Initializes all enw values to 0.
      public void addAssignment(int type){
	  if (type < -1 && type >4)
	      System.out.println("Not a valid assignment type.");
	  else {
	      values.addElement(type);
	      if(type == 0)
		  numHWs++;
	      else if (type == 1)
		  numQuizzes++;
	      else if(type == 2)
		  numLabs++;
	      for(int i =0;i<gradebook.size();i++)
		  (gradebook.get(i)).addElement(0);
	  }
      }
      
      //changes a specific grade. when you call this from text, remember that the first position is actually the student ID. DERP.
      public void changeGrade(int newGrade, int ID, int assignment){
	  (gradebook.get(ID)).set(assignment,newGrade);
      }

      //Prints out the Student's highest HW grade
      public void printHighestHWGradeForStudent(int ID){
	  Vector<Integer> HWIndexes = new Vector<Integer>();
	  for(int i = 0;i<values.size();i++)
	      if(values.get(i) == 0)
		  HWIndexes.addElement(i);
	  
	  int high = (gradebook.get(ID)).get(HWIndexes.get(0));
	  
	  for(int i=1;i<(gradebook.get(ID)).size();i++)
	      if(i < HWIndexes.size())
		  if(i == HWIndexes.get(i))
		      if((gradebook.get(ID)).get(i) > high)
			  high = (gradebook.get(ID)).get(i);
	  
	  System.out.println("The highest HW grade for Student " + ID + " is " + high);
      }		
      
      //Prints out the Student's lowest grade
      public void printLowestHWGradeForStudent(int ID){
	  Vector<Integer> HWIndexes = new Vector<Integer>();
	  for(int i = 0;i<values.size();i++)
	      if(values.get(i) == 0)
		  HWIndexes.addElement(i);
	  
	  int low = (gradebook.get(ID)).get(HWIndexes.get(0));
	  
	  for(int i=1;i<(gradebook.get(ID)).size();i++)
	      if(i < HWIndexes.size())
		  if(i == HWIndexes.get(i))
		      if((gradebook.get(ID)).get(i) < low)
			  low = (gradebook.get(ID)).get(i);
	  
	  System.out.println("The lowest HW grade for Student #" + ID + " is " + low);
      }
      
      
      //Prints out the Student's highest HW grade
      public void printHighestQuizGradeForStudent(int ID){
	  Vector<Integer> quizIndexes = new Vector<Integer>();
	  for(int i = 0;i<values.size();i++)
	      if(values.get(i) == 1)
		  quizIndexes.addElement(i);
	  
	  int high = (gradebook.get(ID)).get(quizIndexes.get(0));
	  
	  for(int i=1;i<(gradebook.get(ID)).size();i++)
	      if(i < quizIndexes.size())
		  if(i == quizIndexes.get(i))
		      if((gradebook.get(ID)).get(i) > high)
			  high = (gradebook.get(ID)).get(i);
	  
	  System.out.println("The highest quiz grade for Student #" + ID + " is " + high);
      }		
      
      //Prints out the Student's lowest grade
      public void printLowestQuizGradeForStudent(int ID){
	  Vector<Integer> quizIndexes = new Vector<Integer>();
	  for(int i = 0;i<values.size();i++)
	      if(values.get(i) == 1)
		  quizIndexes.addElement(i);
	  
	  int low = (gradebook.get(ID)).get(quizIndexes.get(0));
	  
	  for(int i=1;i<(gradebook.get(ID)).size();i++)
	      if(i < quizIndexes.size())
		  if(i == quizIndexes.get(i))
		      if((gradebook.get(ID)).get(i) < low)
			  low = (gradebook.get(ID)).get(i);
	  
	  System.out.println("The lowest quiz grade for Student #" + ID + " is " + low);
      }
      
      //Prints out the Student's highest HW grade
      public void printHighestLabGradeForStudent(int ID){
	  Vector<Integer> HWIndexes = new Vector<Integer>();
	  for(int i = 0;i<values.size();i++)
	      if(values.get(i) == 2)
		  HWIndexes.addElement(i);
			
	  int high = (gradebook.get(ID)).get(HWIndexes.get(0));
	  
	  for(int i=1;i<(gradebook.get(ID)).size();i++)
	      if(i < HWIndexes.size())
					if(i == HWIndexes.get(i))
					    if((gradebook.get(ID)).get(i) > high)
						high = (gradebook.get(ID)).get(i);
	  
	  System.out.println("The highest lab grade for Student #" + ID + " is " + high);
      }      
     
      //Prints out the Student's lowest grade
      public void printLowestLabGradeForStudent(int ID){
	  Vector<Integer> HWIndexes = new Vector<Integer>();
	  for(int i = 0;i<values.size();i++)
	      if(values.get(i) == 2)
		  HWIndexes.addElement(i);
	  
	  int low = (gradebook.get(ID)).get(HWIndexes.get(0));
	  
	  for(int i=1;i<(gradebook.get(ID)).size();i++)
	      if(i < HWIndexes.size())
		  if(i == HWIndexes.get(i))
		      if((gradebook.get(ID)).get(i) < low)
			  low = (gradebook.get(ID)).get(i);
	  
	  System.out.println("The lowest lab grade for Student #"); 
	  
      }
      //Locks the class if it is unlocked
		//Unlocks the class if it is locked
		public void lockOrUnlock(){
			if(this.locked == false)
				locked = true;
			else
				locked = false;
		}
      //Checks to see if the grade is valid
      public void isGradeValid(double av){
/*	  if (av > 100 || av < 0)
	      System.out.println("The average is invalid");
	  else
	      System.out.println("The average is valid!");
*/      }

	/*
	  Purpose: return overall average for entire class
	  @precondition: a gradebook has been filled with values
	  @postcondition: a grade is returned representing overall class average
	 */
	public double getClassAverage(){
	    double average = 0;
	    double grade = 0;
	    for(int i =0;i<students.size();i++){ //runs through each student and adds their average to toal number of averages
		grade = getWeightedAverageForStudent(i);
		average +=grade;
	    }
	    return average/students.size(); //returns class mean 
	}

	/*
	  Purpose: return overall standard deviation for the class
	  @precondition: a gradebook has been filled with values for a class
	  @postcondition: a value is returned representing overall class standard deviation 
	 */
	public double getClassStdDev(){
	    double total = 0;
	    double av = 0;
	    double mean = getClassAverage(); //overall class average 
	    for(int i=0;i<students.size();i++)
		av+=((getWeightedAverageForStudent(i)-mean)*(getWeightedAverageForStudent(i)-mean));

	    total = av/students.size();
	    total = Math.sqrt(total);
	    return total; //returns class standard deviation 
	}
	/*
	  Purpose: return highest average among the students 
	 */
	public double getClassMax(){
	    double high = 0;
	    for(int i=0;i<students.size();i++) //runs through each student 
		if(getWeightedAverageForStudent(i)>high)
		    high = getWeightedAverageForStudent(i); //if a new highest grade is found, it is declared as the new highest grade 
	    return high;
	}

	/*
	  Purpose: return the most frequent average among students
	  @precondition: students have had grades entered for assignments
	  @postcondition: the most frequently earned average is returned 
	 */
	public double getMode(){
	    double averages[]=new double[students.size()];
	    for(int i=0;i<students.size();i++) //fills an array with all student averages
		averages[i]=getWeightedAverageForStudent(i);
	    
	    double mode=0;
	    double maxCount=0;
	    for(int i=0;i<averages.length;i++){
		int count = 0;
		for(int j=0;j<averages.length;j++)
		    if(averages[j]==averages[i])
			count++; //counts the number of appearances of each value 
		if(count>maxCount){ //if a new value is found a greater number of times than another, it is the new maxCount
		    maxCount = count;
		    mode = averages[i];
		}
	    }
	    return mode;
	}

	/*
	  Purpose: return the middle value of the averages
	  @precondition: values have been entered for each students' assignments
	  @postcondition: a middle value has been returned 
	 */
	public double getMedian(){
	    double averages[]=new double[students.size()];
	    for(int i=0;i<students.size();i++) //fills an array with all student averages
		averages[i]=getWeightedAverageForStudent(i);
	    
	    Arrays.sort(averages);//sorts array

	    int middle = averages.length/2;
	    if(averages.length%2==1)
		return averages[middle];
	    else
		return (averages[middle-1]+averages[middle])/2.0;
	    
	}

	/*
	  Purpose: return lowest average among the students 
	 */
	public double getClassMin(){
	    double low = 100;
	    for(int i =0;i<students.size();i++) //runs through each student 
		if(getWeightedAverageForStudent(i)<low)
		    low =getWeightedAverageForStudent(i); //if a new lowest grade is found, it is declared as the new lowest grade 
	    return low;
	}

	/*
	  Purpose: check if entered values by user are within a range of 0 to the highest value for that type of assignment 
	 */
	public Boolean validTable(JTable grades){
	    int value =0;
	    for(int i=3;i<grades.getColumnCount();i++){
		if(values.get(i-3)==0)
		    value = R.getHWValue();
		else if(values.get(i-3)==1)
		    value = R.getQuizValue();
		else if(values.get(i-3)==2)
		    value = R.getLabValue();
		else if(values.get(i-3)==3)
		    value = R.getParticipationValue();
		else if(values.get(i-3)==4)
		    value = R.getExtraCreditValue();
		for(int k = 0;k<grades.getRowCount();k++){
		    int test = Integer.parseInt(grades.getModel().getValueAt(k,i).toString()); //grabs integer value from the table 
		    if(test>value ||test<0) //checks whether the value is within the range and returns false if not 
			return false;
		}
	    }
	    return true;  
	}
	
    }
    
    public static void main(String[] args) {
	/*
	ClassList semester = new ClassList();
	semester.loadClassFile();
	semester.loadLast();
	
	Class currClass = semester.lastEdited;
	semester.loadTable(currClass);
	Object[][]data = semester.currClassData;
	String columns[] = semester.columnNames;
	
	JTable table = new JTable(data,columns);
        LogIn GUI = new LogIn(table,semester);
	*/
	
	LogIn GUI = new LogIn();
    }   
}
