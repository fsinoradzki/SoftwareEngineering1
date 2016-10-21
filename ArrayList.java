import java.io.*;
import java.util.*;
import java.lang.*;

public class GradeBook1
{

  public static class Student
  {
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


  public static class Class
  {
    private ArrayList[][] gradebook;	//Matrix that stores the student ID as the first
    //As the first element in every row. Every other
    //cell is a grade

    //Constructor
      public Class (ArrayList[][]SandG)
      {
	  gradebook = SandG;
      }
      
      public Class (File file){
	  try{	  
	      ArrayList [][]matrix = new ArrayList[4][4];
	      BufferedReader br = new BufferedReader(new FileReader(file));
	      String line;
	      int i =0;
	      while((line = br.readLine())!=null){
		  StringTokenizer st = new StringTokenizer(line);
		  int num = 0;
		  while(st.hasMoreTokens()){
		      int value1 = Integer.parseInt(st.nextToken());
		      matrix [i][num].add(value1);
		      num++;
		  }
		  i++;
	      }
	      gradebook = matrix;	      
	  }
	  catch(IOException e){
	  }
      }

      public void createFile(File file){
	  try{
	      if(!file.exists()){
		  file.createNewFile();
	      }
	  }catch(IOException e){
	  }
	  System.out.println(this.gradebook[2][1]);
	  ArrayList [][]matrix = new ArrayList[4][4];
	  matrix=this.gradebook;
	  try{
	      FileWriter fw = new FileWriter(file);
	      BufferedWriter bw = new BufferedWriter(fw);
	      for (int i =0;i<matrix[0].length;i++){
		  for(int k =0;k<4;k++){
		      bw.write(String.valueOf(matrix[i][k]));
		      bw.write(" ");
		  }
		  bw.write("\n");
	      }
	      bw.close();
	      
	  }
	  catch(IOException e){
	  }
      }
      
      

    //Prints out all grades for a specific student
    public void getGradesForStudent (int ID)
    {
      for (int i = 1; i < gradebook[0].length; i++)
	System.out.println ("Grade: " + gradebook[ID][i]);
    }

    //Prints out all grades for a specific assignment
    public void getGradesForClass (int assignment)
    {
      if (assignment != 0)
	{
	  for (int i = 0; i < gradebook[0].length; i++)
	    System.out.println ("Grade: " + gradebook[i][assignment]);
	}
      else
	System.out.println ("That's for student_id");
    }

    //Prints out the highest grade for a specific assignment
    public void printHighestGradeForAssignment (int assignment)
    {
	String s=  String.valueOf(gradebook[1][assignment]);
	int max = Integer.parseInt(s);
	if (assignment != 0)
	    {
		for (int i = 1; i < gradebook[0].length; i++){
		    String comp = String.valueOf(gradebook[i][assignment]);
		    int test = Integer.parseInt(comp);
		    if (test > max)
			max = test;
		}
	    }
	else
	    System.out.println ("That's for student_id");
	
	System.out.println ("The highest grade is: " + max);
    }

    //Prints out the lowest grade for a specific assignment
    public void printLowestGradeForAssignment (int assignment)
    {
	String s=  String.valueOf(gradebook[1][assignment]);
	int low = Integer.parseInt(s);
	    if (assignment != 0)
	{
	    for (int i = 1; i < gradebook[0].length; i++){
		String comp=  String.valueOf(gradebook[i][assignment]);
		int test = Integer.parseInt(comp);
		if (test < low)
		  low = test;
	    }
	}
      else
	System.out.println ("That's for student_id");

      System.out.println ("The lowest grade is: " + low);
    }

    //Prints out the Student's highest grade
    public void printHighestGradeForStudent (int ID)
    {
	String s=  String.valueOf(gradebook[ID][1]);
	int high = Integer.parseInt(s);
	    for (int i = 1; i < gradebook[0].length; i++){
		String comp=  String.valueOf(gradebook[ID][i]);
		int test = Integer.parseInt(comp);
		if (test > high)
		    high = test;
	    }

      System.out.println ("The highest grade for Student " + ID + " is " +
			  high);
    }

