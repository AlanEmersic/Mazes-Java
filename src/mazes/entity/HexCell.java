package mazes.entity;

import java.util.ArrayList;
import java.util.List;

public class HexCell extends Cell
{
    HexCell northEast;
    HexCell northWest;
    HexCell southEast;
    HexCell southWest;

    public HexCell(int row, int col)
    {
        super(row, col);
    }

    @Override
    public List<Cell> neighbors()
    {
        ArrayList<Cell> list = new ArrayList<Cell>();

        if (northWest != null)
            list.add(northWest);
        if (north != null)
            list.add(north);
        if (northEast != null)
            list.add(northEast);

        if (southWest != null)
            list.add(southWest);
        if (south != null)
            list.add(south);
        if (southEast != null)
            list.add(southEast);

        return list;
    }

    public HexCell getNorthEast()
    {
        return northEast;
    }

    public void setNorthEast(HexCell northEast)
    {
        this.northEast = northEast;
    }

    public HexCell getNorthWest()
    {
        return northWest;
    }

    public void setNorthWest(HexCell northWest)
    {
        this.northWest = northWest;
    }

    public HexCell getSouthEast()
    {
        return southEast;
    }

    public void setSouthEast(HexCell southEast)
    {
        this.southEast = southEast;
    }

    public HexCell getSouthWest()
    {
        return southWest;
    }

    public void setSouthWest(HexCell southWest)
    {
        this.southWest = southWest;
    }
}
