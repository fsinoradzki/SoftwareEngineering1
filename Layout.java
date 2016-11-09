import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;

public class Layout extends JFrame {
    public static class LogIn extends JFrame {
        public LogIn(JTable table) {
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
            //actionLogin(loginButton, password, table);    
            setSize(300, 200);
            setLocation(500, 280);
            
            loginPanel.getRootPane().setDefaultButton(loginButton);
            //loginPanel.setLayout(null);
            
            loginButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    String passwordEntered = password.getText();
                    if(passwordEntered.equals("12345")) {
                        Layout layout = new Layout(table);
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
                }
            });
        }
    }
    
    public class AddAssignment extends JFrame {
        public AddAssignment() { //this will need to call variables needed for assignment
            super("New Assignment");
            JPanel addAssignment = new JPanel();
            addAssignment.setLayout(new BorderLayout());
            
            //all the elements 
            JButton save = new JButton("Save");
            JLabel label1 = new JLabel("Assignment Type:", SwingConstants.CENTER);
            String[] types = {"Homework", "Test", "Midterm", "Final", "Project"};
            JComboBox<String> assignmentType = new JComboBox<String>(types);
            
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(0, 2, 10, 10));
            JPanel savePanel = new JPanel();
            
            inputPanel.add(label1);
            inputPanel.add(assignmentType);
            
            savePanel.add(save, SwingConstants.CENTER);
            
            addAssignment.add(inputPanel, BorderLayout.CENTER);
            addAssignment.add(savePanel, BorderLayout.SOUTH);
          
            save.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //insert code to add the new assignment here
                    //not sure how to get the value currently selected in the combo box
                    //also, let me know if the combo box won't work, I just added dummy assignment types to it for now
                    //but I was hoping whatever is in the rubric for the class can be added to it? 
                    
                    dispose(); //this just shuts the window once everything is done
                }
            });
            
            getContentPane().add(addAssignment);
        }
        
    }
    
    public class AddStudent extends JFrame {
        public AddStudent() { //this will need to call variables needed for student
            super("New Student");
            JPanel addStudent = new JPanel();
            addStudent.setLayout(new BorderLayout());
            
            //all the elements
            JButton save = new JButton("Save");
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
            
            addStudent.add(inputPanel, BorderLayout.CENTER);
            addStudent.add(savePanel, BorderLayout.SOUTH);
            
            save.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) {
                    //insert code to save student here
                    //___.getText can be used to access text in JTextFields
                    //it will return it as a string though, I am assuming you can convert string to int?
                    //and I can set up an error message dialog for if the input isn't valid
                    
                    dispose();
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
    
    public Layout(JTable grades) { //pretty much I think layout will have to call like every variable unless 
                                   //there's another way to do things that I'm just missing? 
        super("Your Gradebook");

        //panels for the split frame
        JPanel navPanel = new JPanel();
        
        //creating the tabbedpane
        JTabbedPane tabbedPane = new JTabbedPane();  
        
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
        gradesPanel.setLayout(new BorderLayout());       
        
        //panel for buttons under grades
        JPanel gradesButtons = new JPanel();
        
        //save grades button
        JButton saveGradesButton = new JButton("Save Grades");
        saveGradesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //code to save grades here
            }
        });
        
        //add Assignment Button
        JButton addAssignmentButton = new JButton ("Add Assignment");
        addAssignmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddAssignment assignmentPopUp = new AddAssignment();
                assignmentPopUp.setVisible(true);
                assignmentPopUp.setLocation(500, 500);
                assignmentPopUp.setSize(350, 110);
            }
        });
        
        //add Student Button
        JButton addStudentButton = new JButton ("Add Student");
        addStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddStudent studentPopUp = new AddStudent();
                studentPopUp.setVisible(true);
                studentPopUp.setLocation(500, 500);
                studentPopUp.setSize(350, 150);
            }
        });
        
        //final Grades Button
        JButton finalGradesButton = new JButton ("Compute Final Grades");
        finalGradesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FinalGrades finalGradesPopUp = new FinalGrades();
                finalGradesPopUp.setVisible(true);
                finalGradesPopUp.setSize(250,100);
                finalGradesPopUp.setLocation(500,500);
            }
        });
        gradesButtons.add(saveGradesButton);
        gradesButtons.add(addAssignmentButton);
        gradesButtons.add(addStudentButton);
        gradesButtons.add(finalGradesButton);
        gradesButtons.setBackground(Color.WHITE);
        //scroll pane for grades table
        JScrollPane gradeScrollPane = new JScrollPane(grades);
        String title = "Class Name";
        gradeScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title, TitledBorder.LEFT, TitledBorder.TOP));
        gradeScrollPane.setBackground(Color.WHITE);
        gradesPanel.add(gradeScrollPane, BorderLayout.CENTER);
        gradesPanel.add(gradesButtons, BorderLayout.SOUTH);
        tabbedPane.add(gradesPanel, "Grades");

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
        //   setVisible(true);
    }
    
    
     public static class ClassList{
	private Vector<Class> list; 
	
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

    //Default constructor
    public Rubric ()
    {
      HWValue = 10;
      quizValue = 100;
      labValue = 50;
    }

    //Constructor with values
    public Rubric (int HW, int quiz, int lab)
    {
      HWValue = HW;
      quizValue = quiz;
      labValue = lab;
    }

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

    //Call this to change the values for each type of assignment
    public void editRubricValues (int HW, int quiz, int lab)
    {
      HWValue = HW;
      quizValue = quiz;
      labValue = lab;
    }
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
    public double getWeightedAverageForStudent (int ID)
    {
      int total = 0;
		double divisor = 0;
      double av=0;

		//gets the total points lost 
      for (int i = 1; i < (gradebook.get (ID)).size (); i++)
			total += (gradebook.get (ID)).get (i);
		//returns number of homeworks, quizzes, and labs
		//multiplies each by the respective rubric value and adds them all up
		//if there are 3 homeworks, no quizzes, no labs then on a default class
		// it would be: (3*10)+(0*100)+(0*50)
		divisor = (numHWs*R.getHWValue())+(numQuizzes*R.getQuizValue())+(numLabs*R.getLabValue());
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
	  if (type < 0 && type >2)
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
	
	File file = new File("./Classes/grades.txt");
	//Vector < Vector < Integer >> SandG = new Vector < Vector < Integer >> ();
	/*
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
	*/
	Student f = new Student ("Frank", 123);
	Student b = new Student ("Ben", 456);
	Student p = new Student ("Phoebe", 789);
	Student x = new Student ("Dummy", 000);
	
	Vector<Student>pupils = new Vector<Student>();
	pupils.addElement(f);
	pupils.addElement(b);
	pupils.addElement(p);
	pupils.addElement(x);

	int HWs = 3;
	int Quizzes = 0;
	int Labs = 0;
	String name = "SoftEng";
	int number = 2;

	Vector<Integer>indexes = new Vector<Integer>();
	indexes.addElement(-1);
	indexes.addElement(0);
	indexes.addElement(0);
	indexes.addElement(0);
	
	String testName = "Math";
	int testNumber = 1100;
	Class c2 = new Class(testName, testNumber);
	c2.createFile();
	
	Vector <String> columnNames = new Vector<String>();
	columnNames.addElement("STUDENT ID");
	columnNames.addElement("HW1");
	columnNames.addElement("HW2");
	columnNames.addElement("HW3");
	columnNames.addElement("HW4");
	JTable table = new JTable(c2.gradebook, columnNames); 
 
	
        LogIn GUI = new LogIn(table);
    }   
}

