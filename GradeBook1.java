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
						System.out.println("Grade: " + gradebook[assignment][i]);
			} else
				System.out.println("That's for student_id");
		}
	}

	public static void main(String[] args) {
		//Create 3 test students
		Student f = new Student("Frank",123);
		Student b = new Student("Ben",456);
		Student p = new Student("Phoebe",789);

		System.out.println(f.getName());
		
		//Create test gradebook
		int[][] SandG = { {123, 100, 90, 100},
				  {456, 90, 100, 100},
				  {789, 100, 100, 90},
				  {000, 0, 0, 0, 0}
				};
		
		//Prints out Ben's grades
		Class c1 = new Class(SandG);
		System.out.println("Ben's grades:");
		c1.getGradesForStudent(1);
		
		//Prints out all grades for assignment 1
		System.out.println("Grades for assignment 1:");
		c1.getGradesForClass(1);
	}
}
