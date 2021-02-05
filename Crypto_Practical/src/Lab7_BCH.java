import java.util.Arrays;
import java.util.Scanner;

public class Lab7_BCH {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Input 12 numbers: ");

		sc.useDelimiter(", ");
		int[] arr = new int[16];
		int index = 0;

		while(sc.hasNext()){
			int temp = sc.nextInt();
			arr[index] = temp;
			System.out.println(arr[index]);
			index++;

////			if(index == 11){
////				System.out.println("Ula");
////				sc.nextLine();
////
////			}
			if(index == 12){
				break;
			}
		}
		BCH_Generator(arr);
//		while(sc.hasNext()){
//			int temp = sc.nextInt();
//			arr[index] = temp;
//			System.out.println(arr[index]);
//			index++;
//
//////			if(index == 11){
//////				System.out.println("Ula");
//////				sc.nextLine();
//////
//////			}
//			if(index == 16){
//				break;
//			}
//		}
//		Error_checking(arr);
		sc.close();
	}
	public static void BCH_Generator(int[] number){
		int d13 = 0;
		int d14 = 0;
		int d15 = 0;
		int d16 = 0;

		int arrd13[] = {4,10,3,1,5,16,1,12,16,14,7,13};
		int arrd14[] = {2,15,15,16,15,9,12,4,16,11,3,6};
		int arrd15[] = {3,11,16,4,12,9,15,16,15,15,2,13};
		int arrd16[] = {7,14,16,12,1,16,5,1,3,10,4,1};

		for(int i = 0; i < 12; i++){
			int num = number[i];
			d13 += num*arrd13[i];
			d14 += num*arrd14[i];
			d15 += num*arrd15[i];
			d16 += num*arrd16[i];
		}

		d13 = Math.floorMod(d13, 17);
		d14 = Math.floorMod(d14, 17);
		d15 = Math.floorMod(d15, 17);
		d16 = Math.floorMod(d16, 17);

		System.out.println("Your syndromes are: D13 = " + d13 + " D14 = "+ d14 + " D15 = " + d15+" D16 = "+d16);



	}

	public static void Error_checking(int[] num){
		// Generate syndromes
		int s1 = new Integer(0);
		int s2 = new Integer(0);
		int s3 = new Integer(0);
		int s4 = new Integer(0);

		int arrs3[] = {1,4,9,16,8,2,15,13,13,15,2,8,16,9,4,1};
		int arrs4[] = {1,8,10,13,6,12,3,2,15,14,5,11,4,7,9,16};
		int mul = 1;

		for(int i = 0; i < num.length;i++){
			s1 = s1 + num[i];
			s2 = s2 + mul*num[i];
			mul++;
			s3 = s3+ arrs3[i]*num[i];
			s4 = s4 + arrs4[i]*num[i];
		}

		s1 = Math.floorMod(s1, 17);
		s2 = Math.floorMod(s2, 17);
		s3 = Math.floorMod(s3, 17);
		s4 = Math.floorMod(s4, 17);
		System.out.println("Your syndromes is: " + s1 + " " + s2 + " " + s3 + " " + s4);

		// Checking errors
		int P = Math.floorMod(s2*s2 - s1*s3, 17), Q = Math.floorMod(s1*s4 - s2*s3, 17), R = Math.floorMod(s3*s3 - s2*s4, 17);
		System.out.println("Your P,Q,R are: " + P +" "+Q+" "+R);

		if(No_error(s1, s2, s3, s4)){
			Print_result_T_2(Arrays.toString(num), num);
			System.out.println("No Error Detected");
			System.out.println("");
		}


		else if(P == 0 && Q == 0 && R == 0){
			int pos = s2/s1;
			if(pos < 1){
				int sttmp = inverse(s1, 17);
				pos = Math.floorMod(s2*sttmp, 17);
			}
				int[] temp = num;
				String tempS = Arrays.toString(num);
				temp[pos - 1] = Math.floorMod(temp[pos - 1] - s1, 17);
				Print_result_T_2(tempS, temp);
				System.out.println("There a single error at pos = " + pos +" value: " + s1);


		}

		else if(P != 0 || Q != 0 || R != 0){
			double determinant = Q*Q - 4*P*R;
			double detersqrt = 0;
			if(determinant < 0){
				determinant = Math.floorMod((int) determinant, 17);
			}
			detersqrt = squareRootValue((int) determinant, 17);

			double iTemp = ((-1) * Q + detersqrt);
			if(iTemp < 0){
				iTemp = Math.floorMod((int) iTemp, 17);
			}
			double jTemp = ((-1) * Q - detersqrt);
			if(jTemp < 0){
				jTemp = Math.floorMod((int) jTemp, 17);
			}
			double sochia = (2*P);
			sochia = inverse((int) sochia, 17);
			double i = Math.floorMod((int) (iTemp * sochia), 17);
			double j = Math.floorMod((int) (jTemp * sochia), 17);
			double btemp = (i*s1 - s2);
			if(btemp < 0){
				btemp = Math.floorMod((int) btemp, 17);
			}
			double sochia2 = 0;
			if(i-j < 0){
				int sochiatemp = Math.floorMod((int) (i-j), 17);
				sochia2 = inverse(sochiatemp,17);
			}
			else{
				sochia2 = inverse((int) (i-j),17);
			}

			double b = Math.floorMod((int) (btemp * sochia2), 17);
			double a = Math.floorMod((int) (s1 - b), 17);

			////////////////Print determinant
			System.out.println("Determinant: " + determinant);


			if(squareRoot((int) determinant, 17) == false || i == 0 || j == 0){
				Print_result_T_2(Arrays.toString(num), num);
				System.out.println("There are more than 2 errors (i,a): " + i+","+a+" (j,b): "+j+","+b);
				System.out.println("");
			}
			else{
				double fix1 = Math.floorMod((int) (num[(int) i - 1] - a), 17);
				double fix2 = Math.floorMod((int) (num[(int) j - 1] - b), 17);
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
	public static void Print_result_T_2(String ori, int[] fixed){
		System.out.println("Test");
		System.out.println("input ( " + ori + ")");
		System.out.println("output ( " + Arrays.toString(fixed) + ")");
	}
}
