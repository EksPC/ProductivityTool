package main.java.todo;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.StorageManager;
import main.java.calendar.CalendarDayView;
import main.java.model.Task;
import main.java.model.TaskList;

/**
 * FXML element that represents a {@code Task} with all its informations: 
 * Name, expiring date (if present) and belonging {@code TaskList}.\
 * Every {@code TaskBox} can be checked or unchecked; This event will change the corresponding {@cod Task} "completed" field.
 * Depending on the constructor used, it can be integrated with either a {@code TaskListView} or {@code CalendarDayView}, based
 * on witch section the user is visualising (To Do or Calendar respectively).
 * 
 * @see Task
 * @see TaskListView
 * @see CalendarDayView
 * @see TaskList
 */
public class TaskBox {

	private Task task;
	private TaskListView view;
	private CalendarDayView calendarView;
	
	private int linkedClassCode;
	
	private String binImage = "/pics/trash-bin.png";

	/**
     * Constructs a {@code TaskBox} based on the given {@code Task} that will be displayed in a {@code TaskListView}.
     * 
     * @param view The TaskListView that contains this TaskBox.
     * @param currentTask The Task to be displayed in this TaskBox.
     * 
     * @see Task
 	 * @see TaskListView
     */
	public TaskBox(TaskListView view, Task currentTask) {
		this.view = view;
		this.task = currentTask;
		linkedClassCode = 0;
	}
	
	
	  /**
     * Constructs a {@code TaskBox} based on the given {@code Task} that will be displayed in a {@code CalendarDayView}.
     * 
     * @param calendarView The {@code CalendarDayView} that contains this {@code TaskBox}.
     * @param currentTask The {@code Task} to be displayed in this {@code TaskBox}.
     * 
     * @see CalendarDayView
     * @see Task
     */
	public TaskBox(CalendarDayView calendarView, Task currentTask) {
		this.calendarView = calendarView;
		this.task = currentTask;
		linkedClassCode = 1;
	}
	
	
	/**
     * Builds and returns an {@code HBox} representing the {@code Task}.
     * 
     * @param task The {@code Task} to be displayed.
     * @return An {@code HBox} containing the visual representation of the {@code Task}.
     * @see HBox
     * @see Task
     */
	public HBox buildTask(Task task) {
		
		HBox mainHBox = buildMainBox();
        VBox clickableBox = buildClickableBox();
        CheckBox checkBox = buildCheckBox(task);
        HBox dataBox = buildDataBox();
        Label listNameLabel = new Label(task.getListName());
		ImageView deleteButton = buildDeleteButton();
		
        listNameLabel.setPrefHeight(30.0);
        listNameLabel.setPrefWidth(250.0);
        
        
        checkBox.addEventHandler(MouseDragEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
            	System.out.println("left clicked");
				deleteButton.setVisible(true);
				event.consume();
            } else {
            	deleteButton.setVisible(false);
            }
        });

        Label dateLabel = new Label();
        if(task.doesExpire()) {
        	dateLabel.setText(task.getExpiration());
        }
        
        dateLabel.setPrefHeight(30.0);
        dateLabel.setPrefWidth(250.0);
        dataBox.getChildren().addAll(listNameLabel, dateLabel);
        clickableBox.getChildren().addAll(checkBox, dataBox);
        mainHBox.getChildren().addAll(clickableBox, deleteButton);
        
        return mainHBox;
	}
	
	
	/**
     * Builds and returns an {@code HBox} for displaying task data, with the belonging {@code TaskList} name and 
     * the expiration date.
     * 
     * @return An HBox configured for displaying task data.
     * @see HBox
     */
	private HBox buildDataBox() {
		HBox dataBox = new HBox();
		dataBox.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        dataBox.setPrefHeight(30.0);
        dataBox.setPrefWidth(910.0);
        dataBox.setStyle("-fx-background-color: #EF8257;");
		return dataBox;
	}
	
	/**
     * Builds and returns a {@code VBox} for clickable areas.
     * 
     * @return A VBox configured for clickable areas.
     * @see VBox
     */
	private VBox buildClickableBox() {
		VBox clickableBox = new VBox();
        clickableBox.setPrefHeight(80.0);
        clickableBox.setPrefWidth(980.0);
        return clickableBox;
	}


	/**
     * Builds and returns the main {@code HBox} for the {@code TaskBox}.
     * 
     * @return The main HBox configured for the TaskBox.
     * @see HBox
     */
	private HBox buildMainBox() {
		HBox mainHBox = new HBox();
		
		mainHBox.getStyleClass().add("task");
		
		mainHBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        mainHBox.setLayoutX(90.0);
        mainHBox.setLayoutY(325.0);
        mainHBox.setPrefHeight(80.0);
        mainHBox.setPrefWidth(900.0);
        mainHBox.setStyle("-fx-background-color: white;");
        return mainHBox;
	}
	
	/**
    * Builds and returns a {@code CheckBox} for the given {@code Task}.
    * It adds an event handler that modifies the corresponding {@code Task} state in the DB
    * by invoking a particular {@code StorageManager} method.
    *  
    * @param task The {@code Task} to associate with the {@code CheckBox}.
    * @return A {@code CheckBox} configured for the {@code Task}.
    * @see CheckBox
    * @see Task
    * @see StorageManager
    */
	private CheckBox buildCheckBox(Task task) {
		System.out.println("DEBUG - TaskBox: name = "+task.getName());
		CheckBox checkBox = new CheckBox(task.getName());
		
		if(task.isCompleted()) {
			checkBox.setSelected(true);
		}
		
        checkBox.setMnemonicParsing(false);
        checkBox.setPrefHeight(50.0);
        checkBox.setPrefWidth(910.0);
        checkBox.setStyle("-fx-background-color: white;");
        checkBox.setPadding(new Insets(0, 0, 0, 10.0));
        
        checkBox.setOnAction(event -> {
//        	task.setCompleted(!task.isCompleted());
        	StorageManager.switchTaskState(task);
        	
        });
        return checkBox;
	}
	
	
	/**
     * Builds and returns an {@code ImageView} for the delete button.
     * The buttons handler calls {@code StorageManager} delete task function.
     * 
     * @return An {@code ImageView} configured for the delete button.
     * @see ImageView
     * @see StorageManager
     */
	private ImageView buildDeleteButton() {
		ImageView deleteButton = new ImageView(binImage);
		deleteButton.setFitHeight(50);
		deleteButton.setFitWidth(50);
		deleteButton.setStyle("-fx-padding: 25 0 25 0;");
		deleteButton.setStyle("-fx-margin: 0 10 0 0;");
		deleteButton.setVisible(false);
		
		if(linkedClassCode == 0) {
			deleteButton.setOnMouseClicked(event -> {
				
				StorageManager.deleteTask(task);
				view.removeTask(task);
				
			});
			
		} else if(linkedClassCode == 1) {
			
			deleteButton.setOnMouseClicked(event -> {
				
				StorageManager.deleteTask(task);
				calendarView.removeTask(task);
				
			});
		}
		
		return deleteButton;
		
	}
}
