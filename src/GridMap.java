import java.util.ArrayList;
import java.util.Random;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public abstract class GridMap {
	private static Random r = new Random();
	
	private static GridPane gridPane;
	private static Node[][] grid;
	private static Node startNode;
	private static Node endNode;
	
	private static ArrayList<Letter> letters = new ArrayList<Letter>();
	
	ArrayList<Node> openList;
	ArrayList<Node> closedList;
	
	public static GridPane getGridPane() {
		return gridPane;
	}
	
	public static Node[][] getNodes() {
		return grid;
	}
	
	public static void setStartNode(Node newStartNode) {
		if(startNode != null) {
			startNode.setNodeType("Default");
			resetPath();
		}
		startNode = newStartNode;
		startNode.setNodeType("StartNode");
	}
	
	public static void setEndNode(Node newEndNode) {
		if(endNode != null) {
			endNode.setNodeType("Default");
			resetPath();
		}
		endNode = newEndNode;
		endNode.setNodeType("EndNode");
	}
	
	private static int randomInt(int min, int max) {
		return r.nextInt(max - min + 1) + min;
	}
	
	public static void drawGrid() {
		gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		grid = new Node[8][8];
		
		//row labels
		int count = 1;
		for(int r = 1; r < 9; r++) {
			for(int c = 0; c < 1; c++) {
				Label label = new Label(Integer.toString(count));
				gridPane.add(label, c, r);
				count++;
			}
		}
		
		//column labels
		char letter = 65;
		for(int r = 0; r < 1; r++) {
			for(int c = 1; c < 9; c++) {
				Label label = new Label(Character.toString(letter));
				gridPane.add(label, c, r);
				GridPane.setHalignment(label, HPos.CENTER);
				letter++;
			}
		}
		
		//actual grid
		for(int r = 1; r < 9; r++) {
			for(int c = 1; c < 9; c++) {
				Node node = new Node(r - 1, c - 1);
				gridPane.add(node.getShape(), c, r);
				grid[r - 1][c - 1] = node;
			}
		}
	}
	
	public static void drawObstacle() {
		resetObstacle();
		resetPath();
		int Tshape[][] = {{0,0}, {0,-1}, {0,1}, {1,0}, {2,0}, {3,0}};
		int Lshape[][] = {{0,0}, {1,0}, {2,0}, {2,1}};
		int Ishape[][] = {{0,0}, {1,0}, {2,0}, {3,0}};
		letters.add(new Letter('T', Tshape));
		letters.add(new Letter('L', Lshape));
		letters.add(new Letter('I', Ishape));
		
		Letter randomLetter = letters.get(randomInt(0, letters.size() - 1));
		int xPos = randomInt(1, (grid.length - 1) - (randomLetter.getYSpace() + 1));
		int yPos = randomInt(randomLetter.getXSpace() + 1, (grid.length - 1) - (randomLetter.getXSpace() + 1));
		
		randomLetter.draw(grid[xPos][yPos]);
	}
	
	public static void resetPath() {
		for(int r = 0; r < grid.length; r++) {
			for(int c = 0; c < grid.length; c++) {
				if(grid[r][c].getNodeType() == "PathNode") {
					grid[r][c].setNodeType("Default");
				}
			}
		}
	}
	
	public static void resetObstacle() {
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				if(grid[r][c].getNodeType() == "ObstacleNode") {
					grid[r][c].setNodeType("Default");
				}
			}
		}
	}
	
	public static void resetStartNode() {
		startNode = null;
	}
	
	public static void resetEndNode() {
		endNode = null;
	}
	
	private static int getDistance(Node A, Node B) {
		return Math.abs(A.getX() - B.getX()) + Math.abs(A.getY() - B.getY());
	}

	public static void drawPath() {
		if(startNode == null || endNode == null)return;
		
		//Initialise open and closed list
		ArrayList<Node> openList = new ArrayList<Node>();
		ArrayList<Node> closedList = new ArrayList<Node>();
		
		//Add the start node
		openList.add(startNode);
	

		while(openList.size() > 0) {
			Node currentNode = openList.get(0);
			
			//set currentnode to node in open list with lowest fcost (if the same then lowest h cost)
			for(int i = 1; i < openList.size(); i++) {
				if((openList.get(i).getFCost() < currentNode.getFCost())
				|| (openList.get(i).getFCost() == currentNode.getFCost())) {
					if(openList.get(i).getHCost() < currentNode.getHCost()) {
						currentNode = openList.get(i);
					}
				}
			}
			
			openList.remove(currentNode);
			closedList.add(currentNode);
			
			//check if on end node
			if(currentNode == endNode) {
				break;
			}
			
			ArrayList<Node> Neighbours = new ArrayList<Node>();
			if(currentNode.getX() + 1 >= 0 && currentNode.getX() + 1 <= 7) {
				Neighbours.add(grid[currentNode.getX() + 1][currentNode.getY()]);
			}
			if(currentNode.getX() - 1 >= 0 && currentNode.getX() - 1 <= 7) {
				Neighbours.add(grid[currentNode.getX() - 1][currentNode.getY()]);
			}
			if(currentNode.getY() + 1 >= 0 && currentNode.getY() + 1 <= 7) {
				Neighbours.add(grid[currentNode.getX()][currentNode.getY() + 1]);
			}
			if(currentNode.getY() - 1 >= 0 && currentNode.getY() - 1 <= 7) {
				Neighbours.add(grid[currentNode.getX()][currentNode.getY() - 1]);
			}
			
			
			for(int i = 0; i < Neighbours.size(); i ++) {
				if(Neighbours.get(i).getNodeType() == "ObstacleNode" || 
				closedList.contains(Neighbours.get(i))) {
					continue;
				}
				
				int MovementCostToNeighbour = currentNode.getGCost() + getDistance(currentNode, Neighbours.get(i));
				if(MovementCostToNeighbour < Neighbours.get(i).getGCost() || !openList.contains(Neighbours.get(i))) {
					Neighbours.get(i).setGCost(MovementCostToNeighbour);
					Neighbours.get(i).setHCost(getDistance(Neighbours.get(i), endNode));
					Neighbours.get(i).setFCost();
					Neighbours.get(i).setParent(currentNode);
					if(!openList.contains(Neighbours.get(i))) {
						openList.add(Neighbours.get(i));
					}
				}
			}
			
		}
		
		Node currentNode = endNode;
		
		while(currentNode != startNode) {
			currentNode = currentNode.getParent();
			if(currentNode != startNode) {
				currentNode.setNodeType("PathNode");
			}
			
		}
		
	}

}
