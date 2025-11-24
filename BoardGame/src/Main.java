import javax.swing.*;
import java.awt.*;

// Main class to run the game
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Board Game - 8x8");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Ask for number of players
            String input = JOptionPane.showInputDialog(frame,
                    "Enter number of players (2-4):", "2");
            int numPlayers = 2;
            try {
                numPlayers = Integer.parseInt(input);
                if (numPlayers < 2) numPlayers = 2;
                if (numPlayers > 4) numPlayers = 4;
            } catch (Exception e) {
                numPlayers = 2;
            }

            // Initialize game components
            Board board = new Board();
            GameManager gameManager = new GameManager(board, numPlayers);
            BoardPanel boardPanel = new BoardPanel(gameManager);

            // Create control panel
            JPanel controlPanel = new JPanel();
            controlPanel.setLayout(new FlowLayout());

            JLabel statusLabel = new JLabel("Current: " +
                    gameManager.getCurrentPlayer().getName() + " | Roll: 0 | Color: -");
            statusLabel.setFont(new Font("Arial", Font.BOLD, 14));

            JButton rollButton = new JButton("Roll Dice");
            rollButton.setFont(new Font("Arial", Font.BOLD, 14));

            rollButton.addActionListener(e -> {
                if (!gameManager.isGameOver()) {
                    gameManager.playTurn();
                    boardPanel.repaint();

                    Dice dice = gameManager.getDice();
                    String direction = dice.isGreen() ? "→" : "←";

                    if (gameManager.isGameOver()) {
                        Player winner = gameManager.getWinner();
                        statusLabel.setText("<html>GAME OVER! Winner: <b>" + winner.getName() +
                                "</b> | Last Roll: " + dice.getLastRoll() + " " + direction +
                                " <font color='" + (dice.isGreen() ? "green" : "red") + "'>" +
                                dice.getColorText() + "</font></html>");
                        rollButton.setEnabled(false);
                    } else {
                        statusLabel.setText("<html>Current: <b>" +
                                gameManager.getCurrentPlayer().getName() +
                                "</b> | Last Roll: " + dice.getLastRoll() + " " + direction +
                                " <font color='" + (dice.isGreen() ? "green" : "red") + "'>" +
                                dice.getColorText() + "</font></html>");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Game is over! " + gameManager.getWinner().getName() + " won!");
                }
            });

            controlPanel.add(rollButton);
            controlPanel.add(statusLabel);

            frame.add(boardPanel, BorderLayout.CENTER);
            frame.add(controlPanel, BorderLayout.SOUTH);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}