import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class TestDriver{
    public static void main(String[] args)
    {
        char[][] map; 
        if(args.length == 1)
        {
            String inputPath = args[0];
            map = toMapArray(inputPath);
            for(int i = 0; i < map[0].length; i++)
            {
                for(int j = 0; j < map.length; j++)
                {
                    System.out.print(map[j][i]);
                }
                System.out.println();
            }
            Sokoban sokoban = new Sokoban(map);
            Node.printWeights();
            String solution = sokoban.solve(1);
            System.out.println("\tSolution found: " + solution);
            solution = sokoban.solve(2);
            System.out.println("\tSolution found: " + solution);
            solution = sokoban.solve(3);
            System.out.println("\tSolution found: " + solution);
            solution = sokoban.solve(4);
            System.out.println("\tSolution found: " + solution);
        }
        else if(args.length == 2)
        {
            String inputPath = args[0];
            int choice = 0;
            try {choice = Integer.parseInt(args[1]);}
            catch (NumberFormatException N) {
                System.out.println(args[1] + " is not recognized as number.");
                helpPrint();
                return;
            }
            if(choice < 1 || choice > 4){
                System.out.println("Solver selection out of bounds");
                helpPrint();
                return;
            }
            map = toMapArray(inputPath);
            for(int i = 0; i < map[0].length; i++)
            {
                for(int j = 0; j < map.length; j++)
                {
                    System.out.print(map[j][i]);
                }
                System.out.println();
            }
            Sokoban sokoban = new Sokoban(map);
            Node.printWeights();
            String solution = sokoban.solve(choice);
            System.out.println("\tSolution found: " + solution);
        }
        else
        {
            map = new char[][]{{'O','O','O','O','O','O','O','O'},
                        {'O',' ',' ',' ',' ',' ',' ','O'},
                        {'O','R',' ','B','B','O',' ','O'},
                        {'O','O',' ','O',' ','O',' ','O'},
                        {'O','S',' ',' ',' ',' ','S','O'},
                        {'O','O','O','O','O','O','O','O'}};

            for(int i = 0; i < map[0].length; i++)
            {
                for(int j = 0; j < map.length; j++)
                {
                    System.out.print(map[j][i]);
                }
                System.out.println();
            }
            Sokoban sokoban = new Sokoban(map);
            Node.printWeights();
            String solution = sokoban.solve(1);
            System.out.println("\tSolution found: " + solution);
            solution = sokoban.solve(2);
            System.out.println("\tSolution found: " + solution);
            solution = sokoban.solve(3);
            System.out.println("\tSolution found: " + solution);
            solution = sokoban.solve(4);
            System.out.println("\tSolution found: " + solution);
        }
        
    }

    public static void helpPrint()
    {
        System.out.println("Usage {path to text file containing puzel} {a number 1-4}");
    }

    public static char[][] toMapArray(String path){
        try{
            FileInputStream fIn = new FileInputStream(new File(path));
            BufferedReader br = new BufferedReader(new InputStreamReader(fIn));
            //Read File Line By Line
            LinkedList<String> Rows = new LinkedList<String>();
            String line;
            try{
                int maxLength = 0;
                while ((line = br.readLine()) != null) {
                    Rows.add(line);
                    maxLength = Math.max(maxLength, line.length());
                }
                char [][] A = new char[Rows.size()][];
                for(int i = 0; i < Rows.size(); i++) {
                    //right pad the string then convert to char array and assign to row i
                    A[i] = String.format("%-" + maxLength + "s", Rows.get(i)).toCharArray();
                    //A[i] = String.format("%-["+ maxLength + "]s", Rows.get(i)).toCharArray();
                }
                char [][] B = new char[A[0].length][A.length];
                for(int i = 0; i < A.length; i++) {
                    for(int j = 0; j < A[0].length; j++) {
                        B[j][i] = A[i][j];
                    }
                }
                fIn.close();
                return B;
            }
            catch (IOException E) {System.out.println("An IO exception occured while reading " + path); fIn.close(); return null;}
            //Close the input stream
            
        }
        catch (IOException E) {System.out.println("An IO exception occured\n\t" + path + " may not exist");}

        return null;
    }
}