package mazes.algorithms;

import mazes.entity.Cell;
import mazes.entity.Grid;

import java.util.ArrayList;
import java.util.List;

public class Sidewinder
{
    public static Grid createMaze(Grid grid)
    {
        for (List<Cell> eachRow : grid.eachRow())
        {
            List<Cell> run = new ArrayList<Cell>();

            for (var cell : eachRow)
            {
                run.add(cell);

                boolean atEasternBoundary = cell.getEast() == null;
                boolean atNorthernBoundary = cell.getNorth() == null;
                boolean shouldCloseOut = atEasternBoundary || (!atNorthernBoundary && grid.getRandom().nextInt(2) == 0);

                if (shouldCloseOut)
                {
                    Cell member = run.get(grid.getRandom().nextInt(run.size()));
                    if (member.getNorth() != null)
                        member.link(member.getNorth());
                    run.clear();
                } else
                {
                    cell.link(cell.getEast());
                }
            }
        }

        return grid;
    }
}
