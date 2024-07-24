package main.java.calendar;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


/**This class is responsible for the main content view of the Calendar section:
 * It can show the month view with {@code CalendarMonthView} or the day view with {@code CalendarDayView}.
 * It implements {@code Initializable} because it has some {@code FXML} fields.
 * 
 * 
 * @see Initializable
 * @see FXML
 * @see CalendarDayView
 * @see CalendarMonthView*/
public class CalendarContentView implements Initializable{

	@FXML private StackPane mainField;
	@FXML private HBox daysLabel;
	@FXML private Label title;
	@FXML private ImageView previousButton;
	@FXML private ImageView nextButton;
	
	private String currentDate;
	private String monthDate;
	private CalendarMonthView monthCalendar;
	private CalendarDayView dayCalendar;
	
	private GridPane calendarGrid;
	private VBox taskList;
	
	private int displayMode;
	
	private String monthViewPath = "/fxml/calendar.fxml";
	private String dayViewPath = "/fxml/calendarDay.fxml";
	
	
	/**Class constructor. It initialises two fields based on inputs and the others base on their constructors.
	 * @param calendarController CalendarController class
	 * @param date Initial date as a String
	 * 
	 * @see CalendarController*/
	public CalendarContentView(String date) {
		// TODO Auto-generated constructor stub
		this.currentDate = date;
		this.monthDate = LocalDate.parse(date).withDayOfMonth(1).toString();
		this.displayMode = 1;
		this.dayCalendar = new CalendarDayView();
		this.monthCalendar = new CalendarMonthView(this);
		this.calendarGrid = new GridPane();
		this.taskList = new VBox();
	}


	@Override
	/**Class initialiser. It is responsible for binding loaders of day and month view with
	 * their controllers, respectively {@code CalendarDayView} and {@code CalendarMonthView} and loading
	 * them in their correspondent fields.
	 * 
	 * @see CalendarDayView
	 * @see CalendarMonthView*/
	public void initialize(URL arg0, ResourceBundle arg1) {
		FXMLLoader dayLoader = new FXMLLoader(getClass().getResource(dayViewPath));
		dayLoader.setController(dayCalendar);
		
		FXMLLoader monthLoader = new FXMLLoader(getClass().getResource(monthViewPath));
		monthLoader.setController(monthCalendar);
		
		try {
			taskList = dayLoader.load();
			calendarGrid = monthLoader.load();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		previousButton.addEventHandler(MouseDragEvent.MOUSE_CLICKED, event -> {
			displayPrevious();
		});
		
		nextButton.addEventHandler(MouseDragEvent.MOUSE_CLICKED, event -> {
			displayNext();
		});
		
	}
	
	/**This method displays the month view of the input date.
	 * If the input date is equals to a default value {@code LocalDate.MIN} the displayed month value should not change.
	 * @param date Date as a String which the month is displayed.*/
	public void showMonth(String date) {
		
		if(!date.equals(LocalDate.MIN.toString())) {
			monthDate = date;
			currentDate = date;
		}

		title.setText(LocalDate.parse(monthDate).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
		if(!mainField.getChildren().isEmpty()){
			mainField.getChildren().remove(0);
		}
		daysLabel.setVisible(true);
		mainField.getChildren().add(0,monthCalendar.getMonthCalendar(monthDate));
		displayMode = 1;
		
	}
	
	
	/**This method displays the day view of the input date.
	 * 
	 * @param date Date to display (as a string)*/
	public void showDay(String date) {
		
		currentDate = date;
		monthDate = date;
		LocalDate tmpDate = LocalDate.parse(date);
		
		String dayFormat = tmpDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
		String monthFormat = tmpDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
		String newTitle = dayFormat.concat(" "+tmpDate.getDayOfMonth()+ " " + monthFormat);	
		title.setText(newTitle);
		
		mainField.getChildren().remove(0);
		daysLabel.setVisible(false);
		mainField.getChildren().add(0,taskList);
		
		dayCalendar.displayDate(date);
		displayMode = 2;
		
	}
	
	/**This method is the handler function of previousButton field. It displays the next day or month based
	 * on the current view.*/
	private void displayNext() {
		if(displayMode == 1) {
			currentDate = LocalDate.parse(currentDate).plusMonths(1).toString();
			showMonth(currentDate);
		
		} else if(displayMode == 2) {
			currentDate = LocalDate.parse(currentDate).plusDays(1).toString();
			showDay(currentDate);
		}
	}
	
	
	/**This method is the handler function of nextButton field. It displays the previous day or month based
	 * on the current view.*/
	private void displayPrevious() {
		if(displayMode == 1) {
			currentDate = LocalDate.parse(currentDate).minusMonths(1).toString();
			showMonth(currentDate);
		
		} else if(displayMode == 2) {
			currentDate = LocalDate.parse(currentDate).minusDays(1).toString();
			showDay(currentDate);
		}
	}

	
}

