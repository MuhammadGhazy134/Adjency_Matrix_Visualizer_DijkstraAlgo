import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private MazePanel mazePanel;
    private JButton generateButton;
    private JButton solveButton;
    private JButton resetButton;
    private JComboBox<String> algorithmCombo;
    private JSlider genSpeedSlider;
    private JSlider solveSpeedSlider;
    private JLabel statsLabel;

    private Timer statsTimer;

    public ControlPanel(MazePanel mazePanel) {
        this.mazePanel = mazePanel;

        setLayout(new BorderLayout());
        setBackground(new Color(30, 41, 59));
        setPreferredSize(new Dimension(800, 150));

        initComponents();
        startStatsTimer();
    }

    private void initComponents() {
        // Top panel - Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(30, 41, 59));
        JLabel titleLabel = new JLabel("Weighted Graph Maze Solver");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Middle panel - Controls
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.setBackground(new Color(30, 41, 59));

        // Row 1: Generation controls
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row1.setBackground(new Color(30, 41, 59));

        generateButton = createStyledButton("Generate Maze", new Color(16, 185, 129));
        generateButton.addActionListener(e -> {
            int speed = 210 - genSpeedSlider.getValue();
            mazePanel.generateMaze(speed);
        });
        row1.add(generateButton);

        JLabel genSpeedLabel = new JLabel("Gen Speed:");
        genSpeedLabel.setForeground(Color.WHITE);
        row1.add(genSpeedLabel);

        genSpeedSlider = new JSlider(10, 200, 50);
        genSpeedSlider.setPreferredSize(new Dimension(120, 30));
        genSpeedSlider.setBackground(new Color(30, 41, 59));
        row1.add(genSpeedSlider);

        resetButton = createStyledButton("Reset", new Color(37, 99, 235));
        resetButton.addActionListener(e -> mazePanel.reset());
        row1.add(resetButton);

        controlsPanel.add(row1);

        // Row 2: Solving controls
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row2.setBackground(new Color(30, 41, 59));

        JLabel algoLabel = new JLabel("Algorithm:");
        algoLabel.setForeground(Color.WHITE);
        row2.add(algoLabel);

        algorithmCombo = new JComboBox<>(new String[]{"BFS", "DFS", "Dijkstra", "A*"});
        algorithmCombo.setSelectedItem("Dijkstra");
        algorithmCombo.setPreferredSize(new Dimension(120, 35));
        algorithmCombo.setFont(new Font("Arial", Font.PLAIN, 12));
        row2.add(algorithmCombo);

        solveButton = createStyledButton("Solve Maze", new Color(139, 92, 246));
        solveButton.addActionListener(e -> {
            String algorithm = (String) algorithmCombo.getSelectedItem();
            int speed = 310 - solveSpeedSlider.getValue();
            mazePanel.solveMaze(algorithm, speed);
        });
        row2.add(solveButton);

        JLabel solveSpeedLabel = new JLabel("Solve Speed:");
        solveSpeedLabel.setForeground(Color.WHITE);
        row2.add(solveSpeedLabel);

        solveSpeedSlider = new JSlider(10, 300, 100);
        solveSpeedSlider.setPreferredSize(new Dimension(120, 30));
        solveSpeedSlider.setBackground(new Color(30, 41, 59));
        row2.add(solveSpeedSlider);

        controlsPanel.add(row2);

        // Bottom panel - Stats
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statsPanel.setBackground(new Color(30, 41, 59));
        statsLabel = new JLabel("Ready - Generate a maze to begin");
        statsLabel.setForeground(Color.WHITE);
        statsLabel.setFont(new Font("Arial", Font.BOLD, 13));
        statsPanel.add(statsLabel);

        // Add all panels
        add(titlePanel, BorderLayout.NORTH);
        add(controlsPanel, BorderLayout.CENTER);
        add(statsPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(bgColor.brighter());
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void startStatsTimer() {
        statsTimer = new Timer(100, e -> updateStats());
        statsTimer.start();
    }

    private void updateStats() {
        if (mazePanel.isGenerating()) {
            statsLabel.setText("🔄 Generating maze...");
            generateButton.setEnabled(false);
            solveButton.setEnabled(false);
            resetButton.setEnabled(false);
            algorithmCombo.setEnabled(false);
        } else if (mazePanel.isSolving()) {
            statsLabel.setText(String.format(
                    "🔍 Solving... | Nodes Explored: %d | Queue/Stack Size: %d",
                    mazePanel.getNodesExplored(),
                    mazePanel.getQueueSize()
            ));
            generateButton.setEnabled(false);
            solveButton.setEnabled(false);
            resetButton.setEnabled(false);
            algorithmCombo.setEnabled(false);
        } else {
            if (mazePanel.getPathLength() > 0) {
                statsLabel.setText(String.format(
                        "✅ Solution Found! | Path Cost: %d | Path Length: %d | Nodes Explored: %d",
                        mazePanel.getPathCost(),
                        mazePanel.getPathLength(),
                        mazePanel.getNodesExplored()
                ));
            } else {
                statsLabel.setText("Ready - Generate a maze to begin");
            }
            generateButton.setEnabled(true);
            solveButton.setEnabled(true);
            resetButton.setEnabled(true);
            algorithmCombo.setEnabled(true);
        }
    }
}