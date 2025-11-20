import java.util.ArrayList;

class Graph {
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();
    private int[][] adjacencyMatrix;
    private String[] label;

    public Graph(int[][] adjacencyMatrix, String[] label) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.label = label;
        initializeGraph();
    }

    private void initializeGraph() {
        int n = adjacencyMatrix.length;
        int centerX = 400, centerY = 300, radius = 200;

        // Create nodes in circular layout
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            int x = centerX + (int)(radius * Math.cos(angle));
            int y = centerY + (int)(radius * Math.sin(angle));
            nodes.add(new Node(i, x, y));
        }

        // Create edges from adjacency matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (adjacencyMatrix[i][j] != 0) {
                    edges.add(new Edge(nodes.get(i), nodes.get(j), adjacencyMatrix[i][j]));
                }
            }
        }
    }

    public ArrayList<Node> getNodes() { return nodes; }
    public ArrayList<Edge> getEdges() { return edges; }
    public int[][] getAdjacencyMatrix() { return adjacencyMatrix; }
    public String[] getLabel() { return label; }
}