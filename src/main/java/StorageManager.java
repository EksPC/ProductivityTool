package main.java;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import main.java.model.Task;
import main.java.model.TaskList;
import main.java.model.TasksStorage;
import main.java.calendar.CalendarDayView;
/**This manages the data storage by:
 * 
 * Reading and writing from and to the DB.
 * Saving locally a copy of the DB, using a {@code TaskStorage} object, and using it to provide data to every module.
 * 
 * @see TasksStorage
 * */
public class StorageManager {
	
	private static String dataFilePath = "resources/xml/data.xml";
	private static TasksStorage storage = new TasksStorage();
	
	
	/**Initialises the StorageManager by reading tasks from DB.*/
	public static void initialise() {
		readTasks();
	}
	
	/**Add a new List called listName both in the {@code TasksStorage} and in the DB.
	 * 
	 * @param listName new list name.
	 * @see TasksStorage*/
	public static void addTaskList(String listName) {
		
		storage.getTodoLists().put(listName, new TaskList(listName));
		saveTasks();
	}
	
	
	/**Returns true whether a list called listName exists in the {@code TasksStorage}
	 * 
	 * @param listName name of the list to find.
	 * @see TasksStorage*/
	public static boolean doesListExist(String listName) {
		return storage.getTodoLists().containsKey(listName);
	}
	
	/**This method returns a {@code TaskList} containing the task expiring on a certain date.
	 * If no task expires on the specified date, an empty {@code TaskList} is returned.
	 * @param date The date as a string
	 * 
	 * @see TaskList*/
 	public static TaskList getTaskListByDate(String date) {
		if(storage.getCalendarTasks().containsKey(date)) {
			System.out.println("StorageManager.getTaskListByDate(): " + storage.getCalendarTasks().get(date).getId());
			return storage.getCalendarTasks().get(date);
		} 
		return new TaskList(date);
	}
	
	
	/**This method adds a {@code Task} on a specific date (as string) via calendar.
	 * @param task The task to add
	 * @param date The date for which to add the task
	 * 
	 * @see CalendarDayView
	 * @see Task
	 * */
	public static void addTaskOnDate(Task task, String date) {
		
		if(!storage.getCalendarTasks().containsKey(date)) {
			
			storage.getCalendarTasks().put(date, new TaskList(date));
		} 
		storage.getCalendarTasks().get(date).addTask(task);
	}
	
	
	/**This method returns the {@code TaskList} that matches the input name.
	 * @param name name of the list to search
	 * @see TaskList*/
	public static TaskList getTaskListByName(String name) {
		System.out.println("Name requested: " + name);
		if(name.equals("Today") || name.equals(LocalDate.now().toString())) {
			return getTaskListByDate(LocalDate.now().toString());
		}
		return storage.getTodoLists().get(name);
	}
	
	
	/**This method returns an ArrayList containing all the {@code TaskLists} available.
	 * @return ArrayList all list available
	 * @see TaskList*/
	public static ArrayList<TaskList> getTaskLists() {
		Collection<TaskList> coll = storage.getTodoLists().values();
		TaskList[] tasks = coll.toArray(new TaskList[0]);
		return new ArrayList<TaskList>(Arrays.asList(tasks));
	}
	
	/**This method add a {@code Task} task to the {@code TaskList} specified in the taskList field.
	 * @param task Task to add
	 * 
	 * @see TaskList
	 * @see Task*/
	public static void addTaskToList(Task task) {
		
		//Updating the storage
		storage.getTodoLists().get(task.getListName()).addTask(task);
		
		if(task.doesExpire()) {
			addTaskOnDate(task, task.getExpiration());
		}
		saveTasks();
	}
	
	
	/**This method returns true if the {@code TasksStorage} is empty, false otherwise.
	 * @return True if the storage is empty, False otherwise
	 * @see TasksStorage*/
	public static boolean isStorageEmpty() {
		System.out.println("STORAGE EMPTY CHECK: ");
		System.out.println("\tcond 0 - null : " + Objects.isNull(storage));
		System.out.println("\tcond 1 - empty: " + storage.getTodoLists().isEmpty());
		return Objects.isNull(storage) || storage.getTodoLists().isEmpty();
	}
	

	/**This method delete the {@code TaskList} passed as parameter and its tasks.
	 * @param list TaskList to delete
	 * @see TaskList*/
	public static void deleteList(TaskList list) {
		for(Task task: storage.getTodoLists().get(list.getId()).getTasks()) {
        	if (task.doesExpire()) {
        		deleteTaskFromCalendar(task);
            }
        }
        storage.getTodoLists().remove(list.getId());
		saveTasks();
	}	
	
	
	/**This method deletes a single {@code Task} passed as parameter, both from
	 * it's original list and from calendar.
	 * 
	 * @see Task
	 * @see TasksStorage*/
	public static void deleteTask(Task task) {
		if (task.doesExpire()) {
			deleteTaskFromCalendar(task);  
		}
		storage.getTodoLists().get(task.getListName()).removeTask(task);
		saveTasks();
	}
	
	

	/**This method deletes a {@code Task} from a calendar day in {@code TasksStorage}.
	 * @param task Task to delete
	 * @see TasksStorage*/
	private static void deleteTaskFromCalendar(Task task) {
		TaskList taskList = storage.getCalendarTasks().get(task.getExpiration());
		taskList.removeTask(task); 
	}


    /**This method switches the internal state of a {@code Task}:
     * If the task is completed, it is set to not completed and vice-versa.
     * 
     * @param task Task to switch the state to*/
    public static void switchTaskState(Task task) {
    	
    	deleteTask(task);
    	task.setCompleted(!task.isCompleted());
    	addTaskToList(task);
    }
	
	
    /**This method reads the storage file (XML) and initialises the {@code TaskStorage}.
     * 
     * @see TasksStorage*/
	public static TasksStorage readTasks() {
		
		try {
			storage = new TasksStorage();
			JAXBContext context = JAXBContext.newInstance(TasksStorage.class);
			storage = (TasksStorage) context.createUnmarshaller().unmarshal(new FileReader(dataFilePath));
			
			for(String id:storage.getTodoLists().keySet()) {
				System.out.println(id);
			}
			
			return storage;
			
		} catch(IOException e) {
			System.out.println("TASK UNMARSHAL ERROR - I/O");
			e.printStackTrace();
		} catch(JAXBException je) {
			System.out.println("TASK UNMARSHAL ERROR - JAXB");
			je.printStackTrace();
		}
		return null;
	}

	/**This method saves the informations contained in the {@code TasksStorage} object in the 
	 * storage file (XML).
	 * 
	 * @see TasksStorage*/
	public static void saveTasks (){
			
		try {
				File dataFile = new File(dataFilePath);
			
				JAXBContext context = JAXBContext.newInstance(TasksStorage.class);
			    Marshaller mar = context.createMarshaller();
			    mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			    mar.marshal(storage, dataFile);
			    
			} catch(PropertyException pe) {
				System.out.println("MARSHAL ERROR - Property");
				pe.printStackTrace();
			} catch(JAXBException je) {
				System.out.println("MARSHAL ERROR - JAXB");
				je.printStackTrace();
			}
			
		}
	
	

	
	
} 