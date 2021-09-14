import java.util.LinkedList; //java.util implements LinkedLists as deques and this serves my needs
import java.util.PriorityQueue;
import java.util.Stack;

public class Sokoban {
    private Node root;

    public Sokoban(char[][] initialState) {
        //todo
        LinkedList<Box> Boxes = new LinkedList<Box>();
        Point robotStart = new Point();
        for(int i = 0; i < initialState.length; i++)
        {
            for(int j = 0; j < initialState[0].length; j++){
                if(initialState[i][j] == 'B'){
                    Boxes.add(new Box(new Point(i,j)));
                }
                if(initialState[i][j] == 'R'){
                    robotStart = new Point(i,j);
                }
            }
        }
        Node.initialize(initialState);
        root = new Node(null,"Initial",Boxes,robotStart);
    }
    public String solve(int searchType) {
        Node solution;
        switch(searchType){
            case 1:
                System.out.println("Preforming Breath First Search:");
                solution = breadthFirstSearch();
                break;
            case 2:
                System.out.println("Preforming Depth First Search:");
                solution = depthFirstSearch();
                break;
            case 3:
                System.out.println("Preforming A* Search:");
                solution = aStarSearch();
                break;
            default:
                solution = null;
        }
        if (solution != null) {
            return solution.getPath();
        }
        System.out.println("\tNO PATH FOUND");
        return null;
    }
    private Node breadthFirstSearch() {
        if(root.isVictory()){return root;} //if the initial state is a victory stat do no work return the root

        LinkedList<Node> unvisitedQueue = new LinkedList<Node>();
        LinkedList<Node> visited = new LinkedList<Node>(); //
        
        unvisitedQueue.add(root);
        int nodesTraversed = 0;
        while(!unvisitedQueue.isEmpty()) {
            Node currentNode = unvisitedQueue.poll();
            nodesTraversed++;
            if(currentNode.isVictory()) {
                System.out.println("\tTraversed " + nodesTraversed + " nodes");
                return currentNode;
            }
            Node tempNode = currentNode.North(); 
            if(tempNode != null && !visited.contains(tempNode)){
                unvisitedQueue.add(tempNode);
            }
            tempNode = currentNode.East(); 
            if(tempNode != null && !visited.contains(tempNode)){
                unvisitedQueue.add(tempNode);
            }
            tempNode = currentNode.South(); 
            if(tempNode != null && !visited.contains(tempNode)){
                unvisitedQueue.add(tempNode);
            }
            tempNode = currentNode.West(); 
            if(tempNode != null && !visited.contains(tempNode)){
                unvisitedQueue.add(tempNode);
            }    
            visited.add(currentNode);
        }
        //if we make it out of the loop before returning a node then no path was found
        System.out.println("\tTraversed " + nodesTraversed + " nodes");
        return null;
    }
    private Node depthFirstSearch() {
        if(root.isVictory()){return root;} //if the initial state is a victory stat do no work return the root
        Stack<Node> unvisitedStack = new Stack<Node>();
        LinkedList<Node> visited = new LinkedList<Node>();
        
        unvisitedStack.add(root);
        int nodesTraversed = 0;
        while(!unvisitedStack.isEmpty()) {
            Node currentNode = unvisitedStack.pop();
            nodesTraversed++;
            if(currentNode.isVictory()) {
                System.out.println("\tTraversed " + nodesTraversed + " nodes");
                return currentNode;
            }
            Node tempNode = currentNode.North(); 
            if(tempNode != null && !visited.contains(tempNode)){unvisitedStack.add(tempNode);}
            tempNode = currentNode.East(); 
            if(tempNode != null && !visited.contains(tempNode)){unvisitedStack.add(tempNode);}
            tempNode = currentNode.South(); 
            if(tempNode != null && !visited.contains(tempNode)){unvisitedStack.add(tempNode);}
            tempNode = currentNode.West(); 
            if(tempNode != null && !visited.contains(tempNode)){unvisitedStack.add(tempNode);}
            visited.add(currentNode);
        }
        System.out.println("\tTraversed " + nodesTraversed + " nodes");
        return null;
    }

    private Node aStarSearch() {
        if(root.isVictory()){return root;} //if the initial state is a victory stat do no work return the root

        PriorityQueue<Node> unvisitedQueue = new PriorityQueue<Node>();
        LinkedList<Node> visited = new LinkedList<Node>(); //
        
        unvisitedQueue.add(root);
        int nodesTraversed = 0;
        while(!unvisitedQueue.isEmpty()) {
            Node currentNode = unvisitedQueue.poll();
            nodesTraversed++;
            if(currentNode.isVictory()) {
                System.out.println("\tTraversed " + nodesTraversed + " nodes");
                return currentNode;
            }
            Node tempNode = currentNode.North(); 
            if(tempNode != null && !visited.contains(tempNode)){
                unvisitedQueue.add(tempNode);
            }
            tempNode = currentNode.East(); 
            if(tempNode != null && !visited.contains(tempNode)){
                unvisitedQueue.add(tempNode);
            }
            tempNode = currentNode.South(); 
            if(tempNode != null && !visited.contains(tempNode)){
                unvisitedQueue.add(tempNode);
            }
            tempNode = currentNode.West(); 
            if(tempNode != null && !visited.contains(tempNode)){
                unvisitedQueue.add(tempNode);
            }    
            visited.add(currentNode);
        }
        //if we make it out of the loop before returning a node then no path was found
        System.out.println("\tTraversed " + nodesTraversed + " nodes");
        return null;
    }
}