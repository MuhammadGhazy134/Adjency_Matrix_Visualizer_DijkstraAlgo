import javax.swing.*;
import java.awt.*;

public class MazeFrame extends JFrame {
    private MazePanel mazePanel;
    private ControlPanel controlPanel;

    public MazeFrame() {
        setTitle("Weighted Graph Maze Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mazePanel = new MazePanel();
        controlPanel = new ControlPanel(mazePanel);

        add(controlPanel, BorderLayout.NORTH);
        add(mazePanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
}