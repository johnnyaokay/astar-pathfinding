
public class Letter {
	private char name;
	private int shape[][];
	private int xSpace;
	private int ySpace;
	
	public Letter(char name, int[][] shape) {
		for(int r = 0; r < shape.length; r++) {
			if(Math.abs(shape[r][0]) > ySpace) ySpace = Math.abs(shape[r][0]); //row (y) (space needed on y)
			if(Math.abs(shape[r][1]) > xSpace) xSpace = Math.abs(shape[r][1]); //col (x) (space needed on x)
		}
		
		this.name = name;
		this.shape = shape;
	}
	
	public void draw(Node startNode) {
		int startRow = startNode.getX();
		int startCol = startNode.getY();
		for(int r = 0; r < shape.length; r++) {
			Node nodeToDraw = GridMap.getNodes()[startRow + shape[r][0]][startCol + shape[r][1]];
			if(nodeToDraw.getNodeType() == "StartNode") GridMap.resetStartNode();
			if(nodeToDraw.getNodeType() == "EndNode") GridMap.resetEndNode();
			nodeToDraw.setNodeType("ObstacleNode");
		}
	}
	
	public char getName() {
		return name;
	}
	
	public int getXSpace() {
		return xSpace;
	}
	
	public int getYSpace() {
		return ySpace;
	}
	
}
