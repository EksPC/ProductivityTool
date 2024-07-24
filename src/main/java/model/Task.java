package main.java.model;

import java.sql.Date;
import java.time.LocalDate;
import javax.xml.bind.annotation.*;

/**This class represents the model of a Task.
 * It contains a task name, an expiration date string formatted, a completed flag, an expiration flag 
 * and the name of the belonging {@code TaskList}.
 * 
 * @see TaskList*/
@XmlAccessorType(XmlAccessType.FIELD)
public class Task{
	
	private String expiration;
	private String name;
	private String listName;
	private boolean completed;
	private boolean expirationFlag;
	@XmlTransient
	private final String defaultExpirationDate = "7565-03-15";
	
	/**Complete constructor.
	 * @param name name of the new Task
	 * @param expiration Date in string format*/
	public Task(String name,String expiration) {
		this.expiration = expiration;
		this.name = name;
		this.expirationFlag = true;
//		this.completed = completed;
	}
	
	/**Partial constructor.
	 * @param name name of the new Task*/
	public Task(String name) {
		this.name = name;
		this.expirationFlag = false;
		this.expiration = Date.valueOf(LocalDate.MIN).toString();
	}

	/**Getter method for the list name field.
	 * @return String list name of this task*/
	public String getListName() {
		return listName;
	}
	
	/**Setter method for the list name field.
	 * @param listName String list name of this task*/
	public void setListName(String listName) {
		this.listName = listName;
	}
	
	/**Dummy constructor.*/
	public Task() {
	}
	
	/**Getter method for the list name.
	 * @return String name of the task*/
	public String getName() {
		return name;
	}
	
	/**Getter method for the expiration date of this task.
	 * @return expiration date as a string*/
	public String getExpiration() { 
		return expiration;
	}
	
	/**This method returns true if this task expires, false otherwise.
	 * @return Boolean true if the task expires.*/
	public boolean doesExpire() {
		return expirationFlag;
	}
	
	/**This method returns true if this task is completed, false otherwise.
	 * @return Boolean true if the task is completed.*/
	public boolean isCompleted() {
		return completed;
	}
	
	/**Setter method for expiration date of this task based on the parameter.
	 * @param expiration Expiration date as a string.*/
	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}
	
	/**Setter method for task name based on the input parameter.
	 * @param name String */
	public void setName(String name) {
		this.name = name;
	}
	
	/**Setter method for the "completed" parameter.
	 * @param Boolean*/
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	

	

	
	
	
	
}
