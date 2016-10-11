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

	public static class Class {
		private int[][] gradebook; //Matrix that stores the student ID as the first
					   //As the first element in every row. Every other
					   //cell is a grade
		
		//Constructor
		public Class(int[][] SandG){
			gradebook = SandG;}
		
		//Prints out all grades for a specific student
		public void getGradesForStudent(int ID){
			for(int i=1;i<gradebook[0].length;i++)
					System.out.println("Grade: " + gradebook[ID][i]);
		}

		//Prints out all grades for a specific assignment
		public void getGradesForClass(int assignment){
			if(assignment != 0){
				for(int i=0;i<gradebook[0].length;i++)
						System.out.println("Grade: " + gradebook[i][assignment]);
			} else
				System.out.println("That's for student_id");
		}
		
		//Prints out the highest grade for a specific assignment
		public void printHighestGradeForAssignment(int assignment){
			int max=gradebook[1][assignment];
			if(assignment != 0){
				for(int i=1;i<gradebook[0].length;i++)
					if(gradebook[i][assignment] > max)
						max = gradebook[i][assignment];
			} else
				System.out.println("That's for student_id");
				
			System.out.println("The highest grade is: " + max);
		}
		
		//Prints out the lowest grade for a specific assignment
		public void printLowestGradeForAssignment(int assignment){
			int low=gradebook[1][assignment];
			if(assignment != 0){
				for(int i=1;i<gradebook[0].length;i++)
					if(gradebook[i][assignment] < low)
						low = gradebook[i][assignment];
			} else
				System.out.println("That's for student_id");
				
			System.out.println("The lowest grade is: " + low);
		}
	
		//Prints out the Student's highest grade
		public void printHighestGradeForStudent(int ID){
			int high = gradebook[ID][1];
			for(int i=1;i<gradebook[0].length;i++)
				if(gradebook[ID][i] > high)
					high = gradebook[ID][i];
					
			System.out.println("The highest grade for Student " + ID + " is " + high);
		}		
		
		//Prints out the Student's lowest grade
		public void printLowestGradeForStudent(int ID){
			int low = gradebook[ID][1];
			for(int i=1;i<gradebook[0].length;i++)
				if(gradebook[ID][i] < low)
					low = gradebook[ID][i];
					
			System.out.println("The lowest grade for Student " + ID + " is " + low);
		}
		
		//Returns the value of the average for a given assignment
		public double getAverageForAssignment(int assignment){
			int total = 0;
			if(assignment != 0)
				for(int i=0;i<gradebook[0].length;i++)
					total += gradebook[i][assignment];
			 else
				System.out.println("That's for student_id");

			return (double) total/gradebook[0].length;
		}
		
		public double getAverageForStudent(int ID){
			int total=0;
			for(int i=1;i<gradebook[0].length;i++)
				total += gradebook[ID][i];
			
			return (double) total/(gradebook[0].length-1);	
		}
		
		//Gets Standard Deviation for a given assignment
		public double getStanDevForAssignment(int assignment){
			double total = 0;
			double av=0;
			
			for(int i=0;i<gradebook[0].length;i++)
				total += gradebook[i][assignment];
				
			av = total/gradebook[0].length;
			total = 0;
			
			for(int i=0;i<gradebook[0].length;i++)
				total += (gradebook[i][assignment]-av) * (gradebook[i][assignment]-av);
				
			av = total/gradebook[0].length;
			av = Math.sqrt(av);

			return av;
		}
		
		//Gets Standard Deviation for a given student
		public double getStanDevForStudent(int ID){
			double total = 0;
			double av=0;
			
			for(int i=1;i<gradebook[0].length;i++)
				total += gradebook[ID][i];
				
			av = total/(gradebook[0].length-1);
			total = 0;
			
			for(int i=1;i<gradebook[0].length;i++)
				total += (gradebook[ID][i]-av) * (gradebook[ID][i]-av);
				
			av = total/(gradebook[0].length-1);
			av = Math.sqrt(av);

			return av;
		}
	}

	public static void main(String[] args) {
		//Create 4 test students
		Student f = new Student("Frank",123);
		Student b = new Student("Ben",456);
		Student p = new Student("Phoebe",789);
		Student x = new Student("Dummy",000);
		
		//Create test gradebook
		int[][] SandG = { {123, 10, 20, 30},
				  {456, 40, 50, 60},
				  {789, 70, 80, 90},
				  {000, 0, 0, 0, 0}
				};
		
		//Prints out Ben's grades
		Class c1 = new Class(SandG);
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