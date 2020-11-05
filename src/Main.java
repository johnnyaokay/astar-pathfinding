import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	private BorderPane borderPane = new BorderPane();
	
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage){
		GridMap.drawGrid();
		GridMap.drawObstacle();
		Input.drawInputGrid();
		
		borderPane.setTop(Input.getInputGrid());
		borderPane.setCenter(GridMap.getGridPane());
		Scene scene = new Scene(borderPane);

		primaryStage.setTitle("A* Pathfinding"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.setResizable(false);
		primaryStage.show(); // Display the stage
	}
	
	public void changeCursor() {
		
	}
} 

