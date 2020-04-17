package mazes.algorithms;

import mazes.entity.Cell;
import mazes.entity.Grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TruePrims
{
    public static Grid createMaze(Grid grid)
    {
        Cell start = grid.randomCell();
        List<Cell> active = new ArrayList<>();
        active.add(start);
        Map<Cell, Integer> costs = new HashMap<>();
        for (Cell cell : grid.eachCell())
            costs.put(cell, grid.getRandom().nextInt(100));

        while (!active.isEmpty())
        {
            Cell cell = active.stream().reduce((min, next) -> costs.get(min) < costs.get(next) ? min : next).get();
            List<Cell> availableNeighbors = cell.neighbors().stream().filter(e -> e.links().size() == 0).collect(Collectors.toList());

            if (!availableNeighbors.isEmpty())
            {
                Cell neighbor = availableNeighbors.stream().reduce((min, next) -> costs.get(min) < costs.get(next) ? min : next).get();
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
