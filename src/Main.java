import mazes.algorithms.*;
import mazes.entity.Grid;
import mazes.entity.HexGrid;
import mazes.entity.PolarGrid;

import java.util.Random;

public class Main
{
    enum Algorithm
    {
        AldousBroder, BinaryTree, HuntAndKill, RecursiveBacktracker, Sidewinder,
        Wilsons, Prims, TruePrims, Kruskals, GrowingTree, RecursiveDivision, Ellers, Houstons
    }

    public static void main(String[] args)
    {
        generateGridMaze(10);
//        generatePolarMaze(10);
//        generateHexMaze(10);
    }

    private static void generateGridMaze(int size)
    {
        Random random = new Random();
        int seed = random.nextInt();
        Grid grid = new Grid(size, size, seed);
        randomAlgorithm(grid);
        grid.toPNG(50);
        System.out.println("generated seed:" + seed);
    }

    private static void generatePolarMaze(int size)
    {
        Random random = new Random();
        int seed = random.nextInt();
        Grid grid = new PolarGrid(size, 1, seed);
        randomAlgorithm(grid);
        grid.toPNG(50);
        System.out.println("generated seed:" + seed);
    }

    private static void generateHexMaze(int size)
    {
        Random random = new Random();
        int seed = random.nextInt();
        Grid grid = new HexGrid(size, size, seed);
        randomAlgorithm(grid);
        grid.toPNG(50);
        System.out.println("generated seed:" + seed);
    }

    private static void randomAlgorithm(Grid grid)
    {
        Random random = new Random();
        Algorithm algorithm = Algorithm.values()[random.nextInt(Algorithm.values().length)];
        System.out.println(algorithm);

        switch (algorithm)
        {
            case BinaryTree:
                BinaryTree.createMaze(grid);
                break;
            case AldousBroder:
                AldousBroder.createMaze(grid);
                break;
            case GrowingTree:
                GrowingTree.createMaze(grid);
                break;
            case HuntAndKill:
                HuntAndKill.createMaze(grid);
                break;
            case Prims:
                Prims.CreateMaze(grid);
                break;
            case RecursiveBacktracker:
                RecursiveBacktracker.createMaze(grid);
                break;
            case RecursiveDivision:
                RecursiveDivision.createMaze(grid);
                break;
            case Sidewinder:
                Sidewinder.createMaze(grid);
                break;
            case Wilsons:
                Wilsons.createMaze(grid);
                break;
            case Houstons:
                Houstons.createMaze(grid);
                break;
            case Ellers:
                Ellers.createMaze(grid);
                break;
            case Kruskals:
                Kruskals.createMaze(grid);
                break;
            case TruePrims:
                TruePrims.createMaze(grid);
                break;
            default:
        }
    }
}
