import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Scanner;
import java.util.Vector;

public class GradeBook1 {

    public static class Student {
	private String name;	//Student name
	private int student_id; //Student ID
	
	//Constructor for Student
	public Student(String Sname, int ID){
	    name = Sname;
	    student_id = ID;
	}
	
	//Returns the student's name
	public String getName(){
	    return name;}
	
	//Returns the student's ID
	    public int getID(){
		return student_id;}	
    }
	
	public static class Rubric {
		private int HWValue;    //Value of a homework assignment for a class
		private int quizValue;  //Value of a quiz for a class
		private int labValue;   //Value of a lab assignment for a class
		
		//Default constructor
		public Rubric(){
			HWValue = 10;
			quizValue = 100;
			labValue = 50;}
		
		//Constructor with values
		public Rubric(int HW, int quiz, int lab){
			HWValue = HW;
			quizValue = quiz;
			labValue = lab;}
		
		//Returns value for a homework assignment
		public int getHWValue(){
			return HWValue;}
		
		//Returns value for a quiz
		public int getQuizValue(){
			return quizValue;}
		
		//Returns value for a lab
		public int getLabValue(){
			return labValue;}
		
		//Call this to change the values for each type of assignment
		public void editRubricValues(int HW, int quiz, int lab){
			HWValue = HW;
			quizValue = quiz;
			labValue = lab;}
	}
    

	public static class Class {
		private Vector<Vector<Integer>> gradebook; //Vector matrix that stores the student ID as the first
					   		   //As the first element in every row. Every other
					   		   //cell is a grade
		private int numHWs;        //# of homework assignments
		private int numQuizzes;    //# of quizzes
		private int numLabs;       //# of lab assignments
		private Rubric R;          //The rubric for the class
		
		//Constructor
		public Class(Vector<Vector<Integer>> SandG, int HWs, int Quizzes, int Labs){
			gradebook = new Vector<Vector<Integer>>(SandG);
			numHWs = HWs;
			numQuizzes = Quizzes;
			numLabs = Labs;
			R = new Rubric();}

	    	
		
		//Prints the whole gradebook
		public void printGradebook(){
			System.out.println(gradebook);}
		
		//Prints out all grades for a specific student
		public void getGradesForStudent(int ID){
			for(int i=1;i<(gradebook.get(ID)).size();i++){
//				System.out.println(gradebook.get(ID));
				System.out.println("Grade: " + ((gradebook.get(ID)).get(i)));
			}
		}

		//Prints out all grades for a specific assignment
		public void getGradesForClass(int assignment){
			if(assignment != 0){
				for(int i=0;i<gradebook.size();i++)
						System.out.println("Grade: " + (gradebook.get(i)).get(assignment));
			} else
				System.out.println("That's for student_id");
		}
		
		//Prints out the highest grade for a specific assignment
		public void printHighestGradeForAssignment(int assignment){
			int max=(gradebook.get(1)).get(assignment);
			if(assignment != 0){
				for(int i=1;i<gradebook.size();i++)
					if((gradebook.get(i)).get(assignment) > max)
						max = (gradebook.get(i)).get(assignment);
			} else
				System.out.println("That's for student_id");
				
			System.out.println("The highest grade is: " + max);
		}
		
		//Prints out the lowest grade for a specific assignment
		public void printLowestGradeForAssignment(int assignment){
			int low=(gradebook.get(1)).get(assignment);
			if(assignment != 0){
				for(int i=1;i<gradebook.size();i++)
					if((gradebook.get(i)).get(assignment) < low)
						low = (gradebook.get(i)).get(assignment);
			} else
				System.out.println("That's for student_id");
				
			System.out.println("The lowest grade is: " + low);
		}
	
		//Prints out the Student's highest grade
		public void printHighestGradeForStudent(int ID){
			int high = (gradebook.get(ID)).get(1);
			for(int i=1;i<(gradebook.get(ID)).size();i++)
				if((gradebook.get(ID)).get(i) > high)
					high = (gradebook.get(ID)).get(i);
					
			System.out.println("The highest grade for Student " + ID + " is " + high);
		}		
		
		//Prints out the Student's lowest grade
		public void printLowestGradeForStudent(int ID){
			int low = (gradebook.get(ID)).get(1);
			for(int i=1;i<(gradebook.get(ID)).size();i++)
				if((gradebook.get(ID)).get(i) < low)
					low = (gradebook.get(ID)).get(i);
					
			System.out.println("The lowest grade for Student #" + ID + " is " + low);
		}
		
