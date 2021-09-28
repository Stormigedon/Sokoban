import java.io.FileReader;
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
                System.out.println("-------------------=Preforming Breadth First Search:");
                solution = breadthFirstSearch();
                break;
            case 2:
                System.out.println("-------------------=Preforming Depth First Search:");
                solution = depthFirstSearch();
                break;
            case 3:
                System.out.println("-------------------=Preforming A* Search:");
                solution = aStarSearch();
                break;
            case 4:
                System.out.println("-------------------=Preforming Greedy Best First Search:");
                solution = greedFirstSearch();
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
    /*
        this is the breadth first function
        Nodes are added to a queue in the order they appear.  Nodes are only added if the node is not in the visited list.
        Nodes are generated clockwise starting with the North position.
        while the unvisitedQueue is technically a linked list java implements likned Lists as Dequeues on the back end and provides all nessicary functions 
        to interact with the list as a queue which serves my purposes just fine.
    */
    private Node breadthFirstSearch() {
        if(root.isVictory()){return root;} //if the initial state is a victory state do no work return the root

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
    /*
        this is the depth first function
        Nodes are added to a stack in the order they appear.  Nodes are only added if the node is not in the visited list.
        Nodes are generated clockwise starting with the North position.
        The left most node will always be visited first in this case that is the west facing node.
    */
    private Node depthFirstSearch() {
        if(root.isVictory()){return root;} //if the initial state is a victory state do no work return the root
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
    /*
        this is the A* search function
        Nodes are added to a queue based on the value given by Node.calculateHuristic placing nodes with lower values nearer to the top.
        Nodes are only added if the node is not in the visited list.
        Nodes are generated clockwise starting with the North position.
    */
    private Node aStarSearch() {
        if(root.isVictory()){return root;} //if the initial state is a victory stat do no work return the root
        //todo add lambda function here and remove Node.Compare
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

    
    //gready best first searchs are functionally identical to A* search using a different huristic. 

    private Node greedFirstSearch() {
        if(root.isVictory()){return root;} //if the initial state is a victory stat do no work return the root
        //the defualt behaviour of a java PriorityQueue is to sort by GREATEST value first so the arguments for the comparitor function are reversed.
        PriorityQueue<Node> unvisitedQueue = new PriorityQueue<Node>((o1,o2) -> o2.simpleManhattenHuristic() - o1.simpleManhattenHuristic());
        LinkedList<Node> visited = new LinkedList<Node>(); 
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