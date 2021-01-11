import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DeC_Controller {
	@FXML
	Button DeC = new Button();
	@FXML
	Button GTE = new Button();
	@FXML
	TextArea EnC_Msg = new TextArea();
	@FXML
	TextArea Msg1_Txt = new TextArea();
	@FXML
	TextArea Msg2_Txt = new TextArea();

	@FXML
	Button OK_ND = new Button();

	public void DeC_Controller(ActionEvent event) throws UnsupportedEncodingException{
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		EnCrypt_Controller EnC_Con = loader.getController();
		if(EnC_Con.getMp().isEmpty()){

			try {
				Pane root = loader.load(getClass().getResource("DeC_No_Data.fxml").openStream());
				Scene scene = new Scene(root);
				primaryStage.setTitle("E&D App");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			String EnStr = EnC_Msg.getText();
			if(!EnStr.isEmpty()){
				if(EnStr.charAt(0) != '['){
					if( Decrypt1(EnStr) == null){
						try {
							Pane root = loader.load(getClass().getResource("DeC_Not_Found.fxml").openStream());
							Scene scene = new Scene(root);
							primaryStage.setTitle("E&D App");
							primaryStage.setScene(scene);
							primaryStage.show();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else{
						ArrayList<String> Result = Decrypt1(EnStr);
						Msg1_Txt.setText(Result.get(0));
						Msg2_Txt.setText(Result.get(1));
					}
				}
				else{
					if( Decrypt_2(EnStr) == null){
						try {
							Pane root = loader.load(getClass().getResource("DeC_Not_Found.fxml").openStream());
							Scene scene = new Scene(root);
							primaryStage.setTitle("E&D App");
							primaryStage.setScene(scene);
							primaryStage.show();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else{
						ArrayList<String> Result = Decrypt_2(EnStr);
						Msg1_Txt.setText(Result.get(0));
						Msg2_Txt.setText(Result.get(1));
					}
				}
			}
		}
	}

	public void GTE_Controller(ActionEvent event){
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		try {
			Pane root = loader.load(getClass().getResource("EnCrypt_GUI.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("E&D App");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void OK_ND_Controller(ActionEvent event){
		((Node)event.getSource()).getScene().getWindow().hide();
	}

	public static ArrayList<String> Decrypt1(String EnStr) throws UnsupportedEncodingException{
		FXMLLoader loader = new FXMLLoader();
		EnCrypt_Controller EnC_Con = loader.getController();
		int index = EnStr.length() - 1;
		int num = 0;
		int passlength = 0;
		StringBuilder encrMsg2 = new StringBuilder();
		List<Integer> numList = new ArrayList<>();

		while(EnStr.charAt(index) != '|' && index > 0){
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

		String msg2 = " ";
		if(EnC_Con.getMp().containsKey(encrMsg2.toString())){
			String key = EnC_Con.getMp().get(encrMsg2.toString());
			msg2 = XorString(encrMsg2.toString(), key);
			System.out.println("Original Message 1: " + EnStr);
			System.out.println("Original Message 2: " + msg2);
			ArrayList<String> temp = new ArrayList<>();
			temp.add(EnStr);
			temp.add(msg2);
			return temp;
		}

		return null;

	}

	public static ArrayList<String> Decrypt_2(String EnStr) throws UnsupportedEncodingException{
		FXMLLoader loader = new FXMLLoader();
		EnCrypt_Controller EnC_Con = loader.getController();
		List<Integer> numList = new ArrayList<>();

		int index = 0;
		int temp = 0;
		int passlen = 0;
		int m2len = 0;
		StringBuilder sb = new StringBuilder();
		int valTemp2 = 0;

		while(EnStr.charAt(index) != ']'){
			if(EnStr.charAt(index) != '['){
				valTemp2 = (10 * valTemp2) + Character.getNumericValue(EnStr.charAt(index)) ;
			}
			index++;
		}
		passlen = valTemp2;
		index = 0;

		System.out.println("PassLen DeC_2: " + passlen);

		for(int i = 0 ; i < passlen; i ++){
			int valTemp = 0;
			while(EnStr.charAt(index) != ']'){
				if(EnStr.charAt(index) != '['){
					valTemp = (10 * valTemp) + Character.getNumericValue(EnStr.charAt(index)) ;
				}
				index++;
			}
			index++;
			if(i > 0){
				numList.add(valTemp);
				System.out.println("Mul Value in DeC_2 " + valTemp);
			}
		}



		m2len = index;

		for(int i = 0 ; i < numList.size();i++){
			char[] c = Character.toChars(numList.get(i));
			sb.append(c);
		}
		System.out.println("EnCrt Msg in Decrypt: " + sb.toString());

		String msg2 = " ";
		EnStr = EnStr.substring(m2len);
		if(EnC_Con.getMp().containsKey(sb.toString())){
			String key = EnC_Con.getMp().get(sb.toString());
			msg2 = XorString(sb.toString(), key);
			System.out.println("Original Message 1: " + EnStr);
			System.out.println("Original Message 2: " + sb.toString());
			ArrayList<String> temp2 = new ArrayList<>();
			temp2.add(EnStr);
			temp2.add(msg2);
			return temp2;
		}
		return null;
	}

	public static String fromHex(String hex) throws UnsupportedEncodingException {
	    hex = hex.replaceAll("^(00)+", "");
	    byte[] bytes = new byte[hex.length() / 2];
	    for (int i = 0; i < hex.length(); i += 2) {
	        bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
	    }
	    return new String(bytes);
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
}
