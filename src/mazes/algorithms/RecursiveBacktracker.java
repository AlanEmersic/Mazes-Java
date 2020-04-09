package mazes.algorithms;

import mazes.entity.Cell;
import mazes.entity.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RecursiveBacktracker
{
    public static Grid CreateMaze(Grid grid)
    {
        Cell start = grid.randomCell();
        Stack<Cell> stack = new Stack<Cell>();
        stack.push(start);

        while (!stack.isEmpty())
        {
            Cell current = stack.peek();
            List<Cell> neighbors = new ArrayList<Cell>();

            current.neighbors().forEach((Cell e) ->
            {
                if (e.links().size() == 0)
                    neighbors.add(e);
            });

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
