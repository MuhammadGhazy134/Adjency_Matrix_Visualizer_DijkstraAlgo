import java.util.*;

public class DijkstraSolver extends MazeSolver {

    public DijkstraSolver(Maze maze) {
        super(maze);
    }

    @Override
    public void solve(SolverListener listener) {
        PriorityQueue<PathNode> pq = new PriorityQueue<>();
        Map<String, Integer> distances = new HashMap<>();
        Set<Position> explored = new HashSet<>();
        int nodesExplored = 0;

        List<Position> startPath = new ArrayList<>();
        startPath.add(start);
        pq.offer(new PathNode(start, startPath, 0));
        distances.put(start.toString(), 0);

        while (!pq.isEmpty()) {
            PathNode current = pq.poll();
            Position pos = current.getPosition();

            if (explored.contains(pos)) continue;

            explored.add(pos);
            nodesExplored++;

            if (listener != null) {
                List<Position> queuePositions = new ArrayList<>();
                for (PathNode node : pq) {
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
                if (explored.contains(neighbor)) continue;

                int weight = maze.getCell(neighbor.getRow(), neighbor.getCol())
                        .getTerrain().getWeight();
                int newCost = current.getCost() + weight;

                String neighborKey = neighbor.toString();
                if (newCost < distances.getOrDefault(neighborKey, Integer.MAX_VALUE)) {
                    distances.put(neighborKey, newCost);
                    List<Position> newPath = new ArrayList<>(current.getPath());
                    newPath.add(neighbor);
                    pq.offer(new PathNode(neighbor, newPath, newCost));
                }
            }
        }

        if (listener != null) {
            listener.onNoSolution();
        }
    }
}