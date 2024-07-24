package main.java.calendar;


import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**This class handles the calendar section navigator, a tool to quickly navigate through different months.
 * It allows the user to click on a certain date, change visualisation (month or day) and navigate through months.
 * */
public class CalendarNavigatorView implements Initializable{

	@FXML private GridPane calendarNavigator;
	@FXML private Button monthButton;
	@FXML private Button dayButton;
	@FXML private Button nextMonthButton;
	@FXML private Button prevMonthButton;
	@FXML private Label monthNameField;
	
	private CalendarController controller;
	private LocalDate currentDate;
	private int gridMonth;
	private int gridYear;
	
	/**Class constructor. It initialises different fields, with default values and the {@code CalendarController}
	 * field based on the input parameter.
	 * @param controller {@link CalendarController}*/
	public CalendarNavigatorView(CalendarController controller) {
		this.controller = controller;
		this.currentDate = LocalDate.now();
		this.gridMonth = currentDate.getMonthValue();
		this.gridYear = currentDate.getYear();
	}
	
	/**Class initialiser, it sets up some fields and adds event handlers to active components.*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		setMovementButtons();
		buildCalendarNavigator(currentDate.getMonthValue());
		nextMonthButton.setOnAction(event ->{
			gridMonth++;
			checkYearChanges();
			gridMonth = formatMonthNumbers(gridMonth);
			buildCalendarNavigator(gridMonth);
			setMovementButtons();
		});
		
		
		prevMonthButton.setOnAction(event -> {
			gridMonth--;
			checkYearChanges();
			gridMonth = formatMonthNumbers(gridMonth);
			buildCalendarNavigator(gridMonth);
			setMovementButtons();
		});
		
		monthButton.setOnAction(event->{
			controller.displayMonth(LocalDate.MIN.toString());
		});
		
	}	
	
	/**This method builds the navigator structure starting from a simple {@code GridPane} 
	 * and building each cell with input month days.
	 * For each cell it adds the day number and the click event handler.
	 * @param month integer representing the month to display
	 * @see GridPane*/
	public void buildCalendarNavigator(int month) {

		clearNavigatorGrid();
		int tmpDay = 1;
		int start = LocalDate.of(gridYear,month,tmpDay).getDayOfWeek().getValue()-1;
		
		for(int i = 0; i < LocalDate.of(gridYear, month, tmpDay).lengthOfMonth();i++){
			Button cellButton = new Button(Integer.toString(i+1));
			cellButton.getStyleClass().add("calendar-navigator-button");
			LocalDate currentDate = LocalDate.of(gridYear, month, i+1);
			
			cellButton.setOnMouseClicked(event->{
				controller.displayDate(currentDate.toString());
			});
			
			int col = (start+i)/7;
			int row = (start+i)%7;
			
			calendarNavigator.add(cellButton, row, col);
		}
		
	}
	
	/**This method clears the navigator {@code GridPane}.
	 * 
	 * @see GridPane*/
	private void clearNavigatorGrid() {
		Node node = calendarNavigator.getChildren().get(0);
	    calendarNavigator.getChildren().clear();
	    calendarNavigator.getChildren().add(0,node);
		
	}
	
	/**This method is the "Month" button handler and it displays the monthly calendar of 
	 * the current date displayed by calling the {@code CalendarController} controller method.
	 * @see CalendarController*/
	@FXML
	private void displayMonth() {
		controller.displayMonth(currentDate.toString());
	}
	
	/**This method is the handler of every calendar cell. It displays the selected date 
	 * view calling the {@code CalendarController} controller method.
	 * @see CalendarController.
	 * */
	@FXML
	private void displayDay(){
		controller.displayDate(currentDate.toString());
	}
	
	/**This method changes the label of the previous and next month buttons of the navigator based
	 * on the current month displayed in the {@code GridPane}.
	 * @see GridPane*/
	private void setMovementButtons() {
		monthNameField.setText(Month.of(gridMonth).getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
		nextMonthButton.setText(Month.of(formatMonthNumbers(gridMonth+1)).getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
		prevMonthButton.setText(Month.of(formatMonthNumbers(gridMonth-1)).getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
	}
	
	/**This method formats the month number when changing the month displayed.*/
	private static int formatMonthNumbers(int x) {
		if(x == 0) {
			return 12;
		} else if(x == 13) {
			return 1;
		}
		return x;
	}
	
	/**This method formats the year number when changing the month displayed.*/
	private void checkYearChanges() {
		if(gridMonth == 13) {
			gridYear++;
		} else if(gridMonth == 0) {
			gridYear--;
		}
	}

	
	
	
	
	
}
