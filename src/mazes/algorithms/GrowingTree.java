package mazes.algorithms;

import mazes.entity.Cell;
import mazes.entity.Grid;

import java.util.ArrayList;
import java.util.List;

public class GrowingTree
{
    public static Grid createMaze(Grid grid)
    {
        Cell start = grid.randomCell();
        List<Cell> active = new ArrayList<Cell>();
        active.add(start);

        while (!active.isEmpty())
        {
            Cell cell = grid.getRandom().nextInt(2) == 0 ? active.get(active.size() - 1)
                    : active.get(grid.getRandom().nextInt(active.size())); // recursive backtracker/prims 50/50 split

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
                active.remove(cell);
        }
        return grid;
    }
}
