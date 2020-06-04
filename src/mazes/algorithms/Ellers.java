package mazes.algorithms;

import mazes.entity.Cell;
import mazes.entity.Grid;

import java.util.*;
import java.util.stream.Collectors;

public class Ellers
{
    private class RowState
    {
        Map<Integer, List<Cell>> cellsInSet;
        Map<Integer, Integer> setForCell;
        int nextSet;

        public RowState(int startingSet)
        {
            cellsInSet = new HashMap<>();
            setForCell = new HashMap<>();
            nextSet = startingSet;
        }

        public void record(int set, Cell cell)
        {
            setForCell.put(cell.getCol(), set);

            if (!cellsInSet.containsKey(set))
                cellsInSet.put(set, new ArrayList<>());

            cellsInSet.get(set).add(cell);
        }

        public int setFor(Cell cell)
        {
            if (!setForCell.containsKey(cell.getCol()))
            {
                record(nextSet, cell);
                nextSet++;
            }

            return setForCell.get(cell.getCol());
        }

        public void merge(int winner, int loser)
        {
            for (Cell cell : cellsInSet.get(loser))
            {
                setForCell.put(cell.getCol(), winner);
                cellsInSet.get(winner).add(cell);
            }

            cellsInSet.remove(loser);
        }

        public RowState next()
        {
            return new RowState(nextSet);
        }

        public List<Map.Entry<Integer, List<Cell>>> eachSet()
        {
            return new ArrayList<>(cellsInSet.entrySet());
        }
    }

    public static Grid createMaze(Grid grid)
    {
        RowState rowState = new Ellers().new RowState(0);

        for (List<Cell> row : grid.eachRow())
        {
            for (Cell cell : row)
            {
                if (cell.getWest() == null)
                    continue;

                int set = rowState.setFor(cell);
                int priorSet = rowState.setFor(cell.getWest());
                boolean shouldLink = set != priorSet && (cell.getSouth() == null || grid.getRandom().nextInt(2) == 0);

                if (shouldLink)
                {
                    cell.link(cell.getWest());
                    rowState.merge(priorSet, set);
                }
            }
            if (row.get(0).getSouth() != null)
            {
                RowState nextRow = rowState.next();

                for (var set : rowState.eachSet())
                {
                    List<Cell> list = set.getValue().stream().sorted(Comparator.comparing(x -> grid.getRandom().nextInt())).collect(Collectors.toList());
                    Cell cell = list.get(0);
                    cell.link(cell.getSouth());
                    nextRow.record(rowState.setFor(cell), cell.getSouth());
                }
                rowState = nextRow;
            }
        }

        return grid;
    }
}
