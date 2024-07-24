package main.java.todo;


import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.StorageManager;
import main.java.model.TaskList;

/**
* Represents a {@code ListContainerView} which manages the display and interaction
* with multiple {@code TaskLists}.
* It also contains a section to create a new list.
* 
* @see TaskList
* @see ListBox
* @see TodoController
*/
public class ListContainerView implements Initializable {
	
	/**
     * Nested class for input error messages.
     */
	private class FieldMessages {
		public final static String noIdPromptText = "please insert a name";
		public final static String standardPromptText = "type the list name";
		public final static String nameTakenText = "name already taken";
	}
	
	private TodoController controller;
	private TaskList activeTaskList;
	
	@FXML private HBox todayList;
	@FXML private VBox listContainer;
	@FXML private Button newListButton;
	@FXML private TextField listNameField;
	
	 /**
     * Constructs a {@code ListContainerView} with the specified {@code TodoController}.
     * 
     * @param controller The {@code TodoController} managing this view.
     * @see TodoController
     */
	public ListContainerView(TodoController controller) {
		
		this.controller = controller;
		this.listContainer = new VBox();
	}

	
	/**
     * Initialises the {@code ListContainerView} when it is loaded by getting and displaying
     * current's day {@code Tasks} from {@code StorageManager}.
	 *
	 *@see StorageManager
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		activeTaskList = StorageManager.getTaskListByDate(LocalDate.now().toString());
		
		for(int i = 0; i<StorageManager.getTaskLists().size(); i++) {
			TaskList list = StorageManager.getTaskLists().get(i);
			displayTaskList(list);	
		}
		
	}
	
	/**
     * Displays the tasks for today.
     */
	@FXML
	private void displayTodayTasks() {
		
		activeTaskList = StorageManager.getTaskListByDate(LocalDate.now().toString());
		controller.displayTodayList();
	}
	
	
	/**
     * Adds a new task list based on user input from the {@code listNameField}, 
     * if no input errors are detected.
     */
	@FXML
	private void addNewList() {
		String newListName = listNameField.getText();
		listNameField.clear();
		//if the same name exists --> notify

		if(StorageManager.doesListExist(newListName)) {
			listNameField.setPromptText(FieldMessages.nameTakenText);
			return;
		} else if(newListName.isBlank()) {
			listNameField.setPromptText(FieldMessages.noIdPromptText);
			return;
		}
		
		listNameField.setPromptText(FieldMessages.standardPromptText);
		
		TaskList newList = new TaskList(newListName);
		StorageManager.addTaskList(newListName);
		activeTaskList = newList;
		
		displayTaskList(newList);
		controller.displayList(activeTaskList);
	}

	
	/**
     * Creates the view of a {@code TaskList}.
     * 
     * @param list The TaskList to display.
     * @see TaskList
     * @see ListBox
    */
	private void displayTaskList(TaskList list) {
				
		ListBox newTaskBox = new ListBox(list, this);
		HBox listBox = newTaskBox.getListBox();
		System.out.println("DEBUG - displayTodayTask -> ActiveList =  " + activeTaskList.getId());

		listContainer.getChildren().add(listBox);
	}	
	
	/**
     * Handles the event when a list box is clicked.
     * 
     * @param list The {@code TaskList} that was clicked.
     * @see TaskList
     */
	public void handleListBoxClick(TaskList list) {
		activeTaskList = StorageManager.getTaskListByName(list.getId());
		controller.displayList(activeTaskList);
	}
	
	/**
     * Removes the HBox representing the list at the index passed as parameter.
     * 
     * @param list The {@code TaskList} to remove.
     * @return 1 when the list is removed.
     * @see TaskList
     */
	public int removeList(TaskList list) {
		
		controller.removeTask(list);
		listContainer.getChildren().clear();
		displayLists();
		displayTodayTasks();
		
		return 1;
		
	}
	
	/**
     * Displays all task lists in the DB by calling the specific {@code StorageManager} method.
     */
	private void displayLists() {
		
		for(TaskList list:StorageManager.getTaskLists()) {
			displayTaskList(list);
		}
	}
	
}
