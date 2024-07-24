package main.java.model;

import java.util.HashMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
/**This class stores all the {@code Tasks} using two {@code HashMaps}:
 * One for to do lists storage, one for single day tasks.
 * This class is used to store locally parameters from the storage XML file.
 * @see Task 
 * @see HashMap
 */
public class TasksStorage{

	private HashMap<String,TaskList> todoLists;
	private HashMap<String,TaskList> calendarTasks;
	
	/**Class constructor. It initialises two empty {@code HashMaps}.*/
	public TasksStorage() {
		todoLists = new HashMap<String, TaskList>();
		calendarTasks = new HashMap<String, TaskList>();
		
	}
	
	/**Setter method for to do list {@code HashMap}.*/
	public void setTodoLists(HashMap<String,TaskList> todoLists) {
		this.todoLists = todoLists;
	}
	
	/**Setter method for calendar {@code HashMap}.*/
	public void setCalendarDates(HashMap<String,TaskList> calendarDates) {
		this.calendarTasks = calendarDates;
	}
	
	/**Getter method for to do list {@code HashMap}.*/
	public HashMap<String, TaskList> getTodoLists() {
		return todoLists;
	}
	
	/**Getter method for calendar {@code HashMap}.*/
	public HashMap<String,TaskList> getCalendarTasks(){
		return calendarTasks;
	}

}
