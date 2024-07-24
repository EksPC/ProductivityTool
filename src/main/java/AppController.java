package main.java;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**This class is the main screen controller and it handles left bar buttons clicks, needed for
 * page changing.*/
public class AppController implements Initializable{
 
	@FXML private Pane page;
	@FXML private ImageView todoButton;
	@FXML private ImageView calendarButton;
	
	private MainController controller;
	
	/**Simple constructor, take a [{@code MainController} object as parameter and uses it to
	 * initialise the local field {@code controller}.*/
	public AppController(MainController controller) {
		this.controller = controller;
	}
	
	
	
	@Override
	/**Initialise buttons by adding an event handler allowing page changing.*/
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		todoButton.setOnMouseClicked(event -> {
			controller.changePage(2);
		});
		
		
		calendarButton.setOnMouseClicked(event -> {
			controller.changePage(1);
		});
	}
	
	
	/**This method sets the page centre based on the input {@code Pane}.
	 * @see Pane*/
	public void setCenter(Pane center) {
		page = center;
	}
}
