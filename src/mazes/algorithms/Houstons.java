package mazes.algorithms;

import mazes.entity.Cell;
import mazes.entity.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Houstons
{
    public static Grid createMaze(Grid grid)
    {

        List<Cell> unvisited = new ArrayList<Cell>(grid.eachCell());

        int threshold = grid.getSize() / 3;
        Cell current = grid.randomCell();
        unvisited.remove(current);

        while (threshold != 0)
        {
            Cell neighbor = current.neighbors().get(grid.getRandom().nextInt(current.neighbors().size()));

            if (neighbor.links().size() == 0)
            {
                current.link(neighbor);
                unvisited.remove(neighbor);
                threshold--;
            }
            current = neighbor;
        }

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
                    path = (path.stream().limit(position + 1).collect(Collectors.toList()));
                else
                    path.add(cell);
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
