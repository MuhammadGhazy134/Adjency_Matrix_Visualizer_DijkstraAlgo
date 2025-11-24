
import java.awt.*;

// GameManager class to handle game logic
class GameManager {
    private Board board;
    private Player[] players;
    private int currentPlayerIndex;
    private Dice dice;
    private boolean gameOver;

    public GameManager(Board board, int numPlayers) {
        this.board = board;
        this.dice = new Dice();
        this.currentPlayerIndex = 0;
        this.gameOver = false;

        // Initialize players with different colors
        players = new Player[numPlayers];
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player("Player " + (i + 1), colors[i % colors.length]);
        }
    }

    public void playTurn() {
        if (gameOver) return;

        Player currentPlayer = players[currentPlayerIndex];
        int roll = dice.roll();

        int newPosition;
        if (dice.isGreen()) {
            // Green: move forward
            newPosition = currentPlayer.getCurrentPosition() + roll;
        } else {
            // Red: move backward
            newPosition = currentPlayer.getCurrentPosition() - roll;
        }

        // Ensure position stays within bounds
        if (newPosition < 1) {
            newPosition = 1; // Can't go below position 1
        }

        // Check if player reaches or exceeds the final position
        if (newPosition >= board.getTotalNodes()) {
            currentPlayer.setCurrentPosition(board.getTotalNodes());
            gameOver = true;
        } else {
            currentPlayer.setCurrentPosition(newPosition);
        }

        // Move to next player
        if (!gameOver) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        }
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getLastRoll() {
        return dice.getLastRoll();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getWinner() {
        if (!gameOver) return null;
        return players[currentPlayerIndex];
    }

    public Dice getDice() {
        return dice;
    }
}