# Overview

The program uses JavaFX to visualize the A-star algorithm to find the shortest
path between two nodes with an obstacle.

Left click on an empty Node places a Start Node and right click on an empty Node
places an End Node. These must be set before a path is drawn. The draw path
button then uses the A-star algorithm to find the shortest path from the Start
Node to the End Node while accounting for the obstacle in its way. The shortest
path is highlighted by painting the Nodes green.

# Main.java

This class creates the main Stage for the program. It creates a new BorderPane
in which the grid of the cells is placed in the center and the user input
buttons at the top of the stage. The grid, obstacle and input are all drawn in
this function.

# GridMap.java

This class handles all the grid visualization for the class. The method drawGrid
is called from the main class to setup the grid. This method fills the grid with
8x8 nodes that are stored in an array. It also draws the numbers and letters
along the side of the grid for labelling the row and columns.

An obstacle is then drawn at a random position on the grid using the
drawObstacle method. This is called when the program is open from main and when
the Draw Obstacle button is clicked. This method will draw a new obstacle at a
random location every time the button is clicked. 3 Letters are used as
obstacles in this program, T, L and I. These are drawn at a random position in
the grid by use of the Letter class.

In the GridMap class an array of positions is set which make of the letter. For
example int Tshape[][] starts at position {0,0} which is a random x and y
position on the grid. {0,-1} represents the next position to be filled with an
Obstacle Node which means x, y-1 on the grid. Same for {0.1} represents x+1, y
on the grid and so on for the rest of the array and this makes up the T shape
that was calculated by hand. The same is done for the L shape and I shape. These
are then added to an array of Letters which are randomly chosen each time the
drawObstacle method is called. After a random letter is chosen, the start x and
y position are calculated to make sure that the letter fits inside the grid. The
draw method in the letter class in then called with the starting node for
drawing the letter being passed in which draws the obstacle to the grid.

The next methods that are used in the GridMap are the setStartNode and
setEndNode methods. These set the start and end nodes for the A-star algorithm
and colour the Nodes blue and red respectively. These methods also set the
previous start and end nodes to Default Nodes if they existed already and call
the resetPath method which resets all the Path Nodes in the grid to Default
Nodes.

The drawPath method inside GridMap is where the A-star algorithm is coded to
find the shortest path. Firstly, two lists empty lists of nodes are created
called openList and closedList and the startNode is added to the openList. We
then repeat the following steps until the openList is empty, which means our
path is found. We initalise a new node variable called currentNode to the first
element of the openList. We then iterate through the openList searching for the
node with the lowest F Cost. If two nodes have the same F Cost then we choose
the node with the lowest H Cost and set it to the currentNode. The currentNode
is removed from the openList and added to the closedList. The currentNode is
then checked to see if it’s the endNode to break the loop as our path has been
found. If it hasn’t been found, then all the neighbours of the currentNode are
inserted into an array. We then iterate through this array. If the neighbour is
an Obstacle Node or is in the closedList then we skip it. Otherwise we calculate
the cost it would take to move to that neighbour by getting the gCost of the
currentNode plus the heuristic distance from the currentNode to the neighbour.
If the new movement to the neighbour is less than the neighbours current g cost
or the neighbour is not in the openList then we set the G, H and F Cost of the
neighbour. If the neighbours not in the openList then we add it and then the
loop repeats.

After the loop finishes, we then draw the path. This is done by starting at the
endNode and retracing the parents of the nodes and setting all the nodes to path
nodes which colours them green.

# Letter.java

This class handles the drawing and other methods associated with the
construction of the obstacle in the shape of a Letter. The constructor for the
letter stores the name and the array shape of the letter to be drawn. It also
records the x and y distance of the shape.

The draw method of the letter class goes through each row in the 2d array and
sets an Obstacle Node based on the start row and start column plus the values in
the shape array, to make up the obstacle.

# Input.java

This class handles the creation of all the GridPane at the top of the
application above the GridMap GridPane. This class creates the Left click label,
right click label, Draw Path Button and Draw Obstacle Button. When the draw path
button is clicked it calls the drawPath method in the GridMap class and the draw
obstacle button calls the drawObstacle method in the GridMap class.

# Node.java

This class represents a Node which is what the Grid is made up of. The node uses
Rectangle to represent its colour in JavaFX.

Nodes have different types which have their own respective colour. A default
node is transparent which is simply an empty Node. An Obstacle Node is black,
the Start Node is Blue, the End Node is Red, and a Path Node is green.

Each node has it’s x,y position on the grid, the node’s parent to draw the path,
the G Cost, H Cost and F Cost. There are getter and setter methods associated
with each of these values so the GridMap class can use them.

This class also handles to start and end node placement. Listeners are setup for
whenever a Node that isn’t an obstacle node is left clicked it places a start
node, and when right clicked it places an end node.
