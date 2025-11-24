import javax.swing.*;
import java.awt.*;
// BoardPanel class for rendering
class BoardPanel extends JPanel {
    private Board board;
    private GameManager gameManager;

    public BoardPanel(GameManager gameManager) {
        this.board = gameManager.getPlayers()[0] != null ?
                new Board() : new Board();
        this.gameManager = gameManager;
        setPreferredSize(new Dimension(520, 520));
        setBackground(new Color(245, 222, 179));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g);

        // Draw all players
        Player[] players = gameManager.getPlayers();
        for (int i = 0; i < players.length; i++) {
            Node node = board.getNode(players[i].getCurrentPosition());
            if (node != null) {
                players[i].draw(g, node, i, players.length);
            }
        }
    }

    public Board getBoard() {
        return board;
    }
}