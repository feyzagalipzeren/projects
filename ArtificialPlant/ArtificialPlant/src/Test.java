import java.util.Scanner;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the angle:(if no turn then enter 0)");
		double angle = input.nextDouble();

		System.out.println("Please enter the distance:");
		int distance = input.nextInt();
		
		System.out.println("Please enter the max iterasyon number:");
		int max_it = input.nextInt();
		input.nextLine();
		
		System.out.println("Please enter the started rule");
		String started = input.nextLine();
		
		System.out.println("Please enter the rules:(letter->new" +
				"Note:Dont change push and pop. )");
		String rl = input.nextLine();
		
		Tu1 turtle = new Tu1(angle, distance, max_it, rl,started);
		
		
		//new Tu1();

	}

}
