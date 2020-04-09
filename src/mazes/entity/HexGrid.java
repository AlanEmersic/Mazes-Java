package mazes.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HexGrid extends Grid
{
    public HexGrid(int rows, int cols, int seed)
    {
        super(rows, cols, seed);
    }

    @Override
    protected void prepareGrid()
    {
        size = rows * cols;
        cells = new HexCell[rows][];

        for (int i = 0; i < rows; i++)
        {
            cells[i] = new HexCell[cols];
            for (int j = 0; j < cols; j++)
                cells[i][j] = new HexCell(i, j);
        }
    }

    @Override
    protected void configureCells()
    {
        for (Cell cell : eachCell())
        {
            int row = cell.getRow();
            int col = cell.getCol();
            int northDiagonal, southDiagonal;

            if (col % 2 == 0)
            {
                northDiagonal = row - 1;
                southDiagonal = row;
            } else
            {
                northDiagonal = row;
                southDiagonal = row + 1;
            }

            ((HexCell) cell).setNorthWest((HexCell) getCell(northDiagonal, col - 1));
            ((HexCell) cell).setNorth((HexCell) getCell(row - 1, col));
            ((HexCell) cell).setNorthEast((HexCell) getCell(northDiagonal, col + 1));
            ((HexCell) cell).setSouthWest((HexCell) getCell(southDiagonal, col - 1));
            ((HexCell) cell).setSouth((HexCell) getCell(row + 1, col));
            ((HexCell) cell).setSouthEast((HexCell) getCell(southDiagonal, col + 1));
        }
    }

    @Override
    public void toPNG(int cellSize)
    {
        float aSize = cellSize / 2.0f;
        float bSize = (float) (cellSize * Math.sqrt(3) / 2.0f);
        float height = bSize * 2;
        int imgWidth = (int) (3 * aSize * cols + aSize + 0.5f);
        int imgHeight = (int) (height * rows + bSize + 0.5f);

        BufferedImage image = new BufferedImage(imgWidth + 1, imgHeight + 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2d = image.createGraphics();
        graphics2d.setBackground(Color.WHITE);
        graphics2d.fillRect(0, 0, imgWidth, imgHeight);
        float penWidth = cellSize * 0.1f;
        graphics2d.setColor(Color.BLACK);
        graphics2d.setStroke(new BasicStroke(penWidth));

        for (Cell cell : eachCell())
        {
            float cx = cellSize + 3 * cell.getCol() * aSize;
            float cy = bSize + cell.getRow() * height;
            if (cell.getCol() % 2 != 0)
                cy += bSize;

            int xFW = (int) (cx - cellSize);
            int xNW = (int) (cx - aSize);
            int xNE = (int) (cx + aSize);
            int xFE = (int) (cx + cellSize);

            int yN = (int) (cy - bSize);
            int yM = (int) cy;
            int yS = (int) (cy + bSize);

            if (((HexCell) cell).getSouthWest() == null)
                graphics2d.drawLine(xFW, yM, xNW, yS);
            if (((HexCell) cell).getNorthWest() == null)
                graphics2d.drawLine(xFW, yM, xNW, yN);
            if (((HexCell) cell).getNorth() == null)
                graphics2d.drawLine(xNW, yN, xNE, yN);

            if (!cell.isLinked(((HexCell) cell).getNorthEast()))
                graphics2d.drawLine(xNE, yN, xFE, yM);
            if (!cell.isLinked(((HexCell) cell).getSouthEast()))
                graphics2d.drawLine(xFE, yM, xNE, yS);
            if (!cell.isLinked(((HexCell) cell).getSouth()))
                graphics2d.drawLine(xNE, yS, xNW, yS);
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
