public class GradeBook1 {

	public static class Student {
		private String name;
		private int student_id;

		public Student(String Sname, int ID){
			name = Sname;
			student_id = ID;
		}
		
 		public String getName(){
			return name;}
		
		public int getID(){
			return student_id;}
	}

	public static class Class {
		private int[][] gradebook;
		
		public Class(int[][] SandG){
			gradebook = SandG;}
		
		public void getGradesForStudent(int ID){
			for(int i=1;i<gradebook[0].length;i++)
					System.out.println("Grade: " + gradebook[ID][i]);
		}

		public void getGradesForClass(int assignment){
			if(assignment != 0){
				for(int i=0;i<gradebook[0].length;i++)
						System.out.println("Grade: " + gradebook[assignment][i]);
			} else
				System.out.println("That's for student_id");
		}
	}

	public static void main(String[] args) {
		Student f = new Student("Frank",123);
		Student b = new Student("Ben",456);
		Student p = new Student("Phoebe",789);

		System.out.println(f.getName());
		
		int[][] SandG = { {123, 100, 90, 100},
				  {456, 90, 100, 100},
				  {789, 100, 100, 90},
				  {000, 0, 0, 0, 0}
				};
		
		Class c1 = new Class(SandG);
		System.out.println("Ben's grades:");
		c1.getGradesForStudent(1);
		
		System.out.println("Grades for assignment 1:");
		c1.getGradesForClass(1);
	}
}
