package mazes.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Cell
{
    private int row;
    private int col;
    protected Cell east;
    protected Cell west;
    protected Cell north;
    protected Cell south;
    private Map<Cell, Boolean> links;

    public Cell(int row, int col)
    {
        this.row = row;
        this.col = col;
        links = new HashMap<Cell, Boolean>();
    }

    public Cell link(Cell cell, boolean bidirectional)
    {
        links.put(cell, true);

        if (bidirectional)
            cell.link(this, false);

        return this;
    }

    public Cell link(Cell cell)
    {
        boolean bidirectional = true;
        links.put(cell, true);

        if (bidirectional)
            cell.link(this, false);

        return this;
    }

    public Cell unlink(Cell cell, Boolean bidirectional)
    {
        links.remove(cell);

        if (bidirectional)
            cell.unlink(this, false);

        return this;
    }

    public Cell unlink(Cell cell)
    {
        Boolean bidirectional = true;
        links.remove(cell);

        if (bidirectional)
            cell.unlink(this, false);

        return this;
    }

    public List<Cell> links()
    {
        return new ArrayList<Cell>(links.keySet());
    }

    public boolean isLinked(Cell cell)
    {
        if (cell == null)
            return false;

        return links.containsKey(cell);
    }

    public List<Cell> neighbors()
    {
        ArrayList<Cell> list = new ArrayList<Cell>();

        if (north != null)
            list.add(north);
        if (south != null)
            list.add(south);
        if (east != null)
            list.add(east);
        if (west != null)
            list.add(west);

        return list;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public Cell getEast()
    {
        return east;
    }

    public void setEast(Cell east)
    {
        this.east = east;
    }

    public Cell getWest()
    {
        return west;
    }

    public void setWest(Cell west)
    {
        this.west = west;
    }

    public Cell getNorth()
    {
        return north;
    }

    public void setNorth(Cell north)
    {
        this.north = north;
    }

    public Cell getSouth()
    {
        return south;
    }

    public void setSouth(Cell south)
    {
        this.south = south;
    }
}
