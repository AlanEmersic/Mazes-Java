package mazes.algorithms;

import mazes.entity.Cell;
import mazes.entity.Grid;

public class AldousBroder
{
    public static Grid CreateMaze(Grid grid)
    {
        Cell cell = grid.randomCell();
        int unvisited = grid.getSize() - 1;

        while (unvisited > 0)
        {
            Cell neighbor = cell.neighbors().get(grid.getRandom().nextInt(cell.neighbors().size()));

            if (neighbor.links().size() == 0)
            {
                cell.link(neighbor);
                unvisited--;
            }
            cell = neighbor;
        }

        return grid;
    }
}
