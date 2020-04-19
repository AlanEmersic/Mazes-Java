package mazes.algorithms;

import mazes.entity.Cell;
import mazes.entity.Grid;

import java.util.*;
import java.util.stream.Collectors;

public class Kruskals
{
    private class State
    {
        public List<Map.Entry<Cell, Cell>> neighbors;
        Map<Cell, Integer> setForCell;
        Map<Integer, List<Cell>> cellsInSet;

        public List<Map.Entry<Cell, Cell>> getNeighbors()
        {
            return neighbors;
        }

        public State(Grid grid)
        {
            neighbors = new ArrayList<>();
            setForCell = new HashMap<>();
            cellsInSet = new HashMap<>();

            for (Cell cell : grid.eachCell())
            {
                int set = setForCell.size();
                setForCell.put(cell, set);
                cellsInSet.put(set, new ArrayList<>());
                cellsInSet.get(set).add(cell);

                if (cell.getSouth() != null)
                    neighbors.add(new AbstractMap.SimpleEntry<>(cell, cell.getSouth()));
                if (cell.getEast() != null)
                    neighbors.add(new AbstractMap.SimpleEntry<>(cell, cell.getEast()));
            }
        }

        public boolean canMerge(Cell left, Cell right)
        {
            return !setForCell.get(left).equals(setForCell.get(right));
        }

        public void merge(Cell left, Cell right)
        {
            left.link(right);
            int winner = setForCell.get(left);
            int loser = setForCell.get(right);
            List<Cell> losers = new ArrayList<>();

            if (cellsInSet.get(loser) != null)
                losers = cellsInSet.get(loser);
            else
                losers.add(right);

            for (Cell cell : losers)
            {
                cellsInSet.get(winner).add(cell);
                setForCell.put(cell, winner);
            }

            cellsInSet.remove(loser);
        }
    }

    public static Grid createMaze(Grid grid)
    {
        State state = new Kruskals().new State(grid);
        List<Map.Entry<Cell, Cell>> neighbors = state.neighbors.stream().sorted(Comparator.comparing(e -> grid.getRandom().nextInt(state.getNeighbors().size()))).collect(Collectors.toList());

        while (!neighbors.isEmpty())
        {
            Cell left = neighbors.get(neighbors.size() - 1).getKey();
            Cell right = neighbors.get(neighbors.size() - 1).getValue();
            neighbors.remove(neighbors.get(neighbors.size() - 1));

            if (state.canMerge(left, right))
                state.merge(left, right);

        }

        return grid;
    }
}
