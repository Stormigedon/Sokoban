

public class TestDriver{
    public static void main(String[] args)
    {
        char[][] map = {{'O','O','O','O','O','O','O','O'},
                        {'O',' ',' ',' ',' ',' ',' ','O'},
                        {'O','R',' ','B',' ','O',' ','O'},
                        {'O','O',' ','O','B','O',' ','O'},
                        {'O','S',' ',' ',' ',' ','S','O'},
                        {'O','O','O','O','O','O','O','O'}};
        //{{'O','O','O','O','O','O'},
        // {'O',' ','R',' ',' ','O'},
        // {'O',' ',' ',' ',' ','O'},
        // {'O',' ','B','O','S','O'},
        // {'O',' ',' ',' ',' ','O'},
        // {'O','O','O','O','O','O'}};     
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
    }
}