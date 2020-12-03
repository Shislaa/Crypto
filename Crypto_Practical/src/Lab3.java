import java.util.Arrays;
import java.util.Scanner;

public class Lab3 {

	public static void main(String[] args) {
		System.out.println("Type 1 for Task 1, 2 for Task 2, 0 to exit: ");

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
				Task_1(tas1);
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
				Task_2(tas1);
			}
			else{
				System.out.println("Please input a valid value, or type 0 to exit: ");
				temp = sc.nextInt();
			}
			System.out.println("Type 1 for Task 1, 2 for Task 2, 0 to exit: ");
			temp = sc.nextInt();

		}
	}

	static void Task_1(int[] num){
		Lab2 l2 = new Lab2();
		l2.Task1(num);
	}
	public static void Task_2(int[] num){
		// Generate syndromes
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

		// Checking errors
		int P = Math.floorMod(s2*s2 - s1*s3, 11), Q = Math.floorMod(s1*s4 - s2*s3, 11), R = Math.floorMod(s3*s3 - s2*s4, 11);
		System.out.println("Your P,Q,R are: " + P +" "+Q+" "+R);

		if(No_error(s1, s2, s3, s4)){
			Print_result_T_2(Arrays.toString(num), num);
			System.out.println("No Error Detected");
			System.out.println("");
		}


		else if(P == 0 && Q == 0 && R == 0){
			int pos = s2/s1;
			if(pos < 1){
				System.out.println("There are more than 2 errors");
			}
			else{
				int[] temp = num;
				String tempS = Arrays.toString(num);
				temp[pos - 1] = Math.floorMod(temp[pos - 1] - s1, 11);
				Print_result_T_2(tempS, temp);
				System.out.println("There a single error at pos = " + pos +" value: " + s1);
			}

		}

		else if(P != 0 || Q != 0 || R != 0){
			double determinant = Q*Q - 4*P*R;
			double detersqrt = 0;
			if(determinant < 0){
				determinant = Math.floorMod((int) determinant, 11);
			}
			detersqrt = squareRootValue((int) determinant, 11);

			double iTemp = ((-1) * Q + detersqrt);
			if(iTemp < 0){
				iTemp = Math.floorMod((int) iTemp, 11);
			}
			double jTemp = ((-1) * Q - detersqrt);
			if(jTemp < 0){
				jTemp = Math.floorMod((int) jTemp, 11);
			}
			double sochia = (2*P);
			sochia = inverse((int) sochia, 11);
			double i = Math.floorMod((int) (iTemp * sochia), 11);
			double j = Math.floorMod((int) (jTemp * sochia), 11);
			double btemp = (i*s1 - s2);
			if(btemp < 0){
				btemp = Math.floorMod((int) btemp, 11);
			}
			double sochia2 = 0;
			if(i-j < 0){
				int sochiatemp = Math.floorMod((int) (i-j), 11);
				sochia2 = inverse(sochiatemp,11);
			}
			else{
				sochia2 = inverse((int) (i-j),11);
			}

			double b = Math.floorMod((int) (btemp * sochia2), 11);
			double a = Math.floorMod((int) (s1 - b), 11);

			////////////////Print determinant
			System.out.println("Determinant: " + determinant);


			if(squareRoot((int) determinant, 11) == false || i == 0 || j == 0){
				Print_result_T_2(Arrays.toString(num), num);
				System.out.println("There are more than 2 errors (i,a): " + i+","+a+" (j,b): "+j+","+b);
				System.out.println("");
			}
			else{
				double fix1 = Math.floorMod((int) (num[(int) i - 1] - a), 11);
				double fix2 = Math.floorMod((int) (num[(int) j - 1] - b), 11);
				if(fix1 != 10 && fix2 != 10){
					int[] fixed = num;
					String tempoS = Arrays.toString(num);
					fixed[(int) i - 1] = (int) fix1;
					fixed[(int) j - 1] = (int) fix2;
					Print_result_T_2(tempoS, fixed);
					System.out.println("There are two errors at (i,j): " + i +"," +j+" with (a,b)"+a+","+b);
					System.out.println("");
				}
				else{
					Print_result_T_2(Arrays.toString(num), num);
					System.out.println("There are more than 2 errors(i,a): " + i+","+a+" (j,b): "+j+","+b);
					System.out.println("");
				}

			}
		}

	}

	public static boolean No_error(int s1, int s2, int s3, int s4){
		if(s1 == 0 && s2 == 0 && s3 == 0 && s4 == 0){
			return true;
		}
		else{
			return false;
		}
	}

	static boolean squareRoot(int n, int p)
    {
        n = n % p;

        // One by one check all numbers
        // from 2 to p-1
        for (int x = 2; x < p; x++) {
            if ((x * x) % p == n) {
                System.out.println("Square "
                    + "root is " + x);
                return true;
            }
        }
        System.out.println("Square root "
                + "doesn't exist");
        return false;
    }

	static int squareRootValue(int n, int p)
    {
        n = n % p;

        // One by one check all numbers
        // from 2 to p-1
        for (int x = 2; x < p; x++) {
            if ((x * x) % p == n) {
                return x;
            }
        }
        return 0;
    }

	public static void Print_result_T_2(String ori, int[] fixed){
		System.out.println("Test");
		System.out.println("input ( " + ori + ")");
		System.out.println("output ( " + Arrays.toString(fixed) + ")");
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
