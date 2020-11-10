import java.util.Scanner;

public class Lab2 {

	public static void main(String[] args) {

		System.out.println("Type 1 for Task 1, 2 for Task 2, 3 for Task 3, 0 to exit: ");

		Scanner sc = new Scanner(System.in);
		int temp = sc.nextInt();
		while(temp != 0){
			if (temp == 1){
				System.out.println("Type in 6-digit-number: ");
				String temp2 = sc.next();
				int[] tas1 = new int[6];
				int len = 0;
				for(int i = 0; i < temp2.length();i++){
					if(Character.isDigit(temp2.charAt(i))){
						tas1[len] = Integer.parseInt(String.valueOf(temp2.charAt(i)));
						len++;
					}
				}
				Task1(tas1);
			}
			else if(temp == 2){
				System.out.println("Type in 10-digit-number: ");
				String temp2 = sc.next();
				int[] tas1 = new int[10];
				int len = 0;
				for(int i = 0; i < temp2.length();i++){
					if(Character.isDigit(temp2.charAt(i))){
						tas1[len] = Integer.parseInt(String.valueOf(temp2.charAt(i)));
						len++;
					}
				}
				Task2(tas1);
			}
			else if(temp == 3){
				System.out.println("Type in X: ");
				int X = sc.nextInt();
				System.out.println("Type in Y: ");
				int Y = sc.nextInt();
				System.out.println("Type in N: ");
				int N = sc.nextInt();
				Task3(X,Y,N);
			}
			else{
				System.out.println("Please input a valid value, or type 0 to exit: ");
				temp = sc.nextInt();
			}
			System.out.println("Type 1 for Task 1, 2 for Task 2, 3 for Task 3, 0 to exit: ");
			temp = sc.nextInt();

		}

	}

	public static void Task1(int[] num){
		int d7 = (4*num[0] + 10*num[1] + 9*num[2] + 2*num[3] + num[4] + 7*num[5]) % 11;
		int d8 = (7*num[0] + 8*num[1] + 7*num[2] + num[3] + 9*num[4] + 6*num[5]) % 11;
		int d9 = (9*num[0] + num[1] + 7*num[2] + 8*num[3] + 7*num[4] + 7*num[5]) % 11;
		int d10 = (1*num[0] + 2*num[1] + 9*num[2] + 10*num[3] + 4*num[4] + num[5]) % 11;

		if(d7 == 10 || d8 == 10 || d9 == 10 || d10 == 10 ){
			System.out.println("Unusable number");
		}
		else{
			for(int i = 0; i < num.length;i++){
				System.out.print(num[i]);
			}
			System.out.println(d7 + "" + d8 + "" + d9 +"" + d10);
		}
	}

	public static void Task2(int[] num){
		int s1 = new Integer(0);
		int s2 = new Integer(0);
		int s3 = new Integer(0);
		int s4 = new Integer(0);

		int arrs3[] = {1,4,9,5,3,3,5,9,4,1};
		int arrs4[] = {1,8,5,9,4,7,2,6,3,10};
		int mul = 1;

		for(int i = 0; i < num.length;i++){
			s1 = s1 + num[i];
			s2 = s2 + mul*num[i];
			mul++;
			s3 = s3+ arrs3[i]*num[i];
			s4 = s4 + arrs4[i]*num[i];
		}
		s1 = s1 % 11;
		s2 = s2 % 11;
		s3 = s3 % 11;
		s4 = s4 % 11;
		System.out.println("Your syndromes is: " + s1 + "" + s2 + "" + s3 + "" + s4);

	}
	public static void Task3(int x, int y, int n){
		System.out.println(x + " + "+ y +" mod 11 = " + (Math.floorMod(x + y, n)));
		System.out.println(x + " - "+ y +" mod 11 = " + (Math.floorMod(x - y, n)));
		System.out.println(x + " * "+ y +" mod 11 = " + (Math.floorMod(x*y, n)));
		int temp = inverse(y,n);
		System.out.println(x + " / "+ y +" mod 11 = " + (Math.floorMod(x * temp, n)));
	}

	public static int inverse(int a, int n) {
		int t = 0; int newt = 1;
		int r = n; int newr = a;    int q, temp;
		while(newr != 0) {
			q = r / newr;  /* integer division */
			temp = newt;   /* remember newt    */
			newt = t - q*newt;
			t = temp;
			temp = newr;   /* remember newr    */
			newr = r - q*newr;
			r = temp;
		}
	 	if(r > 1) return -1; /* not invertible */
		if(t < 0) t = t + n; /* change to positive */
		return t;
	}
}
