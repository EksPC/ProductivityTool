package main.java.calendar;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.java.StorageManager;
import main.java.model.TaskList;


/**This class is responsible for the calendar month visualisation in the calendars section main field.
 * The main object is a {@code GridPane} that represents a calendar. The main purpose of this class
 * is to build a calendar based on a specified month and display the number of tasks to complete for each day.
 * 
 * @see GridPane*/
public class CalendarMonthView implements Initializable{

	private GridPane calendarMonth;
	private CalendarContentView controller;
	
	/**Class constructor, it takes a {@code CalendarContentView} as input and sets it as the class controller.
	 * @param controller {@link CalendarMonthView}  */
	public CalendarMonthView(CalendarContentView controller) {
		calendarMonth = new GridPane();
		this.controller = controller;
	}
	
	/**Class initialiser, it builds the initial structure of the calendar with the current month.*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		buildStructure(LocalDate.now().toString());
	}
	
	/**This method returns a {@code GridPane} in it's final form by taking a date string as input.
	 * @param date String representing the month to display.*/
	public GridPane getMonthCalendar(String date) {
		
		clearCalendar();
		buildStructure(date);
		
		return calendarMonth;
	}
	
	
	/**This method fill the {@code GridPane} calendar structure build previously with all the information
	 * of the specified date.
	 * @param date String representing the month to display*/
	private void buildStructure(String date) {
		calendarMonth.setGridLinesVisible(true);
		
		LocalDate startingDate = LocalDate.parse(date).withDayOfMonth(1);
		
		//the starting day will be (0,start) in a grid
		int start = startingDate.getDayOfWeek().getValue()-1;
		
		for(int i = 0; i <  startingDate.lengthOfMonth();i++){
			String currentDate = startingDate.plusDays(i).toString();
			TaskList dayList = StorageManager.getTaskListByDate(currentDate);
			
			int tasksNum = dayList.getTasks().size() - dayList.getCompletedNumber();
			VBox box = buildCell(LocalDate.parse(currentDate),tasksNum, currentDate.equals(LocalDate.now().toString()));
			int col = (start+i)/7;
			int row = (start+i)%7;
			
			box.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
	        	System.out.println("Cell clicked");
	        	controller.showDay(currentDate);
			});
			
			calendarMonth.add(box, row, col);
		}

	}
	

	/**This method build a {@code VBox} calendar cell based on three parameters, then returns it.
	 * The parameters are: {@code LocalDate} a 
	 * @param date {@link LocalDate} to extract*/
	private VBox buildCell(LocalDate date, int tasksNum, boolean today) {
	 	VBox root = new VBox();
        root.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        root.setPrefHeight(140.0);
        root.setPrefWidth(140.0);
        root.setSpacing(10.0);
        
        if(today) {
        	root.setStyle("-fx-background-color: rgb(241, 161, 126);");
        }

        Label dayNumLabel = new Label(String.valueOf(date.getDayOfMonth()));
        dayNumLabel.setFont(Font.font("System Bold", 18.0));
        dayNumLabel.setPadding(new Insets(0, 0, 0, 5.0));

        if(tasksNum == 0) {
        	root.getChildren().add(dayNumLabel);
        	return root;
        }
        
        Label tasksNumLabel = new Label(String.valueOf(tasksNum));
        tasksNumLabel.setPrefHeight(60.0);
        tasksNumLabel.setPrefWidth(60.0);
        tasksNumLabel.setAlignment(javafx.geometry.Pos.CENTER);
        tasksNumLabel.setFont(Font.font("System Bold Italic", 36.0));
        tasksNumLabel.setStyle("-fx-border-color: black; -fx-background-color: green;");

        root.getChildren().addAll(dayNumLabel, tasksNumLabel);
        
        return root;
	}
	
	/**This method clear the calendar view from its current content.*/
	private void clearCalendar() {
		Node node = calendarMonth.getChildren().get(0);
	    calendarMonth.getChildren().clear();
	    calendarMonth.getChildren().add(0,node);
	}

	
	
	
	

	
}
