package mazes.algorithms;

import mazes.entity.Cell;
import mazes.entity.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class RecursiveBacktracker
{
    public static Grid createMaze(Grid grid)
    {
        Cell start = grid.randomCell();
        Stack<Cell> stack = new Stack<Cell>();
        stack.push(start);

        while (!stack.isEmpty())
        {
            Cell current = stack.peek();
            List<Cell> neighbors = current.neighbors().stream().filter(e -> e.links().size() == 0).collect(Collectors.toList());

            if (neighbors.size() == 0)
                stack.pop();
            else
            {
                Cell neighbor = neighbors.get(grid.getRandom().nextInt(neighbors.size()));
                current.link(neighbor);
                stack.push(neighbor);
            }
        }

        return grid;
    }
}
