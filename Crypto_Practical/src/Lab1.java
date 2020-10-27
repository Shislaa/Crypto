import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Lab1 extends Application{

	public void start(Stage primaryStage) {
		try {
			// [[ Initialize Login pane ]]
			Parent pane = FXMLLoader.load(getClass().getResource("Lab1 GUI.fxml"));
			Scene scene = new Scene(pane);
			primaryStage.setTitle("Lab 1");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
