import javax.swing.*;
import java.awt.*;

public class GraphVisualizer extends JFrame {
    private Graph graph;
    private GraphPanel graphPanel;
    private DijkstraAlgorithm dijkstra;
    private JComboBox<String> startCombo, endCombo;
    private JLabel resultLabel;

    public GraphVisualizer(int[][] adjacencyMatrix, String[] labels) {
        setTitle("Graph Visualizer - Dijkstra's Shortest Path");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        graph = new Graph(adjacencyMatrix, labels);
        graphPanel = new GraphPanel(graph);
        dijkstra = new DijkstraAlgorithm(graph);

        add(graphPanel, BorderLayout.CENTER);

        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("Start:"));
        startCombo = new JComboBox<>(labels);
        controlPanel.add(startCombo);

        controlPanel.add(new JLabel("End:"));
        endCombo = new JComboBox<>(labels);
        endCombo.setSelectedIndex(1);
        controlPanel.add(endCombo);

        JButton findBtn = new JButton("Find Shortest Path");
        findBtn.addActionListener(e -> findAndDisplayPath());
        controlPanel.add(findBtn);

        JButton clearBtn = new JButton("Clear Path");
        clearBtn.addActionListener(e -> {
            graphPanel.clearShortestPath();
            resultLabel.setText("Select start/end nodes and click 'Find Shortest Path'.");
        });
        controlPanel.add(clearBtn);

        add(controlPanel, BorderLayout.NORTH);

        // Result label
        resultLabel = new JLabel("Select start/end nodes and click 'Find Shortest Path'.");
        JPanel infoPanel = new JPanel();
        infoPanel.add(resultLabel);
        add(infoPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void findAndDisplayPath() {
        int start = startCombo.getSelectedIndex();
        int end = endCombo.getSelectedIndex();

        if (start == end) {
            resultLabel.setText("Start and end nodes must be different!");
            return;
        }

        DijkstraResult result = dijkstra.findShortestPath(start, end);

        if (result.hasPath()) {
            graphPanel.setShortestPath(result.getPath());
            StringBuilder pathStr = new StringBuilder("Path: ");
            String[] labels = graph.getLabel();
            for (int i = 0; i < result.getPath().size(); i++) {
                pathStr.append(labels[result.getPath().get(i)]);
                if (i < result.getPath().size() - 1) pathStr.append(" → ");
            }
            pathStr.append(" | Total Distance: ").append(result.getTotalDistance());
            resultLabel.setText(pathStr.toString());
        } else {
            graphPanel.clearShortestPath();
            resultLabel.setText("No path found!");
        }
    }

    public static void main(String[] args) {
        String[] labels = {"MKS", "SUB", "BDG", "CGK", "DPS", "MLG", "DHS", "YOG", "PDG", "BIM"};
        int[][] adjacencyMatrix = {
                {0,  2,  3,  0,  8,  0,  0,  0,  0,  0},
                {2,  0,  0,  3,  1,  0,  0,  0,  0,  0},
                {3,  0,  0,  4,  0,  2,  0,  0,  0,  0},
                {0,  3,  4,  0,  0,  6,  4,  0,  0,  0},
                {8,  1,  0,  0,  0,  0,  2,  3,  0, 10},
                {0,  0,  2,  6,  0,  0,  8,  0,  4,  0},
                {0,  0,  0,  4,  2,  8,  0,  0,  0,  3},
                {0,  0,  0,  0,  3,  0,  0,  0,  0,  4},
                {0,  0,  0,  0,  0,  4,  0,  0,  0,  3},
                {0,  0,  0,  0, 10,  0,  3,  4,  3,  0}
        };

        SwingUtilities.invokeLater(() -> {
            new GraphVisualizer(adjacencyMatrix, labels).setVisible(true);
        });
    }
}