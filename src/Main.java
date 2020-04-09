import mazes.algorithms.*;
import mazes.entity.Grid;
import mazes.entity.HexGrid;

import java.util.Random;

public class Main
{
    enum Algorithm
    {
        BinaryTree, AldousBroder, GrowingTree, HuntAndKill, Prims, RecursiveBacktracker, RecursiveDivision, Sidewinder,
        Wilsons, Houstons
    }

    public static void main(String[] args)
    {
//        generateGridMaze(10);
        generateHexMaze(10);
    }

    private static void generateGridMaze(int size)
    {
        Random random = new Random();
        int seed = random.nextInt();
        Grid grid = new Grid(size, size, seed);
        grid = RandomAlgorithm(grid);
        grid.toPNG(50);
        System.out.println("generated seed:" + seed);
    }

    private static void generateHexMaze(int size)
    {
        Random random = new Random();
        int seed = random.nextInt();
        Grid grid = new HexGrid(size, size, seed);
        Houstons.CreateMaze(grid);
        grid.toPNG(50);
        System.out.println("generated seed:" + seed);
    }

    private static Grid RandomAlgorithm(Grid grid)
    {
        Random random = new Random();
        Algorithm algorithm = Algorithm.values()[random.nextInt(Algorithm.values().length)];
        System.out.println(algorithm);

        switch (algorithm)
        {
            case BinaryTree:
                return BinaryTree.CreateMaze(grid);
            case AldousBroder:
                return AldousBroder.CreateMaze(grid);
            case GrowingTree:
                return GrowingTree.CreateMaze(grid);
            case HuntAndKill:
                return HuntAndKill.CreateMaze(grid);
            case Prims:
                return Prims.CreateMaze(grid);
            case RecursiveBacktracker:
                return RecursiveBacktracker.CreateMaze(grid);
            case RecursiveDivision:
                return RecursiveDivision.CreateMaze(grid);
            case Sidewinder:
                return Sidewinder.CreateMaze(grid);
            case Wilsons:
                return Wilsons.CreateMaze(grid);
            case Houstons:
                return Houstons.CreateMaze(grid);
            default:
                return null;
        }
    }
}
