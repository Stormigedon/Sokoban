import java.util.LinkedList;

//version 1.0

public class Node implements Comparable{
    private static char[][] staticObjects; //all the static objects in the world i.e. walls, storage spots, free spaces
    private static LinkedList<Point> storageSpaces; //storages never move; this is used to caclulate the huristic faster

    //This could be modified to priotize certian actions over other such as moving a box closer to a storage
    //I found these provide adiquate results
    private static final float robotWeight = 1, storageWeight = 1, boxWeight = 0.5f; 

    private Node parent;
    private String step; //the step taken to arrive at this node from the parent node
    private LinkedList<Box> boxes; //a list containing all the boxes on the map at their current positions at this node
    private float huristic;
    private int robotSteps;
    private Point robotPos; //the position of the robot at the current Node

    public Node(Node P, String S, LinkedList<Box> B, Point rP)  {
        parent = P;
        step = S;
        if(P != null) {robotSteps = P.robotSteps++;}
        else {robotSteps = 0;}
        boxes = new LinkedList<Box>(B);
        robotPos = new Point(rP);
        calculateHuristic();
    }
    public static void initialize(char[][] input) {
        staticObjects = new char[input.length][input[0].length];
        storageSpaces = new LinkedList<Point>();
        for(int i = 0; i < input.length; i++) {
            for(int j = 0; j < input[0].length; j++) {
                staticObjects[i][j] = input[i][j];
                if(input[i][j] == 'S') {
                    storageSpaces.add(new Point(i,j));
                }
            }
        }
    }
    public boolean isVictory() {
        for(Box B: boxes) {
            if(!(staticObjects[B.getBoxPos().getX()][B.getBoxPos().getY()] == 'S')) {return false;}
        }
        return true;
    }
    public String getPath() {
        if(parent != null) {
            return parent.getPath() + ", " +step;
        } // if parent == null then we're at the head return "Initial" to indicate start position
        return "Initial";
    }
    
    private int manDistance(Point A, Point B) { //calculate the Manhatten distance between two points
        return Math.abs(A.getX() - B.getX()) + Math.abs(A.getY() - B.getY());
    }

    private void calculateHuristic() {
        huristic = 0;
        int robotDistance = 0, storageDistance = 0, boxDistance = Integer.MAX_VALUE;
        //This is a minor but signifigant boost to the hurestic, by tracking both the distance the robot has travled AND how far the robot is from the nearest box
        //moves which bring the robot closer to a box can be proitized, these moves are likely to result in success sooner
        for(Box B: boxes) {
            boxDistance = Math.min(manDistance(B.getBoxPos(), robotPos),boxDistance);
            int tempDistance = Integer.MAX_VALUE;
            //future optimizations for this would involve finding a way to remove this loop
            for(Point P: storageSpaces) {
                tempDistance = Math.min(tempDistance,manDistance(B.getBoxPos(), P));
            }
            storageDistance += tempDistance;
        }
        robotDistance = robotSteps;
        huristic = robotDistance * robotWeight + storageDistance * storageWeight + boxDistance * boxWeight;
    }
    public int simpleManhattenHuristic()
    {
        int storageDistance = 0;
        for(Box B: boxes) {
            int tempDistance = Integer.MAX_VALUE;
            //future optimizations for this would involve finding a way to remove this loop 
            for(Point P: storageSpaces) {
                tempDistance = Math.min(tempDistance,manDistance(B.getBoxPos(), P));
            }
            storageDistance += tempDistance;
        }
        return storageDistance;
    }
    public float getHuristic() {
        return huristic;
    }

    private Node moveRobot(int X, int Y) {
        Point newRobotPos = new Point(robotPos.getX() + X,robotPos.getY() + Y);
        LinkedList<Box> newBoxes = new LinkedList<Box>();
        //todo find a way to optimize this loop (maybe map where key=X+Y*Width), this could allow O(1) look up for boxes
        for(Box B: boxes){
            Box newBox = B.clone();
            newBoxes.add(newBox);
            if(B.getBoxPos().equals(newRobotPos)) {
                if(staticObjects[B.getBoxPos().getX() + X][B.getBoxPos().getY() + Y] == 'O') {
                    //box is against a wall in the direction of travel
                    return null;
                }
                for(Box B2: boxes){
                    if(B2.getBoxPos().equals(new Point(B.getBoxPos().getX() + X,B.getBoxPos().getY() + Y))) {
                        //box is against a box in direction of travel
                        return null;
                    } 
                }
                newBox.setBoxPos(new Point(B.getBoxPos().getX() + X,B.getBoxPos().getY() + Y));
            }
        }
        if(X == 1) {
            return new Node(this, "East", newBoxes, newRobotPos);
        }
        if(Y == 1) {
            return new Node(this, "South", newBoxes, newRobotPos);
        }
        if(X == -1) {
            return new Node(this, "West", newBoxes, newRobotPos);
        }
        if(Y == -1) {
            return new Node(this, "North", newBoxes, newRobotPos);
        }
        return null;
    }

    public Node North()  {
        if(staticObjects[robotPos.getX()][robotPos.getY() - 1] == 'O') {
            return null;
        }//robot is againsts a wall in the direction of travel
        return moveRobot(0,-1);
    }
	public Node East()  {
        if(staticObjects[robotPos.getX() + 1][robotPos.getY()] == 'O') {
            return null;
        }//robot is againsts a wall in the direction of travel
        return moveRobot(1,0);
	}
    public Node South()  {
        if(staticObjects[robotPos.getX()][robotPos.getY() + 1] == 'O') {
            return null;
        }//robot is againsts a wall in the direction of travel
        return moveRobot(0,1);
	}
    public Node West()  {
        if(staticObjects[robotPos.getX() - 1][robotPos.getY()] == 'O') {
            return null;
        }//robot is againsts a wall in the direction of travel
        return moveRobot(-1,0);
	}


    /*
        Here I'm overriding two of the basic java object functions since the default behavior fails to return expected results
        Nodes are considered equal if all boxes and the robot are in the same positions
    */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == this.getClass()) {
            return this.boxes.equals(((Node)obj).boxes) && this.robotPos.equals(((Node)obj).robotPos);
        }
        return false;
    }
    
    @Override
    public int compareTo(Object obj) {
        if (obj == null) {return 1;}
        if (obj.getClass() != this.getClass()) {
            throw new ClassCastException();
        }
        return (this.huristic > ((Node)obj).huristic) ? 1 : 0;
    }

    public static void printWeights(){
        System.out.println("Weights are robot travel: " + robotWeight + ", storage distance: " + storageWeight + ", box travel: " + boxWeight);
    }    
}

//idea use a 2d array containing only robot and boxes 