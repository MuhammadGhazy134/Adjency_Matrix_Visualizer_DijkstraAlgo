import java.util.*;

public abstract class MazeSolver {
    protected Maze maze;
    protected Position start;
    protected Position goal;

    public MazeSolver(Maze maze) {
        this.maze = maze;
        this.start = new Position(0, 0);
        this.goal = new Position(maze.getRows() - 1, maze.getCols() - 1);
    }

    public abstract void solve(SolverListener listener);

    protected int heuristic(Position pos) {
        return Math.abs(pos.getRow() - goal.getRow()) +
                Math.abs(pos.getCol() - goal.getCol());
    }

    public interface SolverListener {
        void onStepComplete(Position current, Set<Position> explored,
                            List<Position> queue, int nodesExplored);
        void onSolutionFound(List<Position> path, int cost, int nodesExplored);
        void onNoSolution();
    }
}