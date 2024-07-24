package main.java.todo;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import main.java.StorageManager;
import main.java.model.Task;
import main.java.model.TaskList;

/**Represents the central screen of the ToDo section and it allows the user to visualise and interact with a
 * {@code TaskList's} list of {@code Tasks}.
 * It displays the list of {@code Tasks}, the {@code TaskList} name and a section to add a {@code Task} to the 
 * showed list.
 * 
 * @see Task
 * @see TaskList*/
public class TaskListView implements Initializable{

	@FXML private Label listName;
	@FXML private VBox listBox;
	@FXML private Button newTaskButton;
	@FXML private TextField nameField;
	@FXML private DatePicker dateField;
	
	private final String mainPromptText = "type in a task";
	private final String noNamePromptText = "please type in a task";

	private TodoController controller;
	private TaskList currentList;
	
	  /**
     * Constructs a {@code TaskListView} with the specified {@code TodoController}.
     * 
     * @param controller The TodoController managing this view.
     * @see TodoController
     */
	public TaskListView(TodoController controller) {
		this.controller = controller;
		this.currentList = StorageManager.getTaskListByDate(LocalDate.now().toString());
	}
	
	/**
     * Initialises the {@code TaskListView} when it is loaded by displaying a list of {@code Tasks}; specifically, 
     * the tasks expiring in the current date.
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		displayTodayList();
		nameField.setOnKeyPressed(event -> keyPressedHandler(event));
	}
	
	  /**
     * Handles key press events for the {@code nameField}.
     * If the ENTER key is pressed, a new task is added (if inserted).
     * 
     * @param event The {@code KeyEvent} triggered by a key press.
     * @see KeyEvent
     */
	public void keyPressedHandler(KeyEvent event) {
		if(event.getCode().equals(KeyCode.ENTER)) {
			addTask();
		}
	}
	
	 /**
     * Adds a new task based on user input from the {@code nameField} and {@code dateField}.
     */
	@FXML
	private void addTask() {
		
		String newTaskName = readInputName();
		try {
			newTaskName.equals(null);
		} catch(NullPointerException n) {
			nameField.setPromptText(noNamePromptText);
			return;
		}
		
		String newTaskDate = readInputDate();
	
		Task newTask = new Task(newTaskName,newTaskDate);
		newTask.setListName(currentList.getId());
		
		TaskBox newTaskBox = new TaskBox(this, newTask);
		
		StorageManager.addTaskToList(newTask);
		
		listBox.getChildren().add(newTaskBox.buildTask(newTask));
		nameField.setPromptText(mainPromptText);
	}
	
	/**
     * Reads the input from the {@code nameField} and verifies if it is correct.
     * 
     * @return The name of the new task, or {@code null} if the input is empty.
     */
	private String readInputName() {
		String newTaskName = nameField.getText();
		nameField.clear();
		if(newTaskName.equals("")) {
			newTaskName = null;
		}
		return newTaskName;
	}
	
	/**
     * Reads the input from the {@code dateField} and verifies if it is correct.
     * 
     * @return The date of the new task as a {@code String}, or an empty string if no date is selected.
     */
	private String readInputDate() {
		LocalDate date = dateField.getValue();
		String newTaskDate = new String("");
		
		dateField.getEditor().clear();
		dateField.setValue(null);
		
		if(date != null) {
			newTaskDate = date.toString();
		}
		return newTaskDate;
	}
	
	/**
     * Gets the current {@code TaskList} being displayed.
     * 
     * @return The current {@code TaskList}.
     * @see TaskList
     */
	public TaskList getCurrentList() {
		return currentList;
	}
	
	/**
     * Displays the specified {@code TaskList}.
     * 
     * @param list The {@code TaskList} to display.
     * @see TaskList
     */
	public void displayTaskList(TaskList list) {
		
		if(LocalDate.now().toString().equals(list.getId())) {
			list.setId("Today");
		} else {
			nameField.setVisible(true);
			dateField.setVisible(true);
			newTaskButton.setVisible(true);
		}
	
		this.currentList = list;
		listName.setText(list.getId());
				
		clearTasksField();
		
		if(list.isEmpty()) {
			return;
		}
		
		for(Task task:list.getTasks()) {
			TaskBox taskBox = new TaskBox(this,task);
			listBox.getChildren().add(taskBox.buildTask(task));
		}
	}

	/**
     * Clears all tasks from the tasks field.
     */
	public void clearTasksField() {
		while(!listBox.getChildren().isEmpty()) {
			listBox.getChildren().removeLast();
		} 
	}
	
	 /**
     * Removes the specified {@code Task} from the list and updates the view.
     * 
     * @param task The {@code Task} to be removed.
     * @see Task
     */
	public void removeTask(Task task) {
		listBox.getChildren().remove(new TaskBox(this,task).buildTask(task));	
		clearTasksField();
		displayTaskList(currentList);
	}
	
	/**
     * Displays the list of tasks expiring on the current date.
     */
	public void displayTodayList() {

		displayTaskList(StorageManager.getTaskListByDate(LocalDate.now().toString()));
		nameField.setVisible(false);
		dateField.setVisible(false);
		newTaskButton.setVisible(false);
		
	}
	
	
	
	
	
	
	
	
	
}
