package main.java;

import java.io.IOException;
import main.java.calendar.CalendarController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import main.java.todo.TodoController;

/**
 * Main controller for the application, handling the interaction between
 * {@code AppController}, {@code CalendarController}, and {@code TodoController}.
 * This class manages the main application view and handles page switching.
 * 
 * @see AppController
 * @see CalendarController
 * @see TodoController
 * @see StorageManager
 */
public class MainController {
	
	private AppController appController;
	private CalendarController calendarController;
	private TodoController todoController;
	
	private int pageNumber;
	private final String applicationPath = "/fxml/applicationView.fxml";

	private BorderPane root;
	
	/**
     * Class constructor. Initializes the three controllers it handles:
     * {@code AppController}, {@code CalendarController}, and {@code TodoController}.
     * It also initializes {@code pageNumber} to the value 1 (calendarPage) and
     * the {@code StorageManager}.
     * 
     * @see AppController
     * @see CalendarController
     * @see TodoController
     * @see StorageManager
     */
	public MainController() {
		StorageManager.initialise();
		todoController = new TodoController();
		calendarController = new CalendarController();
		appController = new AppController(this);
		pageNumber = 1;
	}	
		
	

	/**This method assembles and returns the application view as a {@code BorderPane}.
	 * @return The {@code BorderPane} containing the application view.
	 * @see BorderPane*/
	public BorderPane getApplicationView() {
		
		FXMLLoader appLoader = new FXMLLoader(getClass().getResource(applicationPath));
		appLoader.setController(appController);
		root = new BorderPane();
		
		try {
			root = appLoader.load();
		} catch(IOException e) {
			System.out.println("ROOT LOADING ERROR:");
			e.printStackTrace();
		}
		
		root.setCenter(calendarController.getUserInterface());
		
		return root;
	}
	
	/**This method changes page based on the input code
	 * and it is triggered by the two buttons controlled by AppController.
	 * 
	 * @param code 1 for calendar, 2 for TODO list*/
	public void changePage(int code) {
			
		if(code == 1) {
			if(pageNumber != 1) {
				root.setCenter(calendarController.getUserInterface());
				pageNumber = 1;
				System.out.println("Pg: " + pageNumber);
			}
			
		}else if(code == 2) {
				
			if(pageNumber != 2) {
				root.setCenter(todoController.getUserInterface());
				pageNumber = 2;
				System.out.println("Pg: " + pageNumber);
			}
		}	
	
	}
	
	
	
	
	
}
