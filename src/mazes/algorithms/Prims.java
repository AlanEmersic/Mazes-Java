package mazes.algorithms;

import mazes.entity.Cell;
import mazes.entity.Grid;

import java.util.ArrayList;
import java.util.List;

public class Prims
{
    public static Grid CreateMaze(Grid grid)
    {
        Cell start = grid.randomCell();
        List<Cell> active = new ArrayList<Cell>();
        active.add(start);

        while (!active.isEmpty())
        {
            Cell cell = active.get(grid.getRandom().nextInt(active.size()));
            List<Cell> availableNeighbors = new ArrayList<Cell>();
            for (Cell e : cell.neighbors())
                if (e.links().size() == 0)
                    availableNeighbors.add(e);

            if (!availableNeighbors.isEmpty())
            {
                Cell neighbor = availableNeighbors.get(grid.getRandom().nextInt(availableNeighbors.size()));
                cell.link(neighbor);
                active.add(neighbor);
            } else
            {
                active.remove(cell);
            }
        }
        return grid;
    }
}
