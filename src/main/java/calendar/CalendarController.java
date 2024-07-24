package main.java.calendar;

import java.io.IOException;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

/**This class is the calendar's brain. It is responsible for passing the UI to the main method, passing
 * from the MainController. The calendar view contains two main components:
 * The calendar content, which is controlled by a {@code CalendarContentView} object, and a 
 * {@code CalendarNavigator} object, that allows the user to navigate through different months.
 * 
 * @see CalendarContentView
 * @see CalendarNavigatorView*/
public class CalendarController {

	private String[] paths = {"/fxml/calendarNavigator.fxml",
							  "/fxml/calendarContent.fxml"};
	
	private CalendarContentView contentView;
	private CalendarNavigatorView navigatorView;
	private String initialDate;
	
	/**Class constructor. It initialises multiple fields.*/
	public CalendarController() {
		this.initialDate = LocalDate.now().toString();
		this.contentView = new CalendarContentView(initialDate);
		this.navigatorView = new CalendarNavigatorView(this);
	}
	

	/**This method build the UI of the calendar section:
	 * It binds the {@code CalendarDayView} and the {@code CalendarMonthView} to their loaders, then 
	 * loads the resource in the respective fields.
	 * 
	 * @return BorderPane calendar section view*/
    public BorderPane getUserInterface() {
        BorderPane box = new BorderPane();
        box.setStyle("-fx-background-color: rgb(255, 252, 242);");;
        
        FXMLLoader navigatorLoader = new FXMLLoader(getClass().getResource(paths[0]));
        navigatorLoader.setController(navigatorView);
        
        
        FXMLLoader calendarMonthLoader = new FXMLLoader(getClass().getResource(paths[1]));
        calendarMonthLoader.setController(contentView);
        
        try {
            box.setLeft(navigatorLoader.load());
            box.setCenter(calendarMonthLoader.load());
        
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (NullPointerException e) {
        	e.printStackTrace();
        }
        contentView.showMonth(initialDate);
		return box;
    }

    /**This method displays the date passed as a parameter by calling the corresponding method of 
     * {@code CalendarContentView}.
     * @param date Date to display (as a string)*/
	public void displayDate(String date) {
		System.out.println("Requested: " + date);
		contentView.showDay(date);
	
	}

	/**This method displays the input date month by calling the corresponding method of
	 * {@code CalendarContentView};
	 * 
	 * @param date Date containing the month to display.
	 * */
	public void displayMonth(String date) {
		contentView.showMonth(date);		
	}
	
	
	
	

	
	
	
	
	
}
