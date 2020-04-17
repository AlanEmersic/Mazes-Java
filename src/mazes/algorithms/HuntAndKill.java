package mazes.algorithms;

import mazes.entity.Cell;
import mazes.entity.Grid;

import java.util.ArrayList;
import java.util.List;

public class HuntAndKill
{
    public static Grid createMaze(Grid grid)
    {
        Cell current = grid.randomCell();

        while (current != null)
        {
            List<Cell> unvisitedNeighbors = new ArrayList<Cell>();
            for (Cell e : current.neighbors())
                if (e.links().size() == 0)
                    unvisitedNeighbors.add(e);

            if (!unvisitedNeighbors.isEmpty())
            {
                Cell neighbor = unvisitedNeighbors.get(grid.getRandom().nextInt(unvisitedNeighbors.size()));
                current.link(neighbor);
                current = neighbor;
            } else
            {
                current = null;

                for (Cell cell : grid.eachCell())
                {
                    List<Cell> visitedNeighbors = new ArrayList<Cell>();
                    for (Cell n : cell.neighbors())
                        if (!n.links().isEmpty())
                            visitedNeighbors.add(n);

                    if (cell.links().size() == 0 && !visitedNeighbors.isEmpty())
                    {
                        current = cell;
                        Cell neighbor = visitedNeighbors.get(grid.getRandom().nextInt(visitedNeighbors.size()));
                        current.link(neighbor);
                        break;
                    }
                }
            }
        }
        return grid;
    }
}
