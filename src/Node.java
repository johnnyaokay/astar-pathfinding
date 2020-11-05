import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Node {
	private String NodeType;
	private int x;
	private int y;
	
	private Rectangle shape;
	private Node parent;
	
	private int gCost = 0;
	private int hCost = 0;
	private int fCost = 0;
	
	private EventHandler<MouseEvent> setHandCursor = (MouseEvent event) -> {
		shape.getScene().setCursor(Cursor.HAND);
    };
    
    private EventHandler<MouseEvent> setDefaultCursor = (MouseEvent event) -> {
		shape.getScene().setCursor(Cursor.DEFAULT);
    };
	
	public Node(int x, int y) {
		NodeType = "Default";
		this.x = x;
		this.y = y;
		
		shape = new Rectangle();
		shape.setWidth(75);
		shape.setHeight(75);
		shape.setFill(Color.TRANSPARENT);
		shape.setStroke(Color.BLACK);

		shape.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY) {
            	if(this.getNodeType() != "ObstacleNode") {
            		GridMap.setStartNode(this);
            		shape.getScene().setCursor(Cursor.DEFAULT);
            	}
            } else if (e.getButton() == MouseButton.SECONDARY) {
            	if(this.getNodeType() != "ObstacleNode") {
	            	GridMap.setEndNode(this);
	            	shape.getScene().setCursor(Cursor.DEFAULT);
            	}
            }
        });
	
		shape.setOnMouseEntered(setHandCursor);
		shape.setOnMouseExited(setDefaultCursor);
	}
	
	
	
	public Rectangle getShape() {
		return shape;
	}
	
	public String getNodeType() {
		return NodeType;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public void setNodeType(String NodeType) {
		if(NodeType == "StartNode") shape.setFill(Color.BLUE);
		else if(NodeType == "EndNode") shape.setFill(Color.RED);
		else if(NodeType == "ObstacleNode") shape.setFill(Color.BLACK);
		else if(NodeType == "PathNode") shape.setFill(Color.GREEN);
		else if(NodeType == "Default") shape.setFill(Color.TRANSPARENT);
		
		if(NodeType == "Default") {
			shape.setOnMouseEntered(setHandCursor);
			shape.setOnMouseExited(setDefaultCursor);
		}else {
			shape.setOnMouseEntered(null);
			shape.setOnMouseExited(null);
		}
		
		this.NodeType = NodeType;
	}
	
	public void resetNode() {
		if(NodeType != "ObstacleNode") {
			gCost = 0;
			hCost = 0;
			if(NodeType != "StartNode" && NodeType != "EndNode") {
				NodeType = "Default";
				shape.setFill(Color.TRANSPARENT);
			}
		}
	}
	
	public int getGCost() {
		return gCost;
	}
	
	public int getHCost() {
		return hCost;
	}
	
	public int getFCost() {
		return fCost;
	}
	
	public void setGCost(int gCost) {
		this.gCost = gCost;
	}
	
	public void setHCost(int hCost) {
		this.hCost = hCost;
	}
	
	public void setFCost() {
		fCost = gCost + hCost;
	}
	
}




