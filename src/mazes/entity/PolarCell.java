package mazes.entity;

import java.util.ArrayList;
import java.util.List;

public class PolarCell extends Cell
{
    PolarCell cw;
    PolarCell ccw;
    PolarCell inward;
    List<PolarCell> outward;

    public PolarCell(int row, int col)
    {
        super(row, col);
        outward = new ArrayList<PolarCell>();
    }

    @Override
    public List<Cell> neighbors()
    {

        ArrayList<Cell> list = new ArrayList<Cell>();

        if (cw != null)
            list.add(cw);
        if (ccw != null)
            list.add(ccw);
        if (inward != null)
            list.add(inward);

        list.addAll(outward);

        return list;
    }

    public PolarCell getCW()
    {
        return cw;
    }

    public void setCW(PolarCell cw)
    {
        this.cw = cw;
    }

    public PolarCell getCCW()
    {
        return ccw;
    }

    public void setCCW(PolarCell ccw)
    {
        this.ccw = ccw;
    }

    public PolarCell getInward()
    {
        return inward;
    }

    public void setInward(PolarCell inward)
    {
        this.inward = inward;
    }

    public List<PolarCell> getOutward()
    {
        return outward;
    }
}
