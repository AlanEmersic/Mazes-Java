package mazes.algorithms;

import mazes.entity.Cell;
import mazes.entity.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Wilsons
{
    public static Grid createMaze(Grid grid)
    {
        List<Cell> unvisited = new ArrayList<Cell>(grid.eachCell());

        Cell first = unvisited.get(grid.getRandom().nextInt(unvisited.size()));
        unvisited.remove(first);

        while (!unvisited.isEmpty())
        {
            Cell cell = unvisited.get(grid.getRandom().nextInt(unvisited.size()));
            List<Cell> path = new ArrayList<Cell>();
            path.add(cell);

            while (unvisited.contains(cell))
            {
                cell = cell.neighbors().get(grid.getRandom().nextInt(cell.neighbors().size()));
                int position = path.indexOf(cell);

                if (position >= 0)
                {
                    path = (path.stream().limit(position + 1).collect(Collectors.toList()));
                } else
                {
                    path.add(cell);
                }
            }

            for (int index = 0; index < path.size() - 1; index++)
            {
                path.get(index).link(path.get(index + 1));
                unvisited.remove(path.get(index));
            }
        }

        return grid;
    }
}
