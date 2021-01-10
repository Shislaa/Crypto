import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lab5_2 {

	static long startTime = System.currentTimeMillis();

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String[] data = {"0","1","2","3","4","5","6","7","8","9"};
		String[] data2 = null;
		String[] ResultSet = {"902608824fae2a1918d54d569d20819a4288a4e4",
							"88d0b34055b79644196fce25f876bc1a5ef654d3",
							"5b8f495b7f02b62eb228c5dbece7c2f81b60b9a3"};
		List<String> result = new ArrayList<String>(Arrays.asList(ResultSet));

		for(int i = 1; i < 7; i++){
			data2 = traverse(data, data2, i, result);
		}
	}

	public static String[] traverse(String[] data, String[] data2, int length, List result) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if(length == 1){
			return data;
		}
		else{
			String[] newList = new String[(int) Math.pow(data.length, length)];
			int arrayIndx = 0;

			for(int i = 0 ; i < data.length; i++){
				for(int j = 0; j < data2.length;j++){

					// Generate initial 6 number
					int[] replacer = {0,0,0,0,0,0};
					int arraylength = 6;
					String s = data2[j].toString() + data[i].toString();
					int slength = s.length();
					while(slength > 0){
						replacer[arraylength - 1] = Character.getNumericValue(s.charAt(slength - 1));
						arraylength--;
						slength--;
					}

					newList[arrayIndx] = s;
					arrayIndx++;

					int[] tempoIntArray = BCHGenerator(replacer);
					String tempoSArray = "";
					if(tempoIntArray != null){
						for(int k = 0; k < tempoIntArray.length;k++){
							tempoSArray = tempoSArray.toString() + Integer.toString(tempoIntArray[k]);
						}
					}
					// Generate BCH
					Lab4 lb4 = new Lab4();
					String s1 =  lb4.SHA1(tempoSArray);

//					System.out.println("So ne: " + tempoSArray);

					//Compare the generated code to ResultSet
					for(int k = 0; k < result.size();k++){
						if(result.get(k).equals(s1)){
							long endTime = System.currentTimeMillis();
							System.out.println("It takes " + (endTime - startTime) + " milisecond to find: "+ result.get(k));
							System.out.println("Password is: " + tempoSArray);
							result.remove(k);
						}
					}


				}
			}
			return newList;
		}
	}


	public static int[] BCHGenerator(int[] num){
		int d7 = (4*num[0] + 10*num[1] + 9*num[2] + 2*num[3] + num[4] + 7*num[5]) % 11;
		int d8 = (7*num[0] + 8*num[1] + 7*num[2] + num[3] + 9*num[4] + 6*num[5]) % 11;
		int d9 = (9*num[0] + num[1] + 7*num[2] + 8*num[3] + 7*num[4] + 7*num[5]) % 11;
		int d10 = (1*num[0] + 2*num[1] + 9*num[2] + 10*num[3] + 4*num[4] + num[5]) % 11;

		if(d7 == 10 || d8 == 10 || d9 == 10 || d10 == 10 ){
//			System.out.println("Unusable number");
			return null;
		}
		else{
//			for(int i = 0; i < num.length;i++){
//				System.out.print(num[i]);
//			}
//			System.out.println(d7 + "" + d8 + "" + d9 +"" + d10);

			int[] newnum = new int[10];
			for(int i = 0; i < 7; i ++){
				if(i < 6){
					newnum[i] = num[i];
				}
				else{
					newnum[6] = d7;
					newnum[7] = d8;
					newnum[8] = d9;
					newnum[9] = d10;
				}
			}

			return newnum;
		}
	}
}
