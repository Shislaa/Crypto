import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EnCrypt_Controller implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	static Random rand = new Random();
	static int P = fx(rand.nextInt());
	static int Q = fx(rand.nextInt());
	static int s = rand.nextInt();
	static int s_Ori = s;
	static boolean Typ = false;
	static Map<String,String> mp = new HashMap<>();
	@FXML
	Button EnC = new Button();
	@FXML
	Button Del_All = new Button();
	@FXML
	Button To_DeC = new Button();
	@FXML
	Button Cpy_Result = new Button();
	@FXML
	TextArea Msg1_Txt = new TextArea();
	@FXML
	TextArea Msg2_Txt = new TextArea();
	@FXML
	TextArea Result_Txt = new TextArea();
	@FXML
	CheckBox Type_2 = new CheckBox();
	@FXML
	CheckBox Type_1 = new CheckBox();

	@FXML
	Button OK = new Button();

	public void EnC_Controller(ActionEvent event){
		if(Msg1_Txt.getText().isEmpty() || Msg2_Txt.getText().isEmpty()){
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			try {
				Pane root = loader.load(getClass().getResource("Enc_Error_Noti.fxml").openStream());
				Scene scene = new Scene(root);
				primaryStage.setTitle("E&D App");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			String msg1 = Msg1_Txt.getText();
			String msg2 = Msg2_Txt.getText();
			Result_Txt.setText(Encrypt(msg1, msg2));
		}
	}

	public void Del_All_Controller(ActionEvent event){
		Msg1_Txt.clear();
		Msg2_Txt.clear();
		Result_Txt.clear();
	}

	public void OK_Controller(ActionEvent event){
		((Node)event.getSource()).getScene().getWindow().hide();
	}

	public void Copy_Controller(ActionEvent event){
		if(!Result_Txt.getText().isEmpty()){
			String myString = Result_Txt.getText();
			StringSelection stringSelection = new StringSelection(myString);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);

		}
		else{
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			try {
				Pane root = loader.load(getClass().getResource("Enc_Error_Noti.fxml").openStream());
				Scene scene = new Scene(root);
				primaryStage.setTitle("E&D App");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void To_Dec_Controller(ActionEvent event){
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		try {
			Pane root = loader.load(getClass().getResource("DeC_GUI.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("E&D App");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Type_1_Controller(ActionEvent event){
		Type_2.setSelected(false);
		Typ = true;
	}

	public void Type_2_Controller(ActionEvent event){
		Type_1.setSelected(false);
		Typ = false;
	}

	public static Map<String, String> getMp() {
		return mp;
	}

	public static void setMp(Map<String, String> mp) {
		EnCrypt_Controller.mp = mp;
	}

	public static String Encrypt(String msg1, String msg2){
		System.out.println("Ori msg2 to Hex in Encr: " + msg2);
		String key = PRG(P,Q,s);
		String enMsg = XorString(msg2.toString(), key);

		System.out.println("Secret Msg in EnCryprt: " + enMsg);
		mp.put(enMsg, key);
		List<String> BitsList = new ArrayList<>();
		for ( int i = 0 ; i < enMsg.length(); i ++ ){
			char c = enMsg.charAt(i);
			String tempc = Integer.toBinaryString(c);
			BitsList.add(tempc);
		}
		if(Typ == true){
			return Hide_Your_Msg_1(msg1, BitsList);
		}
		else{
			return Hide_Your_Msg_2(msg1, BitsList);
		}
	}

	public static String Hide_Your_Msg_1(String msg1, List<String> BitsList){

		int[] tempArr = new int[BitsList.size()];
		StringBuilder sb = new StringBuilder();

		for(int i = 0 ; i < BitsList.size(); i++){
	    	int temp = Integer.parseInt(BitsList.get(i),2);
	    	tempArr[i] = temp;
	    }
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

	public static String Hide_Your_Msg_2(String msg1, List<String> BitsList){
		int[] tempArr = new int[BitsList.size()];
		StringBuilder sb = new StringBuilder();

		for(int i = 0 ; i < BitsList.size(); i++){
	    	int temp = Integer.parseInt(BitsList.get(i),2);
	    	tempArr[i] = temp;
	    }

		for(int i = 0; i < tempArr.length + 1;i++){
			sb.append("[");
			if(i == 0){
				sb.append(tempArr.length + 1);
			}
			else{
				int newVal = tempArr[i - 1];
				sb.append(newVal);
			}
			sb.append("]");
		}

		String result = sb.toString() + msg1;

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
	public static int fx(int x){
		int a = -5;
		int b = 8;
		int result = x^3 + a*x + b;
		return result;
	}



}
