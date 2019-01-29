package diff.evolution;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserInterface extends Application{
	
	private PopulationManager pm = new PopulationManager();
	
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Differential evolution");
		BorderPane bp = new BorderPane();
		Scene scene = new Scene(bp,1000,250);
	
		HBox hbox = new HBox();
		VBox vbox = new VBox();
		
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setStyle("-fx-background-color: #336699;");
		bp.setBottom(hbox);
		bp.setLeft(vbox);
		
		Button btn = new Button("Begin!");
		hbox.getChildren().add(btn);
		
		ToggleGroup tg = new ToggleGroup();
		RadioButton camelbackchoice = new RadioButton("Camelback-function");
		camelbackchoice.setUserData(new Camelback());
		camelbackchoice.setSelected(true);
		Individual.setFitnessFunction((Function)camelbackchoice.getUserData());
		RadioButton ackleychoice = new RadioButton("Ackley-function");
		ackleychoice.setUserData(new Ackley());
		RadioButton easomchoice = new RadioButton("Easom-function");
		easomchoice.setUserData(new Easom_function());
		camelbackchoice.setToggleGroup(tg);
		ackleychoice.setToggleGroup(tg);
		easomchoice.setToggleGroup(tg);
		
		
		Image functionImage  = new Image("resources/Camelback-function.png");
		ImageView view = new ImageView();
		view.setImage(functionImage);
		bp.setCenter(view);
		
		
		
		/*
		 * When the selected radiobutton changes, 
		 * we switch the fitnessfunction of Individuals 
		 * to the new fitnessfunction indicated by the now selected radiobutton.
		 */
		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			public void changed(ObservableValue<? extends Toggle> ov, 
					Toggle old_toggle, Toggle new_toggle) {
				Individual.setFitnessFunction((Function)tg.getSelectedToggle().getUserData());
				
				//set the shown image based on the selected radiobutton
				view.setImage(new Image("resources/" + ((RadioButton) tg.getSelectedToggle()).getText() + ".png"));
			}
		});
			
		
		Text functiontext = new Text("Choose the function to optimize: ");
		Text firstResultText = new Text();
		Text secondResultText = new Text();
		Text thirdResultText = new Text();
		vbox.getChildren().addAll(functiontext ,camelbackchoice, ackleychoice, easomchoice, firstResultText ,
				secondResultText, thirdResultText);
		
		
		/*
		 * Setting the functionality for the start-button.
		 * Solves the selected optimization problem and shows 
		 * the results in textfields.
		 * 
		 */
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				pm.mainloop();
				firstResultText.setText("Results for the " + ((RadioButton) tg.getSelectedToggle()).getText() + ".");
				secondResultText.setText("The search took " + pm.getGenerationCount() + " generations.");
				thirdResultText.setText(pm.getMostFitIndividual().toString());
			}
		});
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	

}
