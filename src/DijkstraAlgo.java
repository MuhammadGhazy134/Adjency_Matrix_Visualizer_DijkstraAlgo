import java.util.*;

class DijkstraAlgorithm {
    private int[][] adjacencyMatrix;

    public DijkstraAlgorithm(Graph graph) {
        this.adjacencyMatrix = graph.getAdjacencyMatrix();
    }

    public DijkstraResult findShortestPath(int start, int end) {
        int n = adjacencyMatrix.length;
        int[] dist = new int[n];
        int[] prev = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];

            if (visited[u]) continue;
            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (adjacencyMatrix[u][v] != 0) {
                    int newDist = dist[u] + adjacencyMatrix[u][v];
                    if (newDist < dist[v]) {
                        dist[v] = newDist;
                        prev[v] = u;
                        pq.offer(new int[]{v, newDist});
                    }
                }
            }
        }

        // Reconstruct path
        ArrayList<Integer> path = new ArrayList<>();
        if (dist[end] != Integer.MAX_VALUE) {
            for (int at = end; at != -1; at = prev[at]) {
                path.add(0, at);
            }
        }

        return new DijkstraResult(path, dist[end]);
    }
}
