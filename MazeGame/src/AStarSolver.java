import java.util.*;

public class AStarSolver extends MazeSolver {

    public AStarSolver(Maze maze) {
        super(maze);
    }

    @Override
    public void solve(SolverListener listener) {
        PriorityQueue<PathNode> pq = new PriorityQueue<>();
        Map<String, Integer> gScores = new HashMap<>();
        Set<Position> explored = new HashSet<>();
        int nodesExplored = 0;

        List<Position> startPath = new ArrayList<>();
        startPath.add(start);
        int h = heuristic(start);
        pq.offer(new PathNode(start, startPath, 0, h));
        gScores.put(start.toString(), 0);

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
                int newGCost = current.getCost() + weight;

                String neighborKey = neighbor.toString();
                if (newGCost < gScores.getOrDefault(neighborKey, Integer.MAX_VALUE)) {
                    gScores.put(neighborKey, newGCost);
                    int newH = heuristic(neighbor);
                    int newFScore = newGCost + newH;

                    List<Position> newPath = new ArrayList<>(current.getPath());
                    newPath.add(neighbor);
                    pq.offer(new PathNode(neighbor, newPath, newGCost, newFScore));
                }
            }
        }

        if (listener != null) {
            listener.onNoSolution();
        }
    }
}