		//Returns the value of the average for a given assignment
		public double getAverageForAssignment(int assignment){
			int total = 0;
			if(assignment != 0)
				for(int i=0;i<gradebook.size();i++)
					total += (gradebook.get(i)).get(assignment);
			 else
				System.out.println("That's for student_id");

			return (double) total/gradebook.size();
		}
		
		//Returns the value of the average for a given student
		public double getAverageForStudent(int ID){
			int total=0;
			for(int i=1;i<(gradebook.get(ID)).size();i++)
				total += (gradebook.get(ID)).get(i);
			
			return (double) total/((gradebook.get(ID)).size()-1);	
		}
		
		//Gets Standard Deviation for a given assignment
		public double getStanDevForAssignment(int assignment){
			double total = 0;
			double av=0;
			
			for(int i=0;i<gradebook.size();i++)
				total += (gradebook.get(i)).get(assignment);
				
			av = total/gradebook.size();
			total = 0;
			
			for(int i=0;i<gradebook.size();i++)
				total += ((gradebook.get(i)).get(assignment)-av) * ((gradebook.get(i)).get(assignment)-av);
				
			av = total/gradebook.size();
			av = Math.sqrt(av);

			return av;
		}
		
		//Gets Standard Deviation for a given student
		public double getStanDevForStudent(int ID){
			double total = 0;
			double av=0;
			
			for(int i=1;i<(gradebook.get(ID)).size();i++)
				total += (gradebook.get(ID)).get(i);
				
			av = total/((gradebook.get(ID)).size()-1);
			total = 0;
			
			for(int i=1;i<(gradebook.get(ID)).size();i++)
				total += ((gradebook.get(ID)).get(i)-av) * ((gradebook.get(ID)).get(i)-av);
				
			av = total/((gradebook.get(ID)).size()-1);
			av = Math.sqrt(av);

			return av;
		}
		
		//Returns the weighted average for the student (using the amount of different assignments)
		public double getWeightedAverageForStudent(int ID){
			int total=0;
			int av;
			
			for(int i=1;i<(gradebook.get(ID)).size();i++)
				total += (gradebook.get(ID)).get(i);
			av = total/((numHWs*R.getHWValue())+(numQuizzes*R.getQuizValue())+(numLabs*R.getLabValue()));
			
			return av;
		}
	}

	public static void main(String[] args) {
		
	    Student f = new Student("Frank",123);
	    Student b = new Student("Ben",456);
	    Student p = new Student("Phoebe",789);
	    Student x = new Student("Dummy",000);
		
		Vector<Vector<Integer>> SandG = new Vector<Vector<Integer>>();
		Vector<Integer> test1 = new Vector<Integer>();
		Vector<Integer> test2 = new Vector<Integer>();
		Vector<Integer> test3 = new Vector<Integer>();
		Vector<Integer> test4 = new Vector<Integer>();
		
		//Creates the initial 2D Vector
		test1.addElement(123);
		test1.addElement(10);
		test1.addElement(20);
		test1.addElement(30);
		SandG.addElement(test1);
		test2.addElement(456);
		test2.addElement(40);
		test2.addElement(50);
		test2.addElement(60);
		SandG.addElement(test2);
		test3.addElement(789);
		test3.addElement(70);
		test3.addElement(80);
		test3.addElement(90);
		SandG.addElement(test3);
		test4.addElement(000);
		test4.addElement(0);
		test4.addElement(0);
		test4.addElement(0);
		SandG.addElement(test4);
		
		int HWs = 3;
		int Quizzes = 0;
		int Labs = 0;
		
		//Prints out Ben's grades
		Class c1 = new Class(SandG, HWs, Quizzes, Labs);
		System.out.println("After creating c1: ");
		c1.printGradebook();
		System.out.println("Ben's grades:");
		c1.getGradesForStudent(1);
		
		//Prints out all grades for assignment 1
		System.out.println("Grades for assignment 1:");
		c1.getGradesForClass(1);
		
		//Prints average for assignment 1
		double av = c1.getAverageForAssignment(1);
		System.out.print("Average for assignment 1: ");
		System.out.println(av);
		
		//Prints average of Ben's grades
		av = c1.getAverageForStudent(1);
		System.out.println("Average for student 1: " + av);
		
		c1.printHighestGradeForAssignment(1);
		c1.printLowestGradeForAssignment(1);
		c1.printLowestGradeForStudent(1);
		c1.printHighestGradeForStudent(1);
		double SD1 = c1.getStanDevForAssignment(1);
		System.out.println("Stan Dev for assignment 1 = " + SD1);
		double SD2 = c1.getStanDevForStudent(1);
		System.out.println("Stan Dev for student 1 = " + SD2);
	}
}
