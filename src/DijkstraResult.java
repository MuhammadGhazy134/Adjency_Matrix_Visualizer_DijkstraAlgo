import java.util.*;

class DijkstraResult {
    private ArrayList<Integer> path;
    private int totalDistance;

    public DijkstraResult(ArrayList<Integer> path, int totalDistance) {
        this.path = path;
        this.totalDistance = totalDistance;
    }

    public ArrayList<Integer> getPath() { return path; }
    public int getTotalDistance() { return totalDistance; }
    public boolean hasPath() { return !path.isEmpty(); }
}