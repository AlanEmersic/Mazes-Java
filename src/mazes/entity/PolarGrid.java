package mazes.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PolarGrid extends Grid
{
    public PolarGrid(int rows, int cols, int seed)
    {
        super(rows, 1, seed);
    }

    @Override
    public Cell checkCell(int row, int col)
    {
        if (row < 0 || row >= rows || col < 0)
            return null;
        return (PolarCell) cells[row][col % cells[row].length];
    }

    @Override
    protected void prepareGrid()
    {
        cells = new PolarCell[rows][];
        float rowHeight = 1.0f / rows;
        cells[0] = new PolarCell[1];
        cells[0][0] = new PolarCell(0, 0);
        size = 1;

        for (int row = 1; row < rows; row++)
        {
            float radius = (float) row / rows;
            float circumference = (float) (2 * Math.PI * radius);
            int previousCount = cells[row - 1].length;
            float estimatedCellWidth = circumference / previousCount;
            float ratio = (float) Math.round(estimatedCellWidth / rowHeight);
            int cells = (int) (previousCount * ratio);

            this.cells[row] = new PolarCell[cells];
            for (int col = 0; col < cells; col++)
            {
                this.cells[row][col] = new PolarCell(row, col);
                size++;
            }
        }
    }

    @Override
    protected void configureCells()
    {
        for (Cell cell : eachCell())
        {
            int row = cell.getRow();
            int col = cell.getCol();

            if (row > 0)
            {
                ((PolarCell) cell).setCW((PolarCell) getCell(row, col + 1));
                ((PolarCell) cell).setCCW((PolarCell) getCell(row, col - 1));

                int ratio = cells[row].length / cells[row - 1].length;
                PolarCell parent = (PolarCell) cells[row - 1][col / ratio];
                parent.getOutward().add((PolarCell) cell);
                ((PolarCell) cell).setInward(parent);
            }
        }
    }

    @Override
    public Cell randomCell()
    {
        int row = random.nextInt(rows);
        int col = random.nextInt(cells[row].length);

        return cells[row][col];
    }

    @Override
    public void toPNG(int cellSize)
    {
        int imgSize = 2 * rows * cellSize;
        int center = imgSize / 2;
        BufferedImage image = new BufferedImage(imgSize + 1, imgSize + 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2d = image.createGraphics();
        graphics2d.setBackground(Color.WHITE);
        graphics2d.fillRect(0, 0, imgSize, imgSize);
        float penWidth = cellSize * 0.1f;
        graphics2d.setColor(Color.BLACK);
        graphics2d.setStroke(new BasicStroke(penWidth));

        for (Cell cell : eachCell())
        {
            if (cell.getRow() == 0)
                continue;

            float theta = (float) (2 * Math.PI / cells[cell.getRow()].length);
            float innerRadius = cell.getRow() * cellSize;
            float outerRadius = (cell.getRow() + 1) * cellSize;
            float thetaCCW = cell.getCol() * theta;
            float thetaCW = (cell.getCol() + 1) * theta;

            int ax = center + (int) (innerRadius * Math.cos(thetaCCW));
            int ay = center + (int) (innerRadius * Math.sin(thetaCCW));
            int cx = center + (int) (innerRadius * Math.cos(thetaCW));
            int cy = center + (int) (innerRadius * Math.sin(thetaCW));
            int dx = center + (int) (outerRadius * Math.cos(thetaCW));
            int dy = center + (int) (outerRadius * Math.sin(thetaCW));

            if (!cell.isLinked(((PolarCell) cell).getInward()))
                graphics2d.drawLine(ax, ay, cx, cy);
            if (!cell.isLinked(((PolarCell) cell).getCW()))
                graphics2d.drawLine(cx, cy, dx, dy);

        }

        graphics2d.draw(new Ellipse2D.Double(center - rows * cellSize, center - rows * cellSize, rows * 2 * cellSize,
                rows * 2 * cellSize));

        File file = new File("src\\maze.png");
        try
        {
            ImageIO.write(image, "png", file);
            Desktop.getDesktop().open(file);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
