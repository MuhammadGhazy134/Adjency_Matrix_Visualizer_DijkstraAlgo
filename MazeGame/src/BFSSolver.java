import java.util.*;

public class BFSSolver extends MazeSolver {

    public BFSSolver(Maze maze) {
        super(maze);
    }

    @Override
    public void solve(SolverListener listener) {
        Queue<PathNode> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Set<Position> explored = new HashSet<>();
        int nodesExplored = 0;

        List<Position> startPath = new ArrayList<>();
        startPath.add(start);
        queue.offer(new PathNode(start, startPath, 0));
        visited.add(start.toString());

        while (!queue.isEmpty()) {
            PathNode current = queue.poll();
            Position pos = current.getPosition();

            explored.add(pos);
            nodesExplored++;

            if (listener != null) {
                List<Position> queuePositions = new ArrayList<>();
                for (PathNode node : queue) {
                    queuePositions.add(node.getPosition());
                }
                listener.onStepComplete(pos, explored, queuePositions, nodesExplored);
            }

            if (pos.equals(goal)) {
                if (listener != null) {
                    listener.onSolutionFound(current.getPath(), current.getCost(), nodesExplored);
                }
                return;
            }

            for (Position neighbor : maze.getAccessibleNeighbors(pos)) {
                String neighborKey = neighbor.toString();
                if (!visited.contains(neighborKey)) {
                    visited.add(neighborKey);
                    int weight = maze.getCell(neighbor.getRow(), neighbor.getCol())
                            .getTerrain().getWeight();
                    List<Position> newPath = new ArrayList<>(current.getPath());
                    newPath.add(neighbor);
                    queue.offer(new PathNode(neighbor, newPath, current.getCost() + weight));
                }
            }
        }

        if (listener != null) {
            listener.onNoSolution();
        }
    }
}