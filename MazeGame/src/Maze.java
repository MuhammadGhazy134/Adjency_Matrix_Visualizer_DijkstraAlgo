import java.util.*;

public class Maze {
    private int rows;
    private int cols;
    private Cell[][] grid;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Cell[rows][cols];
        initializeMaze();
    }

    private void initializeMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell();
                grid[i][j].setTerrain(TerrainType.getRandomTerrain());
            }
        }
        grid[0][0].setTerrain(TerrainType.DEFAULT);
        grid[rows - 1][cols - 1].setTerrain(TerrainType.DEFAULT);
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public Cell getCell(int row, int col) { return grid[row][col]; }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public List<Position> getAccessibleNeighbors(Position pos) {
        List<Position> neighbors = new ArrayList<>();
        int row = pos.getRow();
        int col = pos.getCol();
        Cell cell = grid[row][col];

        if (!cell.hasTopWall() && row > 0) {
            neighbors.add(new Position(row - 1, col));
        }
        if (!cell.hasRightWall() && col < cols - 1) {
            neighbors.add(new Position(row, col + 1));
        }
        if (!cell.hasBottomWall() && row < rows - 1) {
            neighbors.add(new Position(row + 1, col));
        }
        if (!cell.hasLeftWall() && col > 0) {
            neighbors.add(new Position(row, col - 1));
        }

        return neighbors;
    }

    public void reset() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell();
                grid[i][j].setTerrain(TerrainType.getRandomTerrain());
            }
        }
        grid[0][0].setTerrain(TerrainType.DEFAULT);
        grid[rows - 1][cols - 1].setTerrain(TerrainType.DEFAULT);
    }
}