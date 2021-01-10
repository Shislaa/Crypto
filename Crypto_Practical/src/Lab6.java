import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Lab6 extends Application{
	static Random rand = new Random();
	static int P = fx(rand.nextInt());
	static int Q = fx(rand.nextInt());
	static int s = rand.nextInt();
	static Map<String,String> mp = new HashMap<>();

	public void start(Stage primaryStage) {
		try {
			// [[ Initialize Login pane ]]
			Parent pane = FXMLLoader.load(getClass().getResource("Lab6_FP_GUI.fxml"));
			Scene scene = new Scene(pane);
			primaryStage.setTitle("E&D APP");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException, DecoderException {

//		Scanner sc = new Scanner(System.in);
//
//		System.out.println("Input Message that doesnt need encrypted: ");
//		String msg1 = sc.nextLine();
//
//		System.out.println("Input Message to encrypt: ");
//		String msg2 = sc.nextLine();
//
//		Encrypt(msg1, msg2);
//
//		System.out.println("Input Encrypted Message: ");
//		String EnStr = sc.nextLine();
//		Decrypt1(EnStr);
//
////		for(int i = 0; i < 5; i++){
//////			System.out.println(Long.parseLong(PRG(P,Q,s),16));
////			System.out.println("s ne: " + s);
////		}
		launch(args);
	}

	public static void Encrypt(String msg1, String msg2){
		String key = PRG(P,Q,s);
		String enMsg = XorString(msg2, key);
		System.out.println("Secret Msg in EnCryprt: " + enMsg);
		mp.put(enMsg, key);
		List<String> BitsList = new ArrayList<>();
		String enMsgToBit = new BigInteger(enMsg.getBytes()).toString(2);

		for(int i = 0 ; i <= enMsgToBit.length(); i = i + 8){
			String temp = enMsgToBit.substring(i, i+7);
			BitsList.add(temp);
		}
		System.out.print("Message encrypted: ");
		System.out.println(Hide_Your_Msg_1(msg1, BitsList));
	}


	public static void Decrypt1(String EnStr){
		int index = EnStr.length() - 1;
		int num = 0;
		int passlength = 0;
		StringBuilder encrMsg2 = new StringBuilder();
		List<Integer> numList = new ArrayList<>();

		while(EnStr.charAt(index) != '|'){
			if(index != EnStr.length() - 1){
				if(EnStr.charAt(index) == '~'){
					numList.add(num);
					num = 0;
				}
				else{
					num++;
				}
				if(EnStr.charAt(index) == '~' && EnStr.charAt(index - 1) == '~'){
					num = 0;
					numList.add(num);
				}
			}
			index--;
			passlength++;
		}



		for(int i = 0; i < numList.size(); i ++){
			char[] c = Character.toChars(numList.get(i));
			encrMsg2.append(c);
		}

		System.out.println("EnCrt Msg in Decrypt: " + encrMsg2.toString());
		EnStr = EnStr.substring(0,EnStr.length()- 1 -passlength);

		String key = mp.get(encrMsg2.toString());

		String msg2 = XorString(encrMsg2.toString(), key);

		System.out.println("Original Message 1: " + EnStr);
		System.out.println("Original Message 2: " + msg2);


	}

	public static String Hide_Your_Msg_1(String msg1, List<String> BitsList){

		int[] tempArr = new int[BitsList.size()];
		StringBuilder sb = new StringBuilder();

		for(int i = 0 ; i < BitsList.size(); i++){
	    	int temp = Integer.parseInt(BitsList.get(i),2);
	    	tempArr[i] = temp;
	    }
		System.out.println("TempArr in Hide yo mes size: " + tempArr.length );
		sb.append("|");
		sb.append("~");
		for(int i = tempArr.length - 1; i >= 0; i -- ){
			for(int k = 0; k < tempArr[i]; k ++){
				sb.append(" ");
			}
			sb.append("~");
		}


		String result = msg1 + sb.toString();


		return result;
	}

// Xor the msg and the key to create an encrypted String
	public static String XorString(String s1, String key){
		StringBuilder sb = new StringBuilder();

		for(int i = 0 ; i < s1.length();i ++){
			char cs1 = s1.charAt(i);
			char cs2 = key.charAt(i % key.length());
			char Xcs = (char) (cs1 ^ cs2);
			sb.append(Xcs);
		}

		return sb.toString();
	}

	public static String PRG(int P, int Q, int s0){

		int r = fx(s0*P);
		int s1 = fx(r*P);
		s = s1;
		int t = fx(r*Q);


		return Integer.toString(t,16);
	}

	public static String Bit2str(List<String> BitsList ) {
	    StringBuilder sb = new StringBuilder();
	    for(int i =0 ; i < BitsList.size(); i++){
	    	int temp = Integer.parseInt(BitsList.get(i),2);
	    	String strTemp = new Character((char) temp).toString();
	    	sb.append(strTemp);
	    }
	    return sb.toString();
	}


	public static int fx(int x){
		int a = -5;
		int b = 8;
		int result = x^3 + a*x + b;
		return result;
	}

	public static String toHexadecimal(String text) throws UnsupportedEncodingException
	{
	    byte[] myBytes = text.getBytes("UTF-8");

	    return DatatypeConverter.printHexBinary(myBytes);
	}
}
