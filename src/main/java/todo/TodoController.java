package main.java.todo;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import main.java.MainController;
import main.java.model.TaskList;

/**
 * Controller class for managing the ToDo application. It handles the interaction
 * between the {@code TaskListView} and {@code ListContainerView}.
 * 
 * @see TaskListView
 * @see ListContainerView
 * @see MainController
 */
public class TodoController {
	
	private TaskListView listView;
	private ListContainerView containerView;
	private String[] paths = {"/fxml/todoList.fxml",
							  "/fxml/listContainer.fxml"};
	
	
	
	/**
     * Constructs a {@code TodoController}.
     * 
     * @see MainController
     */
	public TodoController() {
		this.listView = new TaskListView(this);
		this.containerView = new ListContainerView(this);
	}
	

    /**
     * Returns the user interface as an {@code HBox} containing the task list and container views.
     * 
     * @return The {@code HBox} containing the user interface.
     */
	public HBox getUserInterface() {
		FXMLLoader listLoader = new FXMLLoader(getClass().getResource(paths[0]));
		FXMLLoader containerLoader = new FXMLLoader(getClass().getResource(paths[1]));
		listLoader.setController(listView);
		containerLoader.setController(containerView);
		
		HBox tmpBox = buildContainer();	
				
		try {
			tmpBox.getChildren().add(containerLoader.load());
			tmpBox.getChildren().add(listLoader.load());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return tmpBox;
	}
	
	/**
     * Builds and returns the container {@code HBox} for the todo section.
     * 
     * @return The {@code HBox} for the todo section.
     */
	private HBox buildContainer() {
		
		HBox todoSection = new HBox();
		todoSection.setPrefWidth(1540);
		todoSection.setPrefHeight(800);
		todoSection.setAlignment(Pos.CENTER);
		todoSection.setSpacing(30);
		todoSection.setPadding(new Insets(0,0,0,0));
		
		return todoSection;
	}
	
	/**
     * Displays the specified {@code TaskList} in the {@code TaskListView}.
     * 
     * @param list The {@code TaskList} to display.
     * @see TaskList
     * @see TaskListView
     */
	public void displayList(TaskList list) {
		listView.displayTaskList(list);
	}
	
	/**
     * Displays today's task list in the {@code TaskListView}.
     * 
     * @see TaskListView
     */
	public void displayTodayList() {
		listView.displayTodayList();
	}
	
	/**
     * Removes the specified {@code TaskList} from the view.
     * 
     * @param list The {@code TaskList} to remove.
     * @see TaskList
     * @see TaskListView
     */
	public void removeTask(TaskList list) {
		if(listView.getCurrentList().getId().equals(list.getId())) {
			listView.clearTasksField();
			listView.displayTodayList();
		}
	}

}

