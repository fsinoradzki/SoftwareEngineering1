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
		private int participationValue;	//Value of participation for a class
		private int extraCreditValue;	//Value of extra credit for a class
		
		//Default constructor
		public Rubric(){
			HWValue = 10;
			quizValue = 100;
			labValue = 50;
			participationValue=50;
			extraCreditValue=100;}
		
		//Constructor with values
		public Rubric(int HW, int quiz, int lab, int PV, int ECV){
			HWValue = HW;
			quizValue = quiz;
			labValue = lab;
			participationValue=PV;
			extraCreditValue=ECV;}
		
		//Returns value for a homework assignment
		public int getHWValue(){
			return HWValue;}
		
		//Returns value for a quiz
		public int getQuizValue(){
			return quizValue;}
		
		//Returns value for a lab
		public int getLabValue(){
			return labValue;}
			
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
    

	public static class Class {
		private Vector<Vector<Integer>> gradebook; //Matrix that stores the student ID as the first
					   							  //As the first element in every row. Every other
					   							  //cell is a grade
		private Vector<Student> students;          //Vector that contains Student objects for the class
		private Vector<Integer> values;			  //Vector that contains indexes of grades
												  //  0 is a HW, 1 is a quiz, 2 is a lab
		private int numHWs;        				  //# of homework assignments
		private int numQuizzes;    				  //# of quizzes
		private int numLabs;      				  //# of lab assignments
		private Rubric R;         				  //The rubric for the class
		Boolean locked;							  //Boolean value for locking the class
		
		
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
		
		//Constructor
		public Class(Vector<Vector<Integer>> SandG, Vector<Student> pupils, Vector<Integer> indexes, int HWs, int Quizzes, int Labs){
			gradebook = new Vector<Vector<Integer>>(SandG);
			students = new Vector<Student>(pupils);
			values = new Vector<Integer>(indexes);
			numHWs = HWs;
			numQuizzes = Quizzes;
			numLabs = Labs;
			R = new Rubric();
			locked = false;
			}
		
		//Prints the whole gradebook
		public void printGradebook(){
			System.out.println(gradebook);}
			
		//Given a student ID, prints the corresponding student's name
		//If the ID is not found, returns the first student's name
		public String printStudentName(int ID){
			int position = 0;
			for(int i=1;i<students.size();i++)
				if(students.get(i).getID() == ID)
					position = i;
					
			return students.get(position).getName();
		}
		
		//Prints out all grades for a specific student
		public void getGradesForStudent(int ID){
			for(int i=1;i<(gradebook.get(ID)).size();i++)
				System.out.println("Grade: " + ((gradebook.get(ID)).get(i)));
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
	
//		//Prints out the Student's highest grade
//		public void printHighestGradeForStudent(int ID){
//			int high = (gradebook.get(ID)).get(1);
//			for(int i=1;i<(gradebook.get(ID)).size();i++)
//				if((gradebook.get(ID)).get(i) > high)
//					high = (gradebook.get(ID)).get(i);
//					
//			System.out.println("The highest grade for Student " + ID + " is " + high);
//		}		
//		
//		//Prints out the Student's lowest grade
//		public void printLowestGradeForStudent(int ID){
//			int low = (gradebook.get(ID)).get(1);
//			for(int i=1;i<(gradebook.get(ID)).size();i++)
//				if((gradebook.get(ID)).get(i) < low)
//					low = (gradebook.get(ID)).get(i);
//					
//			System.out.println("The lowest grade for Student #" + ID + " is " + low);
//		}
		
		//Returns the value of the average for a given assignment
		//Adds all values together, divides by the number of students
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
		//Adds up all values, divides by number of assignments
		//NOT THE WEIGHTED AVERAGE!
		public double getAverageForStudent(int ID){
			int total=0;
			for(int i=1;i<(gradebook.get(ID)).size();i++)
				total += (gradebook.get(ID)).get(i);
			
			return (double) total/((gradebook.get(ID)).size()-1);	
		}
		
		//Gets Standard Deviation for a given assignment
		//Look up the Standard Deviation formula
		//That's what it's doing
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
		//Look up the Standard Deviation formula
		//That's what it's doing
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
		
		//Gets type of assignment given an index
		public int getAssignmentType(int assignment){
			int type = 0;
			
			if(values.get(assignment) == 1)
				type = 1;
			else if(values.get(assignment) == 2)
				type = 2;
			else if(values.get(assignment) == 3)
				type = 3;
			else if(values.get(assignment) == 4)
				type = 4;
				
			return type;
		}
		
		//Adds a new student. Initializes all their grades to 0.
		public void addStudent(Student kid){
			//Adds the student object to the Students vectors
			students.addElement(kid);
			
			//Creates the vector to add to gradebook. Sets the first value to the student's ID
			//All other values are initialized to 0
			Vector<Integer> grades = new Vector<Integer>();
			grades.addElement(kid.getID());
			for(int i = 1;i<(gradebook.get(0)).size();i++)
				grades.addElement(0);
				
			gradebook.addElement(grades);
		}
		
		//Adds a new assignment based on the assignment type. Initializes all new values to 0.
		public void addAssignment(int type){
			System.out.println("Made it to addAssignment!");
			if(type < -1 && type > 4)
				System.out.println("Not a valid assignment type.");
			else{
				System.out.println(type);
				values.addElement(type);
				if(type == 0)
					numHWs++;
				else if(type == 1)
					numQuizzes++;
				else if(type == 2)
					numLabs++;
				for(int i=0;i<gradebook.size();i++)
					(gradebook.get(i)).addElement(0);
			}
		}
		
		//Changes a specific grade. When you call this from text, remember that the first position
		//Is actually the student ID. DERP.
		public void changeGrade(int newGrade, int ID, int assignment){
			(gradebook.get(ID)).set(assignment, newGrade);
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
				
			System.out.println("The highest HW grade for Student #" + ID + " is " + high);
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
			Vector<Integer> labIndexes = new Vector<Integer>();
			for(int i = 0;i<values.size();i++)
				if(values.get(i) == 2)
					labIndexes.addElement(i);
			
			int high = (gradebook.get(ID)).get(labIndexes.get(0));
			
			for(int i=1;i<(gradebook.get(ID)).size();i++)
				if(i < labIndexes.size())
					if(i == labIndexes.get(i))
						if((gradebook.get(ID)).get(i) > high)
							high = (gradebook.get(ID)).get(i);
				
			System.out.println("The highest lab grade for Student #" + ID + " is " + high);
		}		
		
		//Prints out the Student's lowest grade
		public void printLowestLabGradeForStudent(int ID){
			Vector<Integer> labIndexes = new Vector<Integer>();
			for(int i = 0;i<values.size();i++)
				if(values.get(i) == 2)
					labIndexes.addElement(i);
			
			int low = (gradebook.get(ID)).get(labIndexes.get(0));
			
			for(int i=1;i<(gradebook.get(ID)).size();i++)
				if(i < labIndexes.size())
					if(i == labIndexes.get(i))
						if((gradebook.get(ID)).get(i) < low)
							low = (gradebook.get(ID)).get(i);
					
			System.out.println("The lowest lab grade for Student #" + ID + " is " + low);
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
		Class c1 = new Class();
	}
}