package main.java.calendar;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.java.StorageManager;
import main.java.model.Task;
import main.java.model.TaskList;
import main.java.model.TasksStorage;
import main.java.todo.TaskBox;

/**This class represents the controller of the day view for the calendar section:
 * It displays the tasks of a certain date and offers the possibility to add new tasks in the current date by
 * selecting an existing {@code TaskList}.
 * 
 * It implements {@code Initializable} because it has some {@code FXML} fields.
 * 
 * @see Initializable
 * @see FXML
 * @see TaskList
 * */
public class CalendarDayView implements Initializable{
	
	@FXML VBox tasksField;
	@FXML Button newTaskButton;
	@FXML TextField nameField;
	@FXML SplitMenuButton listSelector;
	
	private boolean newTaskFlag;
	private String selectedListName;
	private String currentDate;
	private final String noNamePromptText = "please type in a task";
	
	/**Class constructor. It initialises multiple fields with their initial values.*/
	public CalendarDayView() {
		// TODO Auto-generated constructor stub
		listSelector = new SplitMenuButton();
		newTaskFlag = true;
	}
	
	
	@Override
	/**This is the initialise method, it calls the {@code fillListSelector} method.*/
	public void initialize(URL arg0, ResourceBundle arg1) {
		fillListSelector();
	}
	
	/**This method displays the all the tasks of the input date, by calling the {@code TaskBox.buildTask} method.
	 * @param date Date of the tasks to display (as string)*/
	public void displayDate(String date) {
		currentDate = date;
		
		tasksField.getChildren().clear();
		for(Task task:StorageManager.getTaskListByDate(date).getTasks()) {
			TaskBox box = new TaskBox(this,task);
			tasksField.getChildren().add(box.buildTask(task));
		}
	}

	@FXML 
	/**This is the handler of the addButton field and it adds a new {@code Task} to the {@code TasksStorage}. 
	 * The new {@code Task} is build by taking it's name from the {@code TextField} nameField and it's list
	 * name from the {@code SplitMenuButton} listSelector.
	 * 
	 * @see Task
	 * @see TasksStorage
	 * @see TextField
	 * @see SplitMenuButton*/
	private void addTask() {
		//take the text
		if(!newTaskFlag) {
			nameField.setPromptText("select a list or create one");
			return;
		}
		
		String newTaskName = readInputName();
		
		try {
			newTaskName.equals(null);
		} catch(NullPointerException e) {
			nameField.setPromptText(noNamePromptText);
			return;
		}
		Task newTask = new Task(newTaskName,currentDate);
		newTask.setListName(selectedListName);
		
		StorageManager.addTaskToList(newTask);
		displayDate(currentDate);
		
	}

	/**This method reads the input name from the {@code TextField} nameField and returns it as a String;
	 * @return String new task name
	 * 
	 * @see TextField*/
	private String readInputName() {
		String newTaskName = nameField.getText();
		nameField.clear();
		if(newTaskName.equals("")) {
			newTaskName = null;
		}
		return newTaskName;
	}
	
	/**This method fills the {@code SplitMenuButton} listSelector by adding the available {@code TaskLists}.
	 * @see SplitMenuButton
	 * @see TaskList*/
	private void fillListSelector() {
	   
		listSelector.getItems().clear();
		
		try {
			listSelector.setText(StorageManager.getTaskLists().getFirst().getId());
			selectedListName = StorageManager.getTaskLists().getFirst().getId();
			
		} catch(NoSuchElementException e) {
			
			listSelector.setText("");
			newTaskFlag = false;
			return;
		}
		
		newTaskFlag = true;
	    List<MenuItem> items = new ArrayList<MenuItem>();
	    
	    for (TaskList list : StorageManager.getTaskLists()) {
	        items.add(new MenuItem(list.getId()));
	    }
	    
	    // Add all items from the ArrayList to the listSelector
	    listSelector.getItems().addAll(items);
	}

	/**This method removes the input task from the current day view.
	 * @param task task to remove*/
	public void removeTask(Task task) {
		tasksField.getChildren().remove(new TaskBox(this,task).buildTask(task));	
		tasksField.getChildren().clear();
		displayDate(currentDate);
	}

}

