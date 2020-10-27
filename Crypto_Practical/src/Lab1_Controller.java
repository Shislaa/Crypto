import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Lab1_Controller {

	@FXML
	private TextField txt1;
	@FXML
	private TextField txt2;
	@FXML
		TextArea area1;
		Button vf1;
		Button vf2;
		Button Clear;



		public void vf1_Controller(ActionEvent event){
			int[] ISBM = new int[16];
			String s;
			s = txt1.getText();
			int total = 0;
			int Indextemp = 0;
			for(int i = 0; i < s.length();i++){
				if(Character.isDigit(s.charAt(i)) || s.charAt(i) == 'X'){
					if(s.charAt(i) == 'X'){
						ISBM[Indextemp] = 10;
					}
					else
					ISBM[Indextemp] = Integer.parseInt(String.valueOf(s.charAt(i)));

					if(Indextemp < 10)
					total = total + (Indextemp + 1)*ISBM[Indextemp];
					Indextemp++;
				}
				System.out.println("Value of ISBM ["+ (Indextemp - 1) +"] = " + ISBM[Indextemp - 1]);
				}
			System.out.println("Total value: " + total);
			total = total % 11;
			if(total == 0){
				area1.setText(s + " is a valid ISBM number");
			}
			else{
				area1.setText(s+ " is not a valid ISBM number");
			}


		}

		public void vf2_Controller(ActionEvent event){
			int[] CCnum = new int[16];
			String s;
			s = txt2.getText();
			int index = 0;
			int total = 0;
			//
			for(int i = 0; i < s.length();i++){
				if(Character.isDigit(s.charAt(i))){
					CCnum[index] = Integer.parseInt(String.valueOf(s.charAt(i)));
					if(index % 2 == 0){
						int tempura = CCnum[index] * 2;
						if (tempura >= 10){
							CCnum[index] = tempura - 9;
						}
						else{
							CCnum[index] = tempura;
						}
					}
				total = total + CCnum[index];
				index++;
				System.out.println(index);
				}
			}
			System.out.println("Total ne: "+ total);
			//
			if (total % 10 == 0){
				area1.setText(s+" is a valid CC number");
			}
			else{
				area1.setText(s+" is not a valid CC number");
			}
		}
		public void Clear_Controller(ActionEvent event){
			txt1.clear();
			txt2.clear();
			area1.clear();
		}


}

