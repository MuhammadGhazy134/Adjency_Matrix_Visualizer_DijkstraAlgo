import java.util.*;

public class MazeGenerator {
    private Maze maze;
    private Random random;

    private static class Edge {
        Position from;
        Position to;
        String wall;
        String opposite;

        Edge(Position from, Position to, String wall, String opposite) {
            this.from = from;
            this.to = to;
            this.wall = wall;
            this.opposite = opposite;
        }
    }

    public MazeGenerator(Maze maze) {
        this.maze = maze;
        this.random = new Random();
    }

    public void generateWithPrim(MazeGenerationListener listener) {
        int startRow = random.nextInt(maze.getRows());
        int startCol = random.nextInt(maze.getCols());

        Set<String> visited = new HashSet<>();
        List<Edge> frontier = new ArrayList<>();

        Position start = new Position(startRow, startCol);
        maze.getCell(startRow, startCol).setVisited(true);
        visited.add(start.toString());

        addFrontiers(start, frontier, visited);

        if (listener != null) {
            listener.onStepComplete(start, visited, frontier.size());
        }

        while (!frontier.isEmpty()) {
            int randomIndex = random.nextInt(frontier.size());
            Edge edge = frontier.remove(randomIndex);

            Position to = edge.to;
            int toRow = to.getRow();
            int toCol = to.getCol();

            if (!maze.getCell(toRow, toCol).isVisited()) {
                removeWall(edge.from, edge.to, edge.wall, edge.opposite);
                maze.getCell(toRow, toCol).setVisited(true);
                visited.add(to.toString());

                addFrontiers(to, frontier, visited);

                if (listener != null) {
                    listener.onStepComplete(to, visited, frontier.size());
                }
            }
        }

        maze.getCell(0, 0).setTopWall(false);
        maze.getCell(maze.getRows() - 1, maze.getCols() - 1).setBottomWall(false);

        if (listener != null) {
            listener.onGenerationComplete();
        }
    }

    private void addFrontiers(Position pos, List<Edge> frontier, Set<String> visited) {
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        String[] walls = {"top", "right", "bottom", "left"};
        String[] opposites = {"bottom", "left", "top", "right"};

        for (int i = 0; i < directions.length; i++) {
            int newRow = pos.getRow() + directions[i][0];
            int newCol = pos.getCol() + directions[i][1];

            if (maze.isValidPosition(newRow, newCol)) {
                Position neighbor = new Position(newRow, newCol);
                if (!visited.contains(neighbor.toString())) {
                    frontier.add(new Edge(pos, neighbor, walls[i], opposites[i]));
                }
            }
        }
    }

    private void removeWall(Position from, Position to, String wall, String opposite) {
        Cell fromCell = maze.getCell(from.getRow(), from.getCol());
        Cell toCell = maze.getCell(to.getRow(), to.getCol());

        switch (wall) {
            case "top": fromCell.setTopWall(false); break;
            case "right": fromCell.setRightWall(false); break;
            case "bottom": fromCell.setBottomWall(false); break;
            case "left": fromCell.setLeftWall(false); break;
        }

        switch (opposite) {
            case "top": toCell.setTopWall(false); break;
            case "right": toCell.setRightWall(false); break;
            case "bottom": toCell.setBottomWall(false); break;
            case "left": toCell.setLeftWall(false); break;
        }
    }

    public interface MazeGenerationListener {
        void onStepComplete(Position current, Set<String> visited, int frontierSize);
        void onGenerationComplete();
    }
}