package main.java;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**This is the main class, that is responsible for starting the application.
 * @author Francesco Carboni
 */
public class Main extends Application{

    private static MainController controller = new MainController();
        
    @Override
    public void start(Stage stage) {
 
    	Scene scene = new Scene(controller.getApplicationView());
    	
    	stage.setResizable(false);
    	stage.setScene(scene);
    	stage.setTitle("TODO LIST");
    	stage.show();	
	  }
    	
    

    public static void main(String[] args) {

    	launch(args);

    }
    
 
       
}
	

