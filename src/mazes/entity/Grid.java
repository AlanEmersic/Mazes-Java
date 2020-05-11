package mazes.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class Grid
{
    protected int rows, cols, size;
    protected Cell[][] cells;
    protected Random random;

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public int getSize()
    {
        return size;
    }

    public Cell[][] getCells()
    {
        return cells;
    }

    public Random getRandom()
    {
        return random;
    }

    public Grid(int rows, int cols, int seed)
    {
        this.rows = rows;
        this.cols = cols;
        random = new Random(seed);

        prepareGrid();
        configureCells();
    }

    public Cell checkCell(int row, int col)
    {
        if (row < 0 || row >= rows)
            return null;
        if (col < 0 || col >= cols)
            return null;
        return cells[row][col];
    }

    protected void prepareGrid()
    {
        size = rows * cols;
        cells = new Cell[rows][];

        for (int i = 0; i < rows; i++)
        {
            cells[i] = new Cell[cols];
            for (int j = 0; j < cols; j++)
                cells[i][j] = new Cell(i, j);
        }
    }

    protected void configureCells()
    {
        for (Cell cell : eachCell())
        {
            int row = cell.getRow();
            int col = cell.getCol();

            cell.setNorth(checkCell(row - 1, col));
            cell.setSouth(checkCell(row + 1, col));
            cell.setWest(checkCell(row, col - 1));
            cell.setEast(checkCell(row, col + 1));
        }
    }

    public Cell randomCell()
    {
        int row = random.nextInt(rows);
        int col = random.nextInt(cols);

        return cells[row][col];
    }

    public List<List<Cell>> eachRow()
    {
        List<List<Cell>> list = new ArrayList<>();
        List<Cell> row = new ArrayList<Cell>();

        for (int i = 0; i < rows; i++)
        {
            row.addAll(Arrays.asList(cells[i]).subList(0, cols));
            list.add(row);
            row = new ArrayList<Cell>();
        }

        return list;
    }

    public List<Cell> eachCell()
    {
        List<Cell> list = new ArrayList<Cell>();

        for (List<Cell> cellRow : eachRow())
            list.addAll(cellRow);

        return list;
    }

    public Cell getCell(int row, int col)
    {
        return checkCell(row, col);
    }

    public void toPNG(int cellSize)
    {
        int imgWidth = cellSize * cols;
        int imgHeight = cellSize * rows;

        BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2d = image.createGraphics();
        graphics2d.setBackground(Color.WHITE);
        graphics2d.fillRect(0, 0, imgWidth, imgHeight);
        float penWidth = cellSize * 0.1f;
        graphics2d.setColor(Color.BLACK);
        graphics2d.setStroke(new BasicStroke(penWidth));

        for (Cell cell : eachCell())
        {
            int x1 = cell.getCol() * cellSize;
            int y1 = cell.getRow() * cellSize;
            int x2 = (cell.getCol() + 1) * cellSize;
            int y2 = (cell.getRow() + 1) * cellSize;

            if (cell.getNorth() == null)
                graphics2d.drawLine(x1, y1, x2, y1);
            if (cell.getWest() == null)
                graphics2d.drawLine(x1, y1, x1, y2);

            if (!cell.isLinked(cell.getEast()))
                graphics2d.drawLine(x2, y1, x2, y2);
            if (!cell.isLinked(cell.getSouth()))
                graphics2d.drawLine(x1, y2, x2, y2);
        }

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