import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;

public class Layout extends JFrame {
    public static class LogIn extends JFrame {
        public LogIn(JTable table, ClassList semester) {
            super("Authentication");
                        
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
                String passwordEntered = password.getText();
                if(passwordEntered.equals(userPassword)) {
                    Layout layout = new Layout(table,semester);
                    layout.setVisible(true);
                    layout.setSize(1500, 1000);
                    layout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Wrong Passwrd");
                    password.setText("");
                    password.requestFocus();
                }
            });
        }
    }
    
    public class AddClass extends JFrame {
        public AddClass(ClassList semester, Class c1) {
            super("Add Class");
	    JPanel addClass = new JPanel();
            addClass.setLayout(new BorderLayout());
            
            //all the elements
            JButton save = new JButton("Continue");
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
		    String name = className.getText();
		    int number =Integer.valueOf(classNum.getText());
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
		    AddStudent studentPopUp = new AddStudent(c3);
		    studentPopUp.setVisible(true);
		    studentPopUp.setLocation(500, 500);
		    studentPopUp.setSize(350, 150);
		    
                dispose(); //this just shuts the window once everything is done
            });
            
            getContentPane().add(addClass);
        }    
    }
    
    public class AddAssignment extends JFrame {
        public AddAssignment(Class c1) { //this will need to call variables needed for assignment
            super("New Assignment");
            JPanel addAssignment = new JPanel();
            addAssignment.setLayout(new BorderLayout());
            
            //all the elements 
            JButton save = new JButton("Save");
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
		    c1.addAssignment(type);
		    c1.createAssignmentsFile();
                
               	    dispose(); //this just shuts the window once everything is done
		    
           });
            
            getContentPane().add(addAssignment);
        }
        
    }
    
    public class AddStudent extends JFrame {
        public AddStudent(Class c1) { //this will need to call variables needed for student
            super("New Student");
            JPanel addStudent = new JPanel();
            addStudent.setLayout(new BorderLayout());
            
            //all the elements
            JButton save = new JButton("Save");
            JButton savePlus = new JButton("Save & Add Another");
            JLabel label1 = new JLabel("Student Name:");
            JTextField studentName = new JTextField("", 25);
            JLabel label2 = new JLabel("Student ID:");
            JTextField studentID = new JTextField("", 10);

            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(0, 2, 10, 10));
            JPanel savePanel = new JPanel();
            
            inputPanel.add(label1);
            inputPanel.add(studentName);
            inputPanel.add(label2);
            inputPanel.add(studentID);
            
            savePanel.add(save, SwingConstants.CENTER);
            savePanel.add(savePlus);
            
            addStudent.add(inputPanel, BorderLayout.CENTER);
            addStudent.add(savePanel, BorderLayout.SOUTH);

	    
            save.addActionListener((ActionEvent e) -> {
		    if (studentID.getText().matches("[0-9]+") && studentID.getText().length()>2) {
		    	Student test = new Student("default",000);
		    	test.name = studentName.getText();
		   	test.student_id = Integer.valueOf(studentID.getText());
		    	c1.addStudent(test);
		    	c1.createStudentsFile();
		    	c1.createFile();
			
                    	dispose(); 
		    }
		    else {
			JOptionPane.showMessageDialog(null, "Invalid Student ID");
			studentID.requestFocus();
		    }
            });
            
            savePlus.addActionListener((ActionEvent e) -> {
                    //insert code to save student here
                
		    if(studentID.getText().matches("[0-9]+") && studentID.getText().length()>2) {
		    	Student test = new Student("default",000);
		    	test.name = studentName.getText();
		    	test.student_id = Integer.valueOf(studentID.getText());
		    	c1.addStudent(test);
		    	c1.createStudentsFile();
		    	c1.createFile();
		    	AddStudent studentPopUp = new AddStudent(c1);
		  	studentPopUp.setVisible(true);
		    	studentPopUp.setLocation(500, 500);
		    	studentPopUp.setSize(350, 150);

		    	dispose();
		    }
		    else {
			JOptionPane.showMessageDialog(null, "Invalid Student ID");
			studentID.requestFocus();
		    }
            });            
                     
            getContentPane().add(addStudent); 
        }
    }
    
    public class FinalGrades extends JFrame {
        public FinalGrades() { //this will need to call any variables needed for final grade computation
            JPanel finalGrades = new JPanel();
            JButton ok = new JButton("Okay");
            JLabel label1 = new JLabel("Student Grades...");
            finalGrades.add(label1);
            finalGrades.add(ok);
            
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
            JButton save = new JButton("Save");
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
        
        //creating the tabbedpane
        JTabbedPane tabbedPane = new JTabbedPane();  
        
        //creating the split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navPanel, tabbedPane);
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerLocation(300);
        splitPane.setBackground(Color.WHITE);
        
        //navigation panel
        JLabel navTitle = new JLabel("NAVIGATION", SwingConstants.CENTER);
        navTitle.setFont(new Font("Arial", Font.BOLD, 18));
        
        JPanel navButtonPanel = new JPanel();
        JButton addNewClassButton = new JButton("Add New Class");
        addNewClassButton.addActionListener((ActionEvent e) -> {
		AddClass addClassPopUp = new AddClass(semester, c1);
            addClassPopUp.setVisible(true);
            addClassPopUp.setLocation(500, 500);
            addClassPopUp.setSize(350, 150);
        });
        navButtonPanel.add(addNewClassButton);
        
        navPanel.add(navTitle, BorderLayout.NORTH);
        navPanel.add(navButtonPanel, BorderLayout.CENTER);
        navPanel.setBackground(Color.WHITE);
	for(int i =0;i<semester.list.size();i++){
	    int number =semester.list.get(i).classNum;
	    String name =semester.list.get(i).className;
	    String key = name+number;
	    JButton ClassButton = new JButton(key);
	    navButtonPanel.add(ClassButton);
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
		    //whatever code is needed to make the new class show its data instead of the old one goes here 
		    //In theory just recreating layout with the new class and disposing the old layout should work	
		});
	}
        
        //grades panel
        JPanel gradesPanel = new JPanel();
        gradesPanel.setLayout(new BorderLayout());
        
        //panel for buttons under grades
        JPanel gradesButtons = new JPanel();
        
        //save grades button
        JButton saveGradesButton = new JButton("Save Grades");
        saveGradesButton.addActionListener((ActionEvent e) -> {
		c1.createFile(grades);
		JOptionPane.showMessageDialog(null, "Grades Saved");
        });
        
        //add Assignment Button
        JButton addAssignmentButton = new JButton ("Add Assignment");
        addAssignmentButton.addActionListener((ActionEvent e) -> {
            AddAssignment assignmentPopUp = new AddAssignment(c1);
            assignmentPopUp.setVisible(true);
            assignmentPopUp.setLocation(500, 500);
            assignmentPopUp.setSize(350, 110);
	    
	    //dispose();
        });
        
        //add Student Button
        JButton addStudentButton = new JButton ("Add Student");
        addStudentButton.addActionListener((ActionEvent e) -> {
            AddStudent studentPopUp = new AddStudent(c1);
            studentPopUp.setVisible(true);
            studentPopUp.setLocation(500, 500);
            studentPopUp.setSize(350, 150);
	    //dispose();
	    //grades.updateUI();
        });
        
        //final Grades Button
        JButton finalGradesButton = new JButton ("Compute Final Grades");
        finalGradesButton.addActionListener((ActionEvent e) -> {
            FinalGrades finalGradesPopUp = new FinalGrades();
            finalGradesPopUp.setVisible(true);
            finalGradesPopUp.setSize(250,100);
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
        grades.getColumnModel().getColumn(0).setPreferredWidth(200);
        grades.getColumnModel().getColumn(1).setPreferredWidth(100);
        gradeScrollPane.setBackground(Color.WHITE);
        gradesPanel.add(gradeScrollPane, BorderLayout.CENTER);
        gradesPanel.add(gradesButtons, BorderLayout.SOUTH);
        tabbedPane.add(gradesPanel, "Grades");

        //rubric panel
        JPanel rubricPanel = new JPanel(new BorderLayout());
        tabbedPane.add(new JScrollPane(rubricPanel), "Rubric");
        
        //content panel, displays rubric 
        JPanel rubricContent = new JPanel(new GridLayout(0, 1, 10, 10));
        rubricContent.setBackground(Color.WHITE);
        rubricContent.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title + " Rubric", TitledBorder.LEFT, TitledBorder.TOP));
	
	//labels for rubric panel 
	JLabel HWLabel = new JLabel("Homework Value: " + c1.R.getHWValue());
	JLabel QuizLabel = new JLabel("Quiz Layout: " + c1.R.getQuizValue());    
	JLabel LabLabel = new JLabel ("Lab Value: " + c1.R.getLabValue());
	JLabel ParticLabel = new JLabel ("Participation Value: " + c1.R.getParticipationValue());
	JLabel ECLabel = new JLabel ("Extra Credit Value: " + c1.R.getExtraCreditValue());    
	rubricContent.add(HWLabel);
	rubricContent.add(QuizLabel);
	rubricContent.add(LabLabel);
	rubricContent.add(ParticLabel);
	rubricContent.add(ECLabel);
 
       //button panel for rubric
        JPanel rubricButtons = new JPanel();
        rubricButtons.setBackground(Color.WHITE);
        JButton editRubricButton = new JButton("Edit Rubric");
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
        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(Color.GREEN);
        tabbedPane.add(new JScrollPane(statsPanel), "Statistics");
        
        getContentPane().add(splitPane, BorderLayout.CENTER);
        setSize(1500, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    public static class ClassList{
	private Vector<Class> list; 
	Object [][]currClassData;
	String[] columnNames;
	Class lastEdited;
	//Class currClass;
	
	public void loadTable(Class currClass){
	    System.out.println(currClass.classNum);
	    String columns[] = new String[currClass.values.size()+2];
	    columns[0]="Student Name";
	    columns[1]="Student ID";
	    String title= " ";
	    int hwNum =0;
	    int quizNum=0;
	    int labNum=0;
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
	    columns[i+2]=title;
	    }
	    
	    Object[][]data = new Object[currClass.students.size()][currClass.values.size()+2];
	    for(int i =0;i<currClass.students.size();i++){
		data[i][0]=currClass.students.get(i).name;
		for(int k =1;k<=currClass.gradebook.get(i).size();k++)
		    data[i][k] = currClass.gradebook.get(i).get(k-1);
	    }
	    columnNames = columns; 
	    currClassData = data;
	}

	public void saveLast(){
	    try{
		File file = new File("./Classes/lastEdit.txt");
		if(!file.exists())
		    file.createNewFile();
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(String.valueOf(this.lastEdited.classNum));
		bw.write(" ");
		bw.write(this.lastEdited.className);
		bw.write("\n");
		bw.close();
	    }catch(IOException e){}
	}
	public void loadLast(){
	    try{
		File file = new File("./Classes/lastEdit.txt");
    		BufferedReader br = new BufferedReader (new FileReader (file));
		String line;
		while ((line = br.readLine ()) != null)
		    {
			StringTokenizer st = new StringTokenizer (line); 
			while (st.hasMoreTokens ())
			    {
				int number = Integer.valueOf(st.nextToken());
				String name = st.nextToken();
				Class test = new Class(name,number);
				this.lastEdited = test;
				
			    }
		    }	      
	    }catch (IOException e){}
    }
	
	public void addClasses(Vector<Class> item){
	    this.list = item;
	}
	
	public void loadClassFile(){
	    try{
		File file = new File("./Classes/ClassList.txt");
		Vector<Class>semester = new Vector<Class>();
		BufferedReader br = new BufferedReader (new FileReader (file));
		String line;
		while ((line = br.readLine ()) != null)
		    {
			StringTokenizer st = new StringTokenizer (line); 
			while (st.hasMoreTokens ())
			    {
				int number = Integer.valueOf(st.nextToken());
				String name = st.nextToken();
				if (!st.hasMoreTokens ()){
				    Class sample = new Class(name,number);
				    semester.addElement (sample);
				}
			    }
		    }	      
		this.list = semester;
		for (int i =0;i<list.size();i++){
		    System.out.println(list.get(i).className);
		    System.out.println(list.get(i).classNum);
		}
	    }catch(IOException e){}
	}
	public void createClassFile(){
	    try{
		File file = new File("./Classes/ClassList.txt");
		if(!file.exists())
		    file.createNewFile();
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		for(int i =0;i<list.size();i++){
		    bw.write(String.valueOf(list.get(i).classNum));
		    bw.write(" ");
		    bw.write(list.get(i).className);
		    bw.write("\n");
		}
		bw.close();
	    }catch(IOException e){}
	}
	
    }
    
    public static class Student{
	private String name;	//Student name
	private int student_id;	//Student ID

    //Constructor for Student
    public Student (String Sname, int ID)
    {
      name = Sname;
      student_id = ID;
    }


    //Returns the student's name
    public String getName ()
    {
      return name;
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
	  students = new Vector<Student>();
	  values = new Vector<Integer>();
	  this.addAssignment(-1);
	  this.addAssignment(3);
	  this.addAssignment(4);
	  numHWs = 0;
	  numQuizzes = 0;
	  numLabs = 0;
	  R = new Rubric();
	  locked = false;
      }
      
    //Simple Constructor
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
      if(!mydir.exists())
	  mydir.mkdir();
    }

      public Class(String name,int number)
		  {
		      System.out.println("Entered CLASS");
		      className = name;
		      classNum = number;
		      this.returnStudentsFile();
		      this.returnAssignmentsFile();
		      this.returnRubricFile();
		      numHWs =0;
		      numLabs = 0;
		      numQuizzes =0;
		      for(int i=0;i<this.values.size()-1;i++){
			  if(this.values.get(i)==0)
			      numHWs++;
			  if(this.values.get(i)==1)
			      numQuizzes++;
			  if(this.values.get(i)==2)
			      numLabs++;
		      }
       		      locked = false;
		      try
			  {
			      String key = this.className+this.classNum;
			      File mydir = new File("./Classes/"+key);
			      File file = new File("./Classes/"+key+"/"+key+"grades.txt");
			      Vector < Vector < Integer >> matrix = new Vector < Vector < Integer >> ();  //vector of vectors
			      BufferedReader br = new BufferedReader (new FileReader (file));
			      String line;
			      while ((line = br.readLine ()) != null)
				  {
				      StringTokenizer st = new StringTokenizer (line);
				      int num = 0;
				      Vector < Integer > test = new Vector < Integer > ();   //vector used for input
				      test.clear ();
				      while (st.hasMoreTokens ())
					  {
					      int value1 = Integer.parseInt (st.nextToken ());
					      test.addElement (value1);
					      if (!st.hasMoreTokens ())
						  matrix.addElement (test);   //inserts entire test vector into the main vector
					  }
				  }
			      gradebook = matrix;
			      
			  }
		      catch (IOException e)
			  {
			  }
		      
    }

	  public void createStudentsFile(){
		  try{
		  String key = this.className+this.classNum;
		  File file = new File("./Classes/"+key+"/"+key+"Students.txt");
		  if(!file.exists())
			  file.createNewFile();
		  FileWriter fw = new FileWriter(file);
		  BufferedWriter bw = new BufferedWriter(fw);
		  for(int i =0;i<students.size();i++){
			  bw.write(String.valueOf(students.get(i).getID()));
			  bw.write(" ");
			  bw.write(students.get(i).getName());
			  bw.write("\n");
		  }
		  bw.close();
		  }catch(IOException e){}
	  }
      
      public void returnStudentsFile(){
	  try{
	      String key = this.className+this.classNum;
	      File file = new File("./Classes/"+key+"/"+key+"Students.txt");
	      Vector<Student>pupils = new Vector<Student>();
	      BufferedReader br = new BufferedReader (new FileReader (file));
              String line;
              while ((line = br.readLine ()) != null)
		  {
		      StringTokenizer st = new StringTokenizer (line);
		      Student test = new Student("JohnDoe", 999); 
		      while (st.hasMoreTokens ())
			  {
			      test.student_id = Integer.valueOf(st.nextToken());
			      test.name = st.nextToken();
			      if (!st.hasMoreTokens ())
				  pupils.addElement (test); 
			  }
		  }	      
	      this.students = pupils;
	      for (int i =0;i<students.size();i++){
		  System.out.println(students.get(i).name);
		  System.out.println(students.get(i).student_id);
	      }
	  }catch(IOException e){}
      }

      public void createRubricFile(){
		  try{
		  String key = this.className+this.classNum;
		  File file = new File("./Classes/"+key+"/"+key+"Rubric.txt");
		  if(!file.exists())
			  file.createNewFile();
		  FileWriter fw = new FileWriter(file);
		  BufferedWriter bw = new BufferedWriter(fw);
		  bw.write(String.valueOf(R.HWValue));
		  bw.write("\n");
		  bw.write(String.valueOf(R.quizValue));
		  bw.write("\n");
		  bw.write(String.valueOf(R.labValue));
		  bw.write("\n");
		  bw.write(String.valueOf(R.participationValue));
		  bw.write("\n");
		  bw.write(String.valueOf(R.extraCreditValue));
		  bw.close();
		  }catch(IOException e){}
	  }

       public void returnRubricFile(){
	  try{
	      String key = this.className+this.classNum;
	      File file = new File("./Classes/"+key+"/"+key+"Rubric.txt");
	      BufferedReader br = new BufferedReader (new FileReader (file));
              String line;
	      Rubric test = new Rubric();
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
				test.HWValue = Integer.valueOf(st.nextToken());
			    if(i==1)
			      test.quizValue = Integer.valueOf(st.nextToken());
			    if(i==2)
				test.labValue = Integer.valueOf(st.nextToken());
			    if(i==3)
				test.participationValue = Integer.valueOf(st.nextToken());
			    if(i==4)
				test.extraCreditValue = Integer.valueOf(st.nextToken());
			        }
		      
		      i++;
		  }
	      this.R = test;
	      System.out.println(test.HWValue);
	      System.out.println(test.quizValue);
	      System.out.println(test.labValue);
	  }catch(IOException e){}
       }
      /*
		 Purpose: create a standard file to save the gradebook to for each class. This will be done by using the className and classNum to create a standard file name.
		 This standard file name will be className+ClassNum+.txt and an example would be CISC1600.txt where CISC is the class name and 1600 is the class number.
		 Each of these files will be stored within a subdirectory called "Classes".
		 @post condition: at the end of the function, a .txt file will have been written containing the entire gradebook of the class. This file will have a unique standard name so that other classes do not conflict with it.
		*/
	  public void createFile ()
		  {
			  try
			  {
				  String key = this.className + this.classNum;	//used to standardize file format
				  File mydir = new File("./Classes/"+key);
				  if(!mydir.exists())
				      mydir.mkdir();
				  File file = new File ("./Classes/" + key + "/"+key+"grades.txt");	//accesses file of standard format within a subdirectory
				  if (!file.exists ())
					  file.createNewFile ();
				  
				  Vector < Vector < Integer >> matrix =new Vector < Vector < Integer >> ();
				  matrix = this.gradebook;
				  FileWriter fw = new FileWriter (file);
				  BufferedWriter bw = new BufferedWriter (fw);
				  for (int i = 0; i < matrix.size (); i++)
				  {
					  for (int k = 0; k < (matrix.get (i).size ()); k++)
					  {
						  bw.write (String.valueOf ((matrix.get (i)).get (k)));
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

	public void createFile(JTable grades) {
		System.out.println("Saving Grades?");
		try {
			String key = this.className + this.classNum;
			File file = new File ("./Classes/" + key + "/" + key + "grades.txt");
			if (!file.exists())
				file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i=0; i<grades.getRowCount(); i++) {
				for(int j=1; j<grades.getColumnCount(); j++) {
				    if(grades.getModel().getValueAt(i,j)!=null) 
					bw.write(grades.getModel().getValueAt(i, j) + " ");
				    else
					bw.write("0 ");
					System.out.println(grades.getModel().getValueAt(i, j) + " ");
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
	postcondition: the assignment types for a class have been saved to a standard file 
       */
      public void createAssignmentsFile(){
		  try{
		  String key = this.className+this.classNum;
		  File file = new File("./Classes/"+key+"/"+key+"Assignments.txt");
		  if(!file.exists())
			  file.createNewFile();
		  FileWriter fw = new FileWriter(file);
		  BufferedWriter bw = new BufferedWriter(fw);
		  for(int i =0;i<values.size();i++){
		      bw.write(String.valueOf(values.get(i)));
		      bw.write("\n");
		  }
		  bw.close();
		  }catch(IOException e){}
	  }
      
      /*
	Purpose: to return the values of an assignment vector to a gradebook from a standard file 
	postcondition: values have been returned to represent the assignment types for a class
       */
      public void returnAssignmentsFile(){
	  try{
	      String key = this.className+this.classNum;
	      File file = new File("./Classes/"+key+"/"+key+"Assignments.txt");
	      Vector<Integer>indexes = new Vector<Integer>();
	      BufferedReader br = new BufferedReader (new FileReader (file));
              String line;
              while ((line = br.readLine ()) != null)
		  {
		      StringTokenizer st = new StringTokenizer (line); 
		      while (st.hasMoreTokens ())
			  {
			      int value1 = Integer.parseInt (st.nextToken ());
			      if (!st.hasMoreTokens ())
				  indexes.addElement(value1);
			  }
		  }	      
	      this.values = indexes;
	      System.out.println(values);
	  }catch(IOException e){}
      }

      /*
	Purpose: Used within the GUI for Class creation to check whether the class has an existing file in the standard format. 
	@return: if the file exists, the function returns true, otherwise it returns false
      */
      public boolean checkFile(){
			  String key = this.className + this.classNum;
			  File file = new File("./Classes/"+key+".txt");
			  if(file.exists())
				  return true;
			  else
				  return false;
	  }
	  
	  //Prints the whole gradebook
	  public void printGradebook ()
		  {
			  System.out.println (gradebook);
		  }

	  public String printStudentName(int ID){
		  int position = 0;
		  for(int i =0;i<students.size();i++)
			  if(students.get(i).getID()==ID)
				  position = i;
		  return students.get(position).getName();
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
	  
	  //Gets the total points lost. Ignores extra credit, since it's not points lost.
	  for(int i=1;i<(gradebook.get(ID)).size();i++){
	      if(i != 2)
		  total += (gradebook.get(ID)).get(i);
	  }
	  
	  //Adds the actual extra credit points
	  total += R.getExtraCreditValue() - (gradebook.get(ID)).get(2);
	  
	  //Gets the number of homeworks, quizzes, and labs, multiplies each by the respective rubric value
	  //And adds them all up. And also Participation and Extra Credit 
	  //If there are 3 homeworks, no quizzes, and no labs, then on a default class 
	  //it would be: (3*10) + (0*100) + (0*50) + (PV) + (ECV)
	  divisor = (numHWs*R.getHWValue())+(numQuizzes*R.getQuizValue())+(numLabs*R.getLabValue()+R.getParticipationValue());
	  //In the above example, this would be ((30-totalPointsLost)/30)*100 for your actual grade
	  av = ((divisor-total)/divisor)*100;
	  
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
	  gradebook.addElement(grades);
	  
      }
      
      //adds a new assignment based on the assignment type. Initializes all enw values to 0.
      public void addAssignment(int type){
	  if (type < -1 && type >4)
	      System.out.println("Not a valid assignment type.");
	  else {
	      System.out.println(type);
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
      
  }
    
    public static void main(String[] args) {

	String name = "SoftEng";
	int number = 2;
	
	String testName = "Math";
	int testNumber = 1100;
	Class c2 = new Class(testName, testNumber);
	c2.createFile();

	Vector<Class> list = new Vector<Class>();
	ClassList semester = new ClassList();
	Class c1 = new Class(name,number);
	//list.addElement(c1);
	//list.addElement(c2);
	//semester.addClasses(list);
	//semester.createClassFile();
	semester.loadClassFile();
	semester.loadLast();
	Class currClass = semester.lastEdited;
	semester.loadTable(currClass);
	Object[][]data = semester.currClassData;
	String columns[] = semester.columnNames;
	JTable table = new JTable(data,columns);
        LogIn GUI = new LogIn(table,semester);
    }   
}
