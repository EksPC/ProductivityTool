package main.java.todo;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.HBox;
import main.java.StorageManager;
import main.java.model.TaskList;

/**This class builds a {@code TaskList} button, showed on the ToDo section's left side.
 * Every time the user clicks the {@code ListBox} button, the list of {@code Tasks} is displayed.
 * The user can delete a list by left-clicking the {@code ListBox} and clicking the delete icon.
 * All the buttons are displayed in a {@code VBox} container;
 * 
 * @see TaskList
 * @see Task
 * @see VBox*/
public class ListBox {

	@FXML private Label listName;
	@FXML private ImageView deleteButton;

	private TaskList list;
	private ListContainerView container;
	
	private String binImage = "/pics/trash-bin.png";
	
	/**
	 * Class constructor. It takes a {@code TaskList}, the list to display, and a {@code ListContainerView}, the 
	 * lists container controller class.
	 * 
	 * @param list      The TaskList associated with this ListBox.
	 * @param container The ListContainerView that holds this ListBox.
	 */
	public ListBox(TaskList list, ListContainerView container) {
		this.container = container;
		this.list = list;
	}

	/**
	 * Creates and returns an {@code HBox} representing the {@code ListBox}. 
	 * 
	 * @return An HBox containing the list name label and delete button.
	 */
	public HBox getListBox() {
		
		HBox listBox = new HBox();
		listBox.setSpacing(10);
		listBox.alignmentProperty().set(Pos.CENTER_LEFT);
		
		Label nameLabel = buildNameLabel();
		ImageView deleteButton = buildDeleteButton();
		
		listBox.addEventHandler(MouseDragEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
				deleteButton.setVisible(true);
				event.consume();
            } else {
            	deleteButton.setVisible(false);
            }
        });
		
		nameLabel.setOnMouseClicked(event -> {
			
			container.handleListBoxClick(list);
		});
		
		
		listBox.getStyleClass().add("list-button");
		listBox.getChildren().add(nameLabel);
		listBox.getChildren().add(deleteButton);
		
		
		
		return listBox;
	}

	/**
	 * Builds and returns a label containing the name of the list.
	 * 
	 * @return A Label with the list name.
	 */
	private Label buildNameLabel() {
		Label nameLabel = new Label(list.getId());
		nameLabel.alignmentProperty().set(Pos.CENTER_LEFT);
		nameLabel.getStyleClass().add("list-name");
		return nameLabel;
		
	}
	
	/**
	 * Builds and returns an {@code ImageView} representing the delete button.
	 * This element is visible only after a right-click on the {@code ListBox}
	 * 
	 * @return An ImageView configured as a delete button.
	 * 
	 * @see ImageView
	 */
	private ImageView buildDeleteButton() {
		ImageView deleteButton = new ImageView(binImage);
		deleteButton.setFitHeight(40);
		deleteButton.setFitWidth(40);
		deleteButton.setStyle("-fx-padding: 25 0 25 0;");
		deleteButton.setStyle("-fx-margin: 0 10 0 0;");
		deleteButton.setVisible(false);
		
		deleteButton.setOnMouseClicked(event -> {
			
			StorageManager.deleteList(list);
			container.removeList(list);
			deleteButton.setVisible(false);
		});
		
		return deleteButton;
		
	}
	
	
}


