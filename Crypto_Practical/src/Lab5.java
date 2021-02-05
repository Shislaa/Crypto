import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Lab5 {

	static 	Map<Long,String> passmap = new HashMap<Long,String>();
	static long startTime = System.currentTimeMillis();

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String[] database = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
		String[] database2 = null;
		String[] resultset = {"c2543fff3bfa6f144c2f06a7de6cd10c0b650cae",
							"b47f363e2b430c0647f14deea3eced9b0ef300ce",
							"e74295bfc2ed0b52d40073e8ebad555100df1380",
							"0f7d0d088b6ea936fb25b477722d734706fe8b40",
							"77cfc481d3e76b543daf39e7f9bf86be2e664959",
							"5cc48a1da13ad8cef1f5fad70ead8362aabc68a1",
							"4bcc3a95bdd9a11b28883290b03086e82af90212",
							"7302ba343c5ef19004df7489794a0adaee68d285",
							"21e7133508c40bbdf2be8a7bdc35b7de0b618ae4",
							"6ef80072f39071d4118a6e7890e209d4dd07e504",
							"02285af8f969dc5c7b12be72fbce858997afe80a",
							"57864da96344366865dd7cade69467d811a7961b"};
		List<String> result = new ArrayList<String>(Arrays.asList(resultset));

	    for(int i = 1; i < 7; i++){
	    	if(i== 1){
	    		database2 = traverse(database,i,database,result);
	    	}
	    	else{
	    		database2 = traverse(database,i,database2,result);
	    	}

//	    	for(int j = 0; j < database2.length;j++){
//	    		Reduce(database2[j]);
//	    	}
	    }



	}


	static String[] traverse(String[] data, int length,String[] data2,List result) throws NoSuchAlgorithmException, UnsupportedEncodingException{


		if(result.size() == 0){
			return data;
		}

		if(length == 1){
//			for(int i = 0; i  < data.length;i++){
////				Reduce(data[i]);
//			}
			return data;
		}

		else if (length < 6){
			String[] temp = new String[(int) Math.pow(data.length, length)];
			int arrayindx = 0;
			for(int i = 0; i < data.length;i++){
				for(int j = 0; j < data2.length;j++){
					String s = data[i].toString() + data2[j].toString();

					Lab4 lb4 = new Lab4();
					String s2 = lb4.SHA1(s);

					for(int k = 0; k < result.size();k++){
						if(result.get(k).equals(s2)){
							long endTime = System.currentTimeMillis();
							System.out.println( (endTime - startTime)/1000L + " second : "+ result.get(k));
							System.out.println("Password : " + s);
							result.remove(k);
						}
					}
//					Reduce(s);
				//	System.out.println("String combine ne: " + s);
					temp[arrayindx] = s;
					arrayindx++;
				}
			}
			return temp;
		}
		else{
			for(int i = 0; i < data.length;i++){
				for(int j = 0; j < data2.length;j++){
					String s = data[i].toString() + data2[j].toString();
					Lab4 lb4 = new Lab4();
					String s2 = lb4.SHA1(s);

					for(int k = 0; k < result.size();k++){
						if(result.get(k).equals(s2)){
							long endTime = System.currentTimeMillis();
							System.out.println("It takes " + (endTime - startTime)/1000L + " second to find: "+ result.get(k));
							System.out.println("Password is: " + s);
							result.remove(k);
						}
					}
				//	System.out.println("String combine ne: " + s);

				}
			}
			return data;
		}
	}



	static void Reduce(String pwd) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		System.out.println(pwd);
		long temp = IntHashed(pwd,0);
//		passmap.put(temp, pwd);
	}


	static String checkpass(String s) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		long hashint = IntHashed(s,1);
		return passmap.get(hashint);
	}


	static long IntHashed(String Hs,int mode) throws NoSuchAlgorithmException, UnsupportedEncodingException{

			//mode 0 is encrypt, 1 is decrypt

			Lab4 l4 = new Lab4();
			String hash = "";
			if(mode == 0)
				hash = l4.SHA1(Hs);
			else
				hash = Hs;
			System.out.println("Hashed String: " + hash);
			long result = 0;
			long divider = 2176782371L;
			long multiplier = 55000000L;
			for(int i = 0; i < hash.length();i++){
				char c = hash.charAt(i);
				if(Character.isDigit(c)){
					result = result + (Character.getNumericValue(c) * multiplier);
				}
				else{
					int hextoInt = Integer.parseInt(Character.toString(c), 16);
					result = result + (hextoInt * multiplier);
				}
			}
			result = result % divider;
			return result;
	}

}
