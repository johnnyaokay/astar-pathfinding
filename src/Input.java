import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public abstract class Input {
	private static GridPane inputGrid;
	
	public static void drawInputGrid() {
		Label StartNodeLabel = new Label("Left click to set Start Node");
		Label EndNodeLabel = new Label("Right click to set End Node");
		
		Button drawPathButton = new Button("Draw Path");
		Button drawObstacleButton = new Button("Draw Obstacle");
		
		inputGrid = new GridPane();
		inputGrid.setVgap(5);
		inputGrid.setHgap(5);
		inputGrid.setPadding(new Insets(10, 10, 10, 10));
		inputGrid.add(StartNodeLabel, 0, 0);
		inputGrid.add(EndNodeLabel, 0, 1);
		inputGrid.add(drawPathButton, 0, 2);
		inputGrid.add(drawObstacleButton, 1, 2);
		
	
		drawPathButton.setOnAction(e -> {
			GridMap.drawPath();
		});
		
		drawObstacleButton.setOnAction(e -> {
			GridMap.drawObstacle();
		});
	}
	
	public static GridPane getInputGrid() {
		return inputGrid;
	}
	
}
