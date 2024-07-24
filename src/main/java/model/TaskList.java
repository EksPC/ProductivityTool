package main.java.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;



@XmlAccessorType(XmlAccessType.FIELD)
/**This class is the model of the TaskList object.
 * It contains a TaskList ID, an {@code ArrayList} of {@code Tasks} and a completed tasks counter.
 * 
 * @see ArrayList
 * @see Task*/
public class TaskList{

	private String id;
	private ArrayList<Task> tasks;
	private int completedTasks;
	
	/**Dummy constructor. It initialises the ArrayList.*/
	public TaskList() {
		tasks = new ArrayList<Task>();
	}
	
	/**Partial constructor, it initialises the class with an empty {@code ArrayList} and sets 
	 * the name from the input parameter.
	 * @param id Name of the List
	 * @see ArrayList*/
	public TaskList(String id) {
		this.id = id;
		this.tasks = new ArrayList<Task>();
	}
	
	/**Partial constructor */
	public TaskList(ArrayList<Task> tasks, String id) {
		this.id = id;
		this.tasks = tasks;
	}
	
	public void addTask(Task task) {
		this.tasks.add(task);
	}
	
	public void removeTask(Task task) {
		this.tasks.remove(task);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}
	
	public boolean isEmpty() {
		return tasks.isEmpty();
	}
	
	public int getCompletedNumber() {
		completedTasks = 0;
		for(Task task : tasks) {
			if(task.isCompleted()) {
				completedTasks++;
			}
		}
		return completedTasks;
	}

	
}
