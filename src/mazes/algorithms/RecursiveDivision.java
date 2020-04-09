package mazes.algorithms;

import mazes.entity.Cell;
import mazes.entity.Grid;

public class RecursiveDivision
{
    public static Grid CreateMaze(Grid grid)
    {
        for (Cell cell : grid.eachCell())
            for (Cell n : cell.neighbors())
                cell.link(n, false);

        Divide(0, 0, grid.getRows(), grid.getCols(), grid);

        return grid;
    }

    static void Divide(int row, int column, int height, int width, Grid grid)
    {
        if (height <= 1 || width <= 1) // || height < 5 && width < 5 && Random.Range(0, 4) == 0 for rooms
            return;
        if (height > width)
            DivideHorizontally(row, column, height, width, grid);
        else
            DivideVertically(row, column, height, width, grid);
    }

    static void DivideHorizontally(int row, int column, int height, int width, Grid grid)
    {
        int divideSouthOf = grid.getRandom().nextInt(height - 1);
        int passageAt = grid.getRandom().nextInt(width);

        for (int x = 0; x < width; x++)
        {
            if (passageAt == x)
                continue;

            Cell cell = grid.getCell(row + divideSouthOf, column + x);
            cell.unlink(cell.getSouth());
        }

        Divide(row, column, divideSouthOf + 1, width, grid);
        Divide(row + divideSouthOf + 1, column, height - divideSouthOf - 1, width, grid);
    }

    static void DivideVertically(int row, int column, int height, int width, Grid grid)
    {
        int divideEastOf = grid.getRandom().nextInt(width - 1);
        int passageAt = grid.getRandom().nextInt(height);

        for (int y = 0; y < height; y++)
        {
            if (passageAt == y)
                continue;

            Cell cell = grid.getCell(row + y, column + divideEastOf);
            cell.unlink(cell.getEast());
        }

        Divide(row, column, height, divideEastOf + 1, grid);
        Divide(row, column + divideEastOf + 1, height, width - divideEastOf - 1, grid);
    }
}
