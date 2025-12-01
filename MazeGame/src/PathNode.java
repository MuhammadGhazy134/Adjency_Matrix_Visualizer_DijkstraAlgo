import java.util.ArrayList;
import java.util.List;

public class PathNode implements Comparable<PathNode> {
    private Position position;
    private List<Position> path;
    private int cost;
    private int fScore; // For A*

    public PathNode(Position position, List<Position> path, int cost) {
        this.position = position;
        this.path = new ArrayList<>(path);
        this.cost = cost;
        this.fScore = cost;
    }

    public PathNode(Position position, List<Position> path, int cost, int fScore) {
        this.position = position;
        this.path = new ArrayList<>(path);
        this.cost = cost;
        this.fScore = fScore;
    }

    public Position getPosition() { return position; }
    public List<Position> getPath() { return path; }
    public int getCost() { return cost; }
    public int getFScore() { return fScore; }

    @Override
    public int compareTo(PathNode other) {
        return Integer.compare(this.fScore, other.fScore);
    }
}