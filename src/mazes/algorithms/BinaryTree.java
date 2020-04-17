package mazes.algorithms;

import mazes.entity.Cell;
import mazes.entity.Grid;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree
{
    public static Grid createMaze(Grid grid)
    {
        for (Cell cell : grid.eachCell())
        {
            List<Cell> neighbors = new ArrayList<Cell>();

            if (cell.getNorth() != null)
                neighbors.add(cell.getNorth());
            if (cell.getEast() != null)
                neighbors.add(cell.getEast());

            int index = 0;
            if (!neighbors.isEmpty())
                index = grid.getRandom().nextInt(neighbors.size());

            if (index < neighbors.size())
            {
                Cell neighbor = neighbors.get(index);
                cell.link(neighbor);
            }
        }

        return grid;
    }
}