    //Prints out the Student's lowest grade
    public void printLowestGradeForStudent (int ID)
    {
	String s=  String.valueOf(gradebook[ID][1]);
	int low = Integer.parseInt(s);
	for (int i = 1; i < gradebook[0].length; i++){
	    String comp=  String.valueOf(gradebook[ID][i]);
	    int test = Integer.parseInt(comp);
	    if (test < low)
	      low = test;
	}

      System.out.println ("The lowest grade for Student " + ID + " is " +
			  low);
    }

    //Returns the value of the average for a given assignment
    public double getAverageForAssignment (int assignment)
    {
	int total = 0;
      if (assignment != 0)
	  for (int i = 0; i < gradebook[0].length; i++){
	      String s = String.valueOf(gradebook[i][assignment]);
	      int test = Integer.parseInt(s);
	      total += test;
	  }
      else
	System.out.println ("That's for student_id");

      return (double) total / gradebook[0].length;
    }

    public double getAverageForStudent (int ID)
    {
      int total = 0;
      for (int i = 1; i < gradebook[0].length; i++){
	  String s=  String.valueOf(gradebook[ID][i]);
	  int test = Integer.parseInt(s);
	  total += test;
      }

      return (double) total / (gradebook[0].length - 1);
    }

    //Gets Standard Deviation for a given assignment
    public double getStanDevForAssignment (int assignment)
    {
      double total = 0;
      double av = 0;

      for (int i = 0; i < gradebook[0].length; i++){
	  String s=  String.valueOf(gradebook[i][assignment]);
	  int test = Integer.parseInt(s);
	  total += test;
      }
      av = total / gradebook[0].length;
      total = 0;

      for (int i = 0; i < gradebook[0].length; i++){
	  String s=  String.valueOf(gradebook[i][assignment]);
	  int test = Integer.parseInt(s);
	  total +=
	      (test - av) * (test - av);

      }
      av = total / gradebook[0].length;
      av = Math.sqrt (av);

      return av;
    }

    //Gets Standard Deviation for a given student
    public double getStanDevForStudent (int ID)
    {
      double total = 0;
      double av = 0;

      for (int i = 1; i < gradebook[0].length; i++){
	  String s=  String.valueOf(gradebook[ID][i]);
	  int test = Integer.parseInt(s);
	  total += test;
      }

      av = total / (gradebook[0].length - 1);
      total = 0;

      for (int i = 1; i < gradebook[0].length; i++){
	  String s=  String.valueOf(gradebook[ID][i]);
	  int test = Integer.parseInt(s);
	  total += (test - av) * (test - av);
      }
      av = total / (gradebook[0].length - 1);
      av = Math.sqrt (av);

      return av;
    }
  }

    
  public static void main (String[]args)
  {
    Scanner user_input = new Scanner (System.in);
    File file = new File("grades.txt");
    File output = new File("output.txt");
    
    Student f = new Student ("Frank", 123);
    Student b = new Student ("Ben", 456);
    Student p = new Student ("Phoebe", 789);
    Student x = new Student ("Dummy", 000);

    System.out.println ("Please enter the new student's name:");
    String name = user_input.next ();
    System.out.println ("Please enter the student ID #:");
    String identification = user_input.next ();
    int iD = Integer.parseInt (identification);

    Student n = new Student (name, iD);
    System.out.println (n.getName ());
    System.out.println (n.getID ());

    System.out.println (f.getName ());

    Class c1 = new Class (file);
    //Prints out Ben's grades
    //Class c1 = new Class (SandG);
    c1.createFile(output);
    
    System.out.println ("Ben's grades:");
    c1.getGradesForStudent (1);

    //Prints out all grades for assignment 1
    System.out.println ("Grades for assignment 1:");
    c1.getGradesForClass (1);

    //Prints average for assignment 1
    double av = c1.getAverageForAssignment (1);
    System.out.print ("Average for assignment 1: ");
    System.out.println (av);

    //Prints average of Ben's grades
    av = c1.getAverageForStudent (1);
    System.out.println ("Average for student 1: " + av);

    c1.printHighestGradeForAssignment (1);
    c1.printLowestGradeForAssignment (1);
    c1.printLowestGradeForStudent (1);
    c1.printHighestGradeForStudent (1);
    double SD1 = c1.getStanDevForAssignment (1);
    System.out.println ("Stan Dev for assignment 1 = " + SD1);
    double SD2 = c1.getStanDevForStudent (1);
    System.out.println ("Stan Dev for student 1 = " + SD2);
  }

    @SuppressWarnings("unchecked")
    public void myMethod()
    {

    }

}
