import java.util.*;

public class DFSSolver extends MazeSolver {

    public DFSSolver(Maze maze) {
        super(maze);
    }

    @Override
    public void solve(SolverListener listener) {
        Stack<PathNode> stack = new Stack<>();
        Set<String> visited = new HashSet<>();
        Set<Position> explored = new HashSet<>();
        int nodesExplored = 0;

        List<Position> startPath = new ArrayList<>();
        startPath.add(start);
        stack.push(new PathNode(start, startPath, 0));
        visited.add(start.toString());

        while (!stack.isEmpty()) {
            PathNode current = stack.pop();
            Position pos = current.getPosition();

            explored.add(pos);
            nodesExplored++;

            if (listener != null) {
                List<Position> stackPositions = new ArrayList<>();
                for (PathNode node : stack) {
                    stackPositions.add(node.getPosition());
                }
                listener.onStepComplete(pos, explored, stackPositions, nodesExplored);
            }

            if (pos.equals(goal)) {
                if (listener != null) {
                    listener.onSolutionFound(current.getPath(), current.getCost(), nodesExplored);
                }
                return;
            }

            List<Position> neighbors = maze.getAccessibleNeighbors(pos);
            Collections.reverse(neighbors);

            for (Position neighbor : neighbors) {
                String neighborKey = neighbor.toString();
                if (!visited.contains(neighborKey)) {
                    visited.add(neighborKey);
                    int weight = maze.getCell(neighbor.getRow(), neighbor.getCol())
                            .getTerrain().getWeight();
                    List<Position> newPath = new ArrayList<>(current.getPath());
                    newPath.add(neighbor);
                    stack.push(new PathNode(neighbor, newPath, current.getCost() + weight));
                }
            }
        }

        if (listener != null) {
            listener.onNoSolution();
        }
    }
